package boot.jeju.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import boot.jeju.data.SpotlistDto;

@Mapper
public interface SpotlistMapper {
	public List<SpotlistDto> getList();
	public SpotlistDto getData(String contentsid);
}