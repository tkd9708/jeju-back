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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import boot.jeju.data.SpotreviewDto;
import boot.jeju.mapper.SpotreviewMapper;

@CrossOrigin
@RestController
public class SpotreviewController {
	
	@Autowired
	SpotreviewMapper mapper;
	
	@GetMapping("/sreview/list")
	public List<SpotreviewDto> getList(@RequestParam int start, @RequestParam int perPage, @RequestParam String contentsid){
		
		return mapper.getList(start, perPage, contentsid);
	}
	
	@GetMapping("/sreview/select")
	public SpotreviewDto getData(@RequestParam String num) {
		return mapper.getData(num);
	}
	
	@GetMapping("/sreview/count")
	public int getTotalCount(@RequestParam String contentsid) {
		return mapper.getTotalCount(contentsid);
	}
	
	@PostMapping(value = "/sreview/insert")
	public void insert(@RequestParam MultipartFile photo, HttpServletRequest request) {
		
		SpotreviewDto dto = new SpotreviewDto();
		if(photo.isEmpty())
			dto.setPhoto("no");
		else {
			
			String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
			System.out.println(path);
			
			// 이미지의 확장자 가져오기
			int pos = photo.getOriginalFilename().lastIndexOf(".");
			String ext = photo.getOriginalFilename().substring(pos);
			
			// 저장할 이미지명
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String photoname = "jeju" + sdf.format(date) + ext;
			
			try {
				System.out.println(photoname);
				System.out.println(photo);
				
				photo.transferTo(new File(path + "\\" + photoname));
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dto.setPhoto(photoname);
			
		}
		
		dto.setContent(request.getParameter("content"));
		dto.setMemNum(request.getParameter("memNum"));
		dto.setStar(Integer.parseInt(request.getParameter("star")));
		mapper.insert(dto);
	}
	
	@PostMapping(value = "/sreview/update")
	public void update(@RequestParam MultipartFile photo, HttpServletRequest request) {
	
		SpotreviewDto dto = new SpotreviewDto();
		dto.setNum(request.getParameter("num"));
		
		if(photo.isEmpty()) {
			dto.setPhoto(null); // 이미지 변경 안할 시 null로 입력
		}
		else {
			// 기존 이미지 지우기
			String deletePhoto = mapper.getData(dto.getNum()).getPhoto();
			
			if(!deletePhoto.equals("no")) { // 기존 이미지가 존재할 경우 삭제
				String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
				File file = new File(path + "\\" + deletePhoto);
			
				if(file.exists())
					file.delete();
			}
			
			String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
			System.out.println(path);
			
			// 이미지의 확장자 가져오기
			int pos = photo.getOriginalFilename().lastIndexOf(".");
			String ext = photo.getOriginalFilename().substring(pos);
			
			// 저장할 이미지명
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String photoname = "jeju" + sdf.format(date) + ext;
			
			try {
				
				photo.transferTo(new File(path + "\\" + photoname));
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dto.setPhoto(photoname);
		}
		
		dto.setContent(request.getParameter("content"));
		dto.setContentsid(request.getParameter("contentsid"));
		dto.setStar(Integer.parseInt(request.getParameter("star")));
		
		mapper.update(dto);
	}
	
	@GetMapping("/sreview/delete")
	public void delete(@RequestParam String num, HttpServletRequest request) {
		String deletePhoto = mapper.getData(num).getPhoto();
		
		if(!deletePhoto.equals("no")) {
			String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photos");
			File file = new File(path + "\\" + deletePhoto);
		
			if(file.exists())
				file.delete();
		}
		mapper.delete(num);
	}
	
	@GetMapping("/sreview/updatelikes")
	public void updateLikes(@RequestParam String num) {
		mapper.updateLikes(num);
	}
	
	@GetMapping("/sreview/avgstar")
	public int getAvgStar(@RequestParam String contentsid) {
		int count = this.getTotalCount(contentsid);
		
		if(count == 0)
			return 0;
		else 
			return (int)mapper.getAvgStar(contentsid)/count;
	}
	
}