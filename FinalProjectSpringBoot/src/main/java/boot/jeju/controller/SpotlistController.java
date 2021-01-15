package boot.jeju.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	//String uploadImg;
	//String uploadThumbnail;
	
	@GetMapping("/spot/list")
	public List<SpotlistDto> getList(@RequestParam int start, @RequestParam int perPage, 
									@RequestParam String label2, String select){
		return mapper.getList(start, perPage, label2, select);
	}
	
	@GetMapping("/spot/count")
	public int getTotalCount(String label2) {
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
	
	
	@PostMapping(value = "/spot/insert", consumes = {"multipart/form-data"})
	public void insert(@RequestBody SpotlistDto dto, HttpServletRequest request,
			@RequestParam MultipartFile img, @RequestParam MultipartFile thumbnail) {
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
		
		mapper.insert(dto);
		
	}
	
	@PostMapping(value = "/spot/update", consumes = {"multipart/form-data"})
	public void update(@RequestBody SpotlistDto dto, HttpServletRequest request,
			@RequestParam MultipartFile img, @RequestParam MultipartFile thumbnail) {
		
		// 기존 이미지 지우기
		String delImg = mapper.getData(dto.getContentsid()).getImg();
		String delThumbnail = mapper.getData(dto.getContentsid()).getThumbnail();
		
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
		if(!delImg.equals("no")) {
			File file = new File(path + "\\" + delImg);
			if(file.exists())
				file.delete();
		}
		
		if(!delThumbnail.equals("no")) {
			File file = new File(path + "\\" + delThumbnail);
			if(file.exists())
				file.delete();
		}
		
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
	
//	@PostMapping(value = "/spot/imgupload", consumes = {"multipart/form-data"})
//	public void imgUpload(@RequestParam MultipartFile photo, HttpServletRequest request){
//		// 경로
//		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
//		System.out.println(path);
//		
//		// 이미지의 확장자 가져오기
//		int pos = photo.getOriginalFilename().lastIndexOf(".");
//		String ext = photo.getOriginalFilename().substring(pos);
//		
//		// 저장할 이미지명
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		uploadImg = "jeju" + sdf.format(date) + ext;
//		
//		try {
//			photo.transferTo(new File(path + "\\" + uploadImg));
//		} catch (IllegalStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	@PostMapping(value = "/spot/thumbnailupload", consumes = {"multipart/form-data"})
//	public void thumbnailUpload(@RequestParam MultipartFile photo, HttpServletRequest request){
//		// 경로
//		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
//		System.out.println(path);
//		
//		// 이미지의 확장자 가져오기
//		int pos = photo.getOriginalFilename().lastIndexOf(".");
//		String ext = photo.getOriginalFilename().substring(pos);
//		
//		// 저장할 이미지명
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		uploadThumbnail = "jeju" + sdf.format(date) + ext;
//		
//		try {
//			photo.transferTo(new File(path + "\\" + uploadThumbnail));
//		} catch (IllegalStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
