package boot.jeju.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boot.jeju.data.ChatmsgDto;
import boot.jeju.data.ChatroomDto;
import boot.jeju.mapper.ChatMapper;

@RestController
@CrossOrigin
public class ChatController {

	@Autowired
	ChatMapper mapper;
	
	// 방 생성
	@PostMapping("/chat/createRoom")
	public void createRoom(@RequestBody ChatroomDto dto){
		mapper.createRoom(dto);
	}
	
	// user가 속한 방 리스트
	@GetMapping("/chat/getRoom")
	public List<ChatroomDto> getRoom(@RequestParam String user){
		return mapper.getRoom(user);
	}
	
	// user이 searchId 검색 
	@GetMapping("/chat/getSearchIdRoom")
	public List<ChatroomDto> getSearchIdRoom(@RequestParam String user, @RequestParam String searchId){
		return mapper.getSearchIdRoom(user, searchId);
	}
	
	// 해당 방의 메시지 리스트
	@GetMapping("/chat/getMsgs")
	public List<ChatmsgDto> getMsgs(@RequestParam String roomNum){
		return mapper.getMsgs(roomNum);
	}
	
	// 메시지 입력
	@PostMapping("/chat/insertMsg")
	public void insertMsg(@RequestBody ChatmsgDto dto) {
		mapper.insertMsg(dto);
	}
}
