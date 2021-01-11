package boot.jeju.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import boot.jeju.data.SpotlistDto;
import boot.jeju.mapper.SpotlistMapper;

@RestController
@CrossOrigin
public class SpotlistController {
	
	@Autowired
	SpotlistMapper mapper;
	
	@GetMapping("/spot/list")
	public List<SpotlistDto> getList(){
		return mapper.getList();
	}
}
