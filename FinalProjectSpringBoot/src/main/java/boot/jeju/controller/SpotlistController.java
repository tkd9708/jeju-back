package boot.jeju.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import boot.jeju.data.SpotlistDto;
import boot.jeju.mapper.SpotlistMapper;
import boot.jeju.mapper.SpotreviewMapper;

@RestController
@CrossOrigin
public class SpotlistController {
	
	@Autowired
	SpotlistMapper mapper;
	
	@Autowired
	SpotreviewMapper reviewMapper;
	
	// github 테스트테스트
	
	@GetMapping("/spot/list")
	public List<SpotlistDto> getList(@RequestParam int start, @RequestParam int perPage, 
									@RequestParam String label2, @RequestParam(defaultValue = "star") String select){
		switch(label2) {
		case "jeju" : 
			label2 = "제주";
			break;
		case "jocheon" : 
			label2 = "조천";
			break;
		case "gujwa" : 
			label2 = "구좌";
			break;
		case "sungsan" : 
			label2 = "성산";
			break;
		case "pyoseon" : 
			label2 = "표선";
			break;
		case "namwon" : 
			label2 = "남원";
			break;
		case "seogwipo" : 
			label2 = "서귀포";
			break;
		case "andeok" : 
			label2 = "안덕";
			break;
		case "daejung" : 
			label2 = "대정";
			break;
		case "hangyeong" : 
			label2 = "한경";
			break;
		case "hanrim" : 
			label2 = "한림";
			break;
		case "aewol" : 
			label2 = "애월";
			break;
		case "udo" : 
			label2 = "우도";
			break;
		}
		
		return mapper.getList(start, perPage, label2, select);
	}
	
	@GetMapping("/spot/count")
	public int getTotalCount(String label2) {
		switch(label2) {
		case "jeju" : 
			label2 = "제주";
			break;
		case "jocheon" : 
			label2 = "조천";
			break;
		case "gujwa" : 
			label2 = "구좌";
			break;
		case "sungsan" : 
			label2 = "성산";
			break;
		case "pyoseon" : 
			label2 = "표선";
			break;
		case "namwon" : 
			label2 = "남원";
			break;
		case "seogwipo" : 
			label2 = "서귀포";
			break;
		case "andeok" : 
			label2 = "안덕";
			break;
		case "daejung" : 
			label2 = "대정";
			break;
		case "hangyeong" : 
			label2 = "한경";
			break;
		case "hanrim" : 
			label2 = "한림";
			break;
		case "aewol" : 
			label2 = "애월";
			break;
		case "udo" : 
			label2 = "우도";
			break;
		}
		
		return mapper.getTotalCount(label2);
	}
	
	@GetMapping("/spot/searchlist")
	public List<SpotlistDto> getSearchList(@RequestParam int start, @RequestParam int perPage, 
										@RequestParam String category, String search){
		return mapper.getSearchList(start, perPage, category, search);
	}
	
	@GetMapping("/spot/searchcount")
	public int getSearchTotalCount(@RequestParam String category, @RequestParam String search) {
		return mapper.getSearchTotalCount(category, search);
	}
	
	@GetMapping("/spot/select")
	public SpotlistDto getData(@RequestParam String contentsid) {
		return mapper.getData(contentsid);
	}
	
	
	@PostMapping(value = "/spot/insert")
	public void insert(HttpServletRequest request, @RequestParam MultipartFile img, @RequestParam MultipartFile thumbnail) {
		
		/* img, thumbnail은 required로 무조건 이미지 설정하기 */
		
		SpotlistDto dto = new SpotlistDto();
		// 경로
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
		System.out.println(path);
		
		// 이미지의 확장자 가져오기
		int pos = img.getOriginalFilename().lastIndexOf(".");
		String ext = img.getOriginalFilename().substring(pos);
		
		// 저장할 이미지명
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String uploadImg = "img" + sdf.format(date) + ext;
		
		pos = thumbnail.getOriginalFilename().lastIndexOf(".");
		ext = thumbnail.getOriginalFilename().substring(pos);
		String uploadThumbnail = "thumbnail" + sdf.format(date) + ext;
		
		try {
			img.transferTo(new File(path + "\\" + uploadImg));
			thumbnail.transferTo(new File(path + "\\" + uploadThumbnail));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dto.setImg(uploadImg);
		dto.setThumbnail(uploadThumbnail);
		
		dto.setAddr(request.getParameter("addr"));
		dto.setContentsid(request.getParameter("contentsid"));
		dto.setLabel1(request.getParameter("label1"));
		dto.setIntroduction(request.getParameter("introduction"));
		dto.setLabel2(request.getParameter("label2"));
		dto.setLatitude(request.getParameter("latitude"));
		dto.setLongitude(request.getParameter("longitude"));
		dto.setRoadaddr(request.getParameter("roadaddr"));
		dto.setTag(request.getParameter("tag"));
		dto.setTitle(request.getParameter("title"));
		
		mapper.insert(dto);
		
	}
	
	@PostMapping(value = "/spot/update")
	public void update(HttpServletRequest request, @RequestParam MultipartFile img, @RequestParam MultipartFile thumbnail) {
		
		SpotlistDto dto = new SpotlistDto();
		dto.setContentsid(request.getParameter("contentsid"));
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		if(img.isEmpty()) {
			dto.setImg(null);
		}
		else {
			// 기존 이미지 지우기
			String delImg = mapper.getData(dto.getContentsid()).getImg();
			File file = new File(path + "\\" + delImg);
			if(file.exists())
				file.delete();
			
			// 이미지의 확장자 가져오기
			int pos = img.getOriginalFilename().lastIndexOf(".");
			String ext = img.getOriginalFilename().substring(pos);
			
			// 저장할 이미지명
			
			String uploadImg = "img" + sdf.format(date) + ext;
			
			try {
				img.transferTo(new File(path + "\\" + uploadImg));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dto.setImg(uploadImg);
		}
		
		if(thumbnail.isEmpty()) {
			dto.setThumbnail(null);
		}
		else {
			// 기존 이미지 지우기
			String delThumbnail = mapper.getData(dto.getContentsid()).getThumbnail();
			File file = new File(path + "\\" + delThumbnail);
			if(file.exists())
				file.delete();
						
			int pos = thumbnail.getOriginalFilename().lastIndexOf(".");
			String ext = thumbnail.getOriginalFilename().substring(pos);
			String uploadThumbnail = "thumbnail" + sdf.format(date) + ext;
			
			try {
				thumbnail.transferTo(new File(path + "\\" + uploadThumbnail));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dto.setThumbnail(uploadThumbnail);
		}
		
		dto.setAddr(request.getParameter("addr"));
		dto.setLabel1(request.getParameter("label1"));
		dto.setIntroduction(request.getParameter("introduction"));
		dto.setLabel2(request.getParameter("label2"));
		dto.setLatitude(request.getParameter("latitude"));
		dto.setLongitude(request.getParameter("longitude"));
		dto.setRoadaddr(request.getParameter("roadaddr"));
		dto.setTag(request.getParameter("tag"));
		dto.setTitle(request.getParameter("title"));
	
		mapper.update(dto);
		
	}
	
	@GetMapping("/spot/delete")
	public void delete(@RequestParam String contentsid, HttpServletRequest request) {
		String img = mapper.getData(contentsid).getImg();
		String thumbnail = mapper.getData(contentsid).getThumbnail();
		
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
		if(!img.equals("no")) {
			File file = new File(path + "\\" + img);
			if(file.exists())
				file.delete();
		}
		
		if(!thumbnail.equals("no")) {
			File file = new File(path + "\\" + thumbnail);
			if(file.exists())
				file.delete();
		}
		
		mapper.delete(contentsid);
	}
	
	@GetMapping("/spot/updatelikes")
	public void updateLikes(@RequestParam String contentsid) {
		mapper.updateLikes(contentsid);
	}
	
	@GetMapping("/spot/updatestar")
	public void updateStar(@RequestParam String contentsid) {
		int avgStar = reviewMapper.getAvgStar(contentsid) / reviewMapper.getTotalCount(contentsid);
		mapper.updateStar(contentsid, avgStar);
	}
	
}
