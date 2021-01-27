package boot.jeju.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boot.jeju.data.NoticeDto;
import boot.jeju.mapper.NoticeMapper;

@RestController
@CrossOrigin
public class NoticeController {

	@Autowired
	NoticeMapper mapper;
	
	@GetMapping("/notice/list")
	public List<NoticeDto> getList(){
		return mapper.getListOfNotice();
	}
	
	@GetMapping("/notice/count")
	public int getTotalCount() {
		return mapper.totalCountOfNotice();
	}
	
	@PostMapping("/notice/insert")
	public void insert(@RequestBody NoticeDto dto)
	{
		//db 에 저장
		mapper.insertOfNotice(dto);
	}
	
	@GetMapping("/notice/delete")
	public void delete(@RequestParam String num)
	{	
		mapper.deleteOfNotice(num);
	}
	
	@GetMapping("/notice/detail")
	public NoticeDto getData(@RequestParam String num)
	{
		return mapper.getDataOfNotice(num);
	}
	
	@PostMapping("/notice/update")
	public void update(@RequestParam NoticeDto dto)
	{
		mapper.updateOfNotice(dto);
	}
	
	@PostMapping("/notice/updatestar")
	public void update(@RequestParam String num)
	{
		mapper.updateOfStar(num);
	}
}
