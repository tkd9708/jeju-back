package boot.jeju.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import boot.jeju.data.SpotreviewDto;
import boot.jeju.mapper.SpotreviewMapper;

@CrossOrigin
@RestController
public class SpotreviewController {
	
	@Autowired
	SpotreviewMapper mapper;
	
	@GetMapping("/sreview/list")
	public List<SpotreviewDto> getList(String contentsid){
		return mapper.getList(contentsid);
	}
}