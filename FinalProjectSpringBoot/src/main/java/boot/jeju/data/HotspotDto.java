package boot.jeju.data;

import org.apache.ibatis.type.Alias;

@Alias("hotspot")
public class HotspotDto {
	public String Num;
	public String groupNum;
	public String title;
	public String day;
	
	public String getNum() {
		return Num;
	}
	public void setNum(String num) {
		Num = num;
	}
	public String getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
	
}
