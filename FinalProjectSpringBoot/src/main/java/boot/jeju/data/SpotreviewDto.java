package boot.jeju.data;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@Alias("spotreview")
public class SpotreviewDto {
	private String num;
	private String contentsid;
	private String memNum;
	private String content;
	private int star;
	private int likes;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Timestamp writeday;
	
}