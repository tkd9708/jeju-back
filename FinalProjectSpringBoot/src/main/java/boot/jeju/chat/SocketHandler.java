package boot.jeju.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {
	
	//HashMap<String, WebSocketSession> sessionMap = new HashMap<String, WebSocketSession>(); // 웹소켓 세션을 담아둘 맵
	List<HashMap<String, Object>> roomListSession = new ArrayList<HashMap<String,Object>>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
		
		// 소켓 연결
		boolean flag = false;
		String url = session.getUri().toString();
		System.out.println(url);
		
		String roomNum = url.split("/chating/")[1];	
		int idx = roomListSession.size();
		if(roomListSession.size() > 0) {
			for(int i=0; i<roomListSession.size(); i++) {
				String roomNumber = (String)roomListSession.get(i).get("roomNum");
				if(roomNumber.equals(roomNum)) {
					flag = true;
					idx = i;
					break;
				}
			}
		}
		
		if(flag) { // 존재하는 방이라면 세션만 추가
			HashMap<String, Object> map = roomListSession.get(idx);
			map.put(session.getId(), session);
		} 
		else { // 최초 생성하는 방이라면 방번호와 세션 추가
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("roomNum", roomNum);
			map.put(session.getId(), session);
			roomListSession.add(map);
		}
		
		//세션등록이 끝나면 발급받은 세션ID값의 메시지를 발송
		JSONObject obj = new JSONObject();
		obj.put("type", "getId");
		obj.put("sessionId", session.getId());
		session.sendMessage(new TextMessage(obj.toJSONString()));
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 메시지 발송
		String msg = message.getPayload();
		JSONObject obj = JsonToObjectParser(msg);
		
		String roomNum = (String) obj.get("roomNum");
		HashMap<String, Object> temp = new HashMap<String, Object>();
		
		if(roomListSession.size() > 0) {
			for(int i=0; i<roomListSession.size(); i++) {
				String roomNumber = (String)roomListSession.get(i).get("roomNum");
				if(roomNumber.equals(roomNum)) { // 같은 방이 존재하면
					temp = roomListSession.get(i); // 해당 방의 모든 object가져오기
					break;
				}
			}
			
			// 해당방의 세션들만 찾아서 메세지 보내주기
			for(String key : temp.keySet()) {
				if(key.equals("roomNum")) // 방번호일경우에는 넘기기
					continue;
				
				WebSocketSession wss = (WebSocketSession)temp.get(key);
				if(wss != null) {
					try {
						wss.sendMessage(new TextMessage(obj.toJSONString()));
					} catch(Exception e) {
						e.printStackTrace();
						System.out.println("채팅 메세지 전송 오류 : " + e.getMessage());
					}
				}
				
			}
		}
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 소켓 종료
		if(roomListSession.size() > 0) {
			for(int i=0; i<roomListSession.size(); i++) {
				roomListSession.get(i).remove(session.getId());
			}
		}
		
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
	}
	
	private static JSONObject JsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}
