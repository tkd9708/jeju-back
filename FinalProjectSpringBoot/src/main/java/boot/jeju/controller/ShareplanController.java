package boot.jeju.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boot.jeju.data.DayListDto;
import boot.jeju.data.ShareplanDto;
import boot.jeju.data.WishlistDto;
import boot.jeju.mapper.ShareplanMapper;
import boot.jeju.mapper.WishlistMapper;

@RestController
@CrossOrigin
public class ShareplanController {

	@Autowired
	ShareplanMapper mapper;
	
	@Autowired
	WishlistController wishController;
	
	@GetMapping("/plan/list")
	public List<ShareplanDto> getList(@RequestParam String wishday){
		return mapper.getList(wishday);
	}
	
//	@GetMapping("/plan/id")
//	public List<ShareplanDto> getId(){
//		return mapper.getmemId();
//	}
	
	@GetMapping("/plan/count")
	public int getTotalCount(@RequestParam String memId) {
		return mapper.getTotalCount(memId);
	}
	
	@GetMapping("/plan/select")
	public ShareplanDto getData(@RequestParam String num) {
		return mapper.getData(num);
	}
	
//	@GetMapping("/plan/group")
//	public List<ShareplanDto> tgetGroupDaa(@RequestParam String memId, @RequestParam String wishday){
//		return mapper.getGroupData(memId, wishday);
//	}
	

	@PostMapping("/plan/insert")
	public void insert(@RequestBody ShareplanDto dto) {
		mapper.insert(dto);
	}
	
	@PostMapping("/plan/groupinsert")
	public void groupInsert(@RequestParam String memId, @RequestParam String wishday,@RequestParam String comment) {
		List<DayListDto> list = wishController.getDayMyto(memId, wishday);
		
		String maxNum = String.valueOf(Integer.parseInt(mapper.maxGroupNum()) + 1);
		
		
		for(DayListDto dto : list) {
			ShareplanDto sdto = new ShareplanDto();
			sdto.setMemId(memId);
			sdto.setGroupNum(maxNum);
			sdto.setTitle(dto.getTitle());
			sdto.setContent(dto.getAddr());
			sdto.setWishday(dto.getWishday());
			sdto.setWishtime(dto.getWishtime());
			sdto.setComment(comment);
			sdto.setWishNum(dto.getNum());
			this.insert(sdto);
		}
	}
	
	@GetMapping("/plan/delete")
	public void delete(@RequestParam String num) {
		mapper.delete(num);
	}
	
	@GetMapping("/plan/groupnum")
	public List<ShareplanDto> getgroupnum() {
		return mapper.getgroupnum();
	}
	
	@GetMapping("/plan/groupdata")
	public List<ShareplanDto> getgroupdata(@RequestParam String groupnum) {
		return mapper.getgroupdata(groupnum);
	}
	
	@GetMapping("/plan/group")
	public List<ShareplanDto> getPlan(@RequestParam String wishday){
		return mapper.getPlan(wishday);
	}
	@GetMapping("/plan/groupdelete")
	public void groupdelete(@RequestParam String groupnum) {
		mapper.groupdelete(groupnum);
	}
	
	@GetMapping("/plan/plancount")
	public int getgroupnumcount(@RequestParam String groupnum) {
		return mapper.getplancount(groupnum);
	}
	
//	@GetMapping("/plan/groupnumlist")
//	public List<ShareplanDto> getgroupnumList(){
//		return mapper.groupnumList();
//	}
//	@GetMapping("/plan/allgroupnum")
//	public List<ShareplanDto> getAllgroupnum(){
//		return mapper.getAllgroupnum();
//	}
}
