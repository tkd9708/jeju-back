package boot.jeju.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boot.jeju.data.SpotreviewDto;
import boot.jeju.data.WishlistDto;
import boot.jeju.mapper.WishlistMapper;

@RestController
@CrossOrigin
public class WishlistController {
	@Autowired
	WishlistMapper mapper;
	
	@PostMapping("/wish/insertaround")
	public void insertAround(@RequestBody WishlistDto dto) {
		 mapper.insertAround(dto);
	}
	
	@PostMapping("/wish/insertspot")
	public void insertSpot(@RequestBody WishlistDto dto) {
		mapper.insertSpot(dto);
	}
	@GetMapping("/wish/isspot")
	public boolean isspotsearch(@RequestParam String contentsid,@RequestParam String id) {
		boolean find=false;
		if(mapper.isSpotSearch(contentsid, id)!=0)
			find=true;
		return find;
		 
	}
	
	@GetMapping("/wish/isshare")
	public boolean issharesearch(@RequestParam String num) {
		boolean find=false;
		if(mapper.isShareSearch(num)!=0)
			find=true;
		
		return find;
	}
	
	@PostMapping("/wish/insertcontent")
	public void insertcontent(@RequestBody WishlistDto dto) {
		mapper.insertContent(dto);
	}
	
	@PostMapping("/wish/insertshare")
	public void insertshare(@RequestBody WishlistDto dto) {
		mapper.insertshare(dto);
	}
	@GetMapping("/wish/delete")
	public void deletecontent(@RequestParam String num) {
		mapper.deleteContent(num);
	}
	
	@GetMapping("/wish/list")
	public List<WishlistDto> getList(@RequestParam String memId){
		return mapper.getList(memId);
	}
	
	@GetMapping("/wish/myreview")
	public List<SpotreviewDto> getMyreviews(@RequestParam String memNum,@RequestParam int start,@RequestParam int end){
		return mapper.getMyreviews(memNum, start, end);
	}
	@GetMapping("/wish/recentreview")
	public List<SpotreviewDto> getRecentreviews(@RequestParam String memNum){
		return mapper.getRecentreviews(memNum);
	}
	
	@GetMapping("/wish/spottitle")
	public String getSpottitle(@RequestParam String contentsid) {
		return mapper.getSpottitle(contentsid);
	}
	@GetMapping("/wish/count")
	public int getTotalCount(@RequestParam String memNum) {
		return mapper.getTotalCount(memNum);
	}
	@GetMapping("/wish/sharesubject")
	public String getsharesubject(@RequestParam String num) {
		return mapper.getShareSubject(num);
	}
	@GetMapping("/wish/wishcount")
	public int getWishtotalCount(@RequestParam String memId) {
		return mapper.getWishTotalCount(memId);
	}
	
	@GetMapping("/wish/daylist")
	public List<WishlistDto> getDayMyto(@RequestParam String memId, @RequestParam String day){
		return mapper.getDayMyto(memId, day);
	}
}
