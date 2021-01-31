package boot.jeju.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import boot.jeju.mapper.ShareboardMapper;
import boot.jeju.mapper.SpotlistMapper;
import boot.jeju.mapper.WishlistMapper;


@RestController
@CrossOrigin
public class WishlistController {
	@Autowired
	WishlistMapper mapper;
	
	@Autowired
	SpotlistMapper spotMapper;
	
	@Autowired
	ShareboardMapper shareMapper;
	

	public class Daylist {
		private String num;
		private String title;
		private String content;
		
		public void setNum(String num) {
			this.num = num;
		}
		public String getNum() {
			return num;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
	}

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
	public List<Daylist> getDayMyto(@RequestParam String memId, @RequestParam String day){
		List<WishlistDto> list = mapper.getDayMyto(memId, day);
		List<Daylist> result = new ArrayList<Daylist>();
		
		for(WishlistDto dto : list) {
			Daylist dlist = new Daylist();
			dlist.setNum(dto.getNum());
			if(dto.getSpotId()!=null) {
				dlist.setTitle(spotMapper.getData(dto.getSpotId()).getTitle());
				dlist.setContent("spot");
			}
			else if(dto.getShareNum()!=null) {
				dlist.setTitle(shareMapper.getData(dto.getShareNum()).getSubject());
				dlist.setContent("share");
			}
			else if(dto.getAroundId() !=null) {
				dlist.setTitle(dto.getAroundId());
				dlist.setContent(dto.getContent());
			}
			else {
				dlist.setTitle(dto.getContent());
				dlist.setContent("myplan");
			}
			result.add(dlist);
		}
		return result;
	}
	
	@GetMapping("/wish/planlist")
	public List<WishlistDto> getPlanList(@RequestParam String memId, @RequestParam String day, @RequestParam String category, 
			@RequestParam int perPage){
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//		String nowTime = sdf.format(date);
		
		List<WishlistDto> list = mapper.getPlanList(memId, day, category, perPage);
		
		return list;
	}
}
