package boot.jeju.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boot.jeju.data.HotspotDto;
import boot.jeju.mapper.HotspotMapper;
import boot.jeju.mapper.SpotlistMapper;

class Spotdata {
	private String title;
	private String addr;
	private String img;
	private String groupNum;
	private String day;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
}

@RestController
@CrossOrigin
public class HotspotController {
	
	@Autowired
	HotspotMapper mapper;
	
	@Autowired
	SpotlistMapper spotMapper;

	@GetMapping("/hotspot/list")
	public List<Spotdata> getList(@RequestParam String groupNum, @RequestParam String day){
		List<HotspotDto> list = mapper.getList(groupNum, day);
		List<Spotdata> result = new ArrayList<Spotdata>();
		
		for(HotspotDto dto : list) {
			Spotdata spot = new Spotdata();
			System.out.println(dto.getTitle());
			System.out.println(spotMapper.getData(dto.getTitle()));
			spot.setTitle(spotMapper.getData(dto.getTitle()).getTitle());
			spot.setAddr(spotMapper.getData(dto.getTitle()).getAddr());
			spot.setImg(spotMapper.getData(dto.getTitle()).getThumbnail());
			spot.setGroupNum(groupNum);
			spot.setDay(day);
			result.add(spot);
		}
		return result;
	}
}

