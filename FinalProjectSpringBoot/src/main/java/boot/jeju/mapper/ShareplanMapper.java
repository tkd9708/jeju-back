package boot.jeju.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import boot.jeju.data.ShareplanDto;

@Mapper
public interface ShareplanMapper {

	public List<ShareplanDto> getList();
	public int getTotalCount();
	public ShareplanDto getData(String num);
	//public List<ShareplanDto> getGroupData(@Param("memId") String memId, @Param("wishday") String wishday);
	public void insert(ShareplanDto dto);
	public void delete(String num);
	public String maxGroupNum();
//	public List<ShareplanDto> getgroup(String groupnum);
	public List<ShareplanDto> getgroupnum();
	public List<ShareplanDto> getgroupdata(String groupnum);
	public List<ShareplanDto> getPlan(String wishday);
	public void groupdelete(String groupnum);
}
