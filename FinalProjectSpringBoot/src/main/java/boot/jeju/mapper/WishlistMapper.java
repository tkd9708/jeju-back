package boot.jeju.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import boot.jeju.data.SpotreviewDto;
import boot.jeju.data.WishlistDto;

@Mapper
public interface WishlistMapper {
	public void insertAround(WishlistDto dto);
	public void insertSpot(WishlistDto dto);
	public boolean isSpotSearch(String contentsid,String id);
	public boolean isShareSearch(String num);
	public void insertContent(WishlistDto dto);
	public void insertshare(WishlistDto dto);
	public void deleteContent(String num);
	public List<WishlistDto> getList(String memId);
	public List<SpotreviewDto> getMyreviews(String memNum,int start,int end);
	public List<SpotreviewDto> getRecentreviews(String memNum);
	public String getSpottitle(String contentid);
	public int getTotalCount(String memNum);
	public String getShareSubject(String shareNum);
	public int getWishTotalCount(String memId,String category);
}
