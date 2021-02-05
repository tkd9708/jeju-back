package boot.jeju.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import boot.jeju.data.ChatmsgDto;
import boot.jeju.data.ChatroomDto;

@Mapper
public interface ChatMapper {
	public void createRoom(ChatroomDto dto);
	public List<ChatroomDto> getRoom(String user);
	public void insertMsg(ChatmsgDto dto);
	public List<ChatmsgDto> getMsgs(String roomNum);
}
