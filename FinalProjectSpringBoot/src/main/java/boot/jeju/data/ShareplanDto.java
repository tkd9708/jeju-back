package boot.jeju.data;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;

@Alias("shareplan")
public class ShareplanDto {
	private String num;
	private String memId;
	private String title;
	private String content;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Seoul")
	private Timestamp wishday;
	private String wishtime;
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getWishday() {
		return wishday;
	}
	public void setWishday(Timestamp wishday) {
		this.wishday = wishday;
	}
	public String getWishtime() {
		return wishtime;
	}
	public void setWishtime(String wishtime) {
		this.wishtime = wishtime;
	}
	
}
