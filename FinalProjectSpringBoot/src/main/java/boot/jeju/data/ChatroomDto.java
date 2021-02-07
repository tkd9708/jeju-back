package boot.jeju.data;

import org.apache.ibatis.type.Alias;

@Alias("chatroom")
public class ChatroomDto {
	private String num;
	private String user1;
	private String user2;
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	public String getUser2() {
		return user2;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
	
}
