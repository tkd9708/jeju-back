package boot.jeju.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {
	
	List<Room> roomList = new ArrayList<Room>();
	static int roomNum = 0;
	
	@GetMapping("/chat")
	public ModelAndView chat() {
		ModelAndView mview = new ModelAndView();
		mview.setViewName("chat");
		
		return mview;
	}
	
	// 방페이지
	@GetMapping("/room")
	public ModelAndView room() {
		ModelAndView mview = new ModelAndView();
		mview.setViewName("room");
		return mview;
	}
	
	// 방 생성하기
	@PostMapping("/createRoom")
	public @ResponseBody List<Room> createRoom(@RequestParam HashMap<Object, Object> params){
		String roomName = (String)params.get("roomName");
		
		if(roomName != null && !roomName.trim().equals("")) {
			Room room = new Room();
			room.setRoomName(roomName);
			room.setRoomNum(++roomNum);
			roomList.add(room);
		}
		
		return roomList;
	}
	
	// 방 정보가져오기
	@PostMapping("/getRoom")
	public @ResponseBody List<Room> getRoom(@RequestParam HashMap<Object, Object> params){
		return roomList;
	}
	
	// 채팅방
	@RequestMapping("/moveChating")
	public ModelAndView chating(@RequestParam HashMap<Object, Object> params) {
		ModelAndView mview = new ModelAndView();
		
		int roomNumber = Integer.parseInt((String) params.get("roomNum"));
		
		List<Room> newList = roomList.stream().filter(o->o.getRoomNum()==roomNumber).collect(Collectors.toList());
		if(newList != null && newList.size() > 0) {
			mview.addObject("roomName", params.get("roomName"));
			mview.addObject("roomNum", params.get("roomNum"));
			mview.setViewName("chat");
		}else {
			mview.setViewName("room");
		}
		
		return mview;
	}
}
