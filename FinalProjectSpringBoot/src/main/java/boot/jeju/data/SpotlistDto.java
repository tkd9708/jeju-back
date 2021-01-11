package boot.jeju.data;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("spotlist")
public class SpotlistDto {
	private String contentsid;
	private String title;
	private String label1;
	private String label2;
	private String roadaddr;
	private String addr;
	private String latitude;
	private String longitude;
	private String tag;
	private String introduction;
	private String img;
	private String thumbnail;
	private int star;
	private int likes;
}