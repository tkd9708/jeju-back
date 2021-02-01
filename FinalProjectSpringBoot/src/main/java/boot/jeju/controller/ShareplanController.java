package boot.jeju.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boot.jeju.data.ShareplanDto;
import boot.jeju.mapper.ShareplanMapper;

@RestController
@CrossOrigin
public class ShareplanController {

	@Autowired
	ShareplanMapper mapper;
	
	@GetMapping("/plan/list")
	public List<ShareplanDto> getList(){
		return mapper.getList();
	}
	
	@GetMapping("/plan/count")
	public int getTotalCount() {
		return mapper.getTotalCount();
	}
	
	@GetMapping("/plan/select")
	public ShareplanDto getData(@RequestParam String num) {
		return mapper.getData(num);
	}
	
	@GetMapping("/plan/group")
	public List<ShareplanDto> getGroupData(@RequestParam String memId, @RequestParam String wishday){
		return mapper.getGroupData(memId, wishday);
	}
	
	@PostMapping("/plan/insert")
	public void insert(@RequestBody ShareplanDto dto) {
		mapper.insert(dto);
	}
	
	@GetMapping("/plan/delete")
	public void delete(@RequestParam String num) {
		mapper.delete(num);
	}
}
