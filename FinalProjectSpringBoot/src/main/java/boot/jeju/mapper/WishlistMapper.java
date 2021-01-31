package boot.jeju.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import boot.jeju.data.SpotlistDto;
import boot.jeju.data.SpotreviewDto;
import boot.jeju.data.WishlistDto;

@Mapper
public interface WishlistMapper {
	public void insertAround(WishlistDto dto);
	public void insertSpot(WishlistDto dto);
	public int isSpotSearch(@Param("spotId") String contentsid,@Param("memId") String id);
	public int isShareSearch(@Param("shareNum") String num);
	public void insertContent(WishlistDto dto);
	public void insertshare(WishlistDto dto);
	public void deleteContent(String num);
	public List<WishlistDto> getList(String memId);
	public List<SpotreviewDto> getMyreviews(@Param("memNum") String memNum,@Param("start") int start,@Param("end") int end);
	public List<SpotreviewDto> getRecentreviews(String memNum);
	public String getSpottitle(String contentsid);
	public int getTotalCount(String memNum);
	public String getShareSubject(String num);
	public int getWishTotalCount(String memId);
	public List<WishlistDto> getDayMyto(String memId, String day);
	public List<WishlistDto> getPlanList(@Param("memId") String memId, @Param("day") String day, @Param("category") String category);
	public SpotlistDto getSpot(String contentsid);
}
