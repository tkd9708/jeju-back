package boot.jeju.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boot.jeju.data.WishlistDto;
import boot.jeju.mapper.WishlistMapper;

@RestController
@CrossOrigin
public class WishlistController {
	@Autowired
	WishlistMapper mapper;
	
	@GetMapping("/wish/list")
	public List<WishlistDto> getlist(@RequestParam String memId) {
		return mapper.getList(memId);
	}
}
