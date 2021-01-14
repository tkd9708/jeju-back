package boot.jeju.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import boot.jeju.data.NoticeDto;

@Mapper
public interface NoticeMapper {
	public List<NoticeDto> getListOfNotice();
	public NoticeDto getDataOfNotice(String num);
	public void insertOfNotice(NoticeDto dto);
	public void deleteOfNotice(String num);
	public void updateOfNotice(NoticeDto dto);
	public void updateOfStar(String num);
	public int totalCountOfNotice();
}
