package boot.jeju.chat;

public class Room {
	int roomNum;
	String roomName;
	
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Room [roomNum=" + roomNum + ", roomName=" + roomName + "]";
	}
}
