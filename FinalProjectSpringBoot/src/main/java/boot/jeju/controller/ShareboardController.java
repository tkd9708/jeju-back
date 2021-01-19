package boot.jeju.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import boot.jeju.data.ShareboardDto;
import boot.jeju.mapper.ShareboardMapper;



@RestController
@CrossOrigin
//@MultipartConfig
//(
//location = "C:\\Users\\tkd97\\git\\jeju-back\\FinalProjectSpringBoot\\src\\main\\webapp\\WEB-INF\\photo",        // 공용 파일을 통해 경로를 하나로 관리한다.
//fileSizeThreshold = 1024 * 1024 * 512,        // 512MB : maxRequestSize 보다 높게 설정하여 임시파일을 하드디스크에 기록하지 않도록 만든다.
//maxRequestSize = 1024 * 1024 * 20,            // 20MB : 연결이 끊어져도 될만한 충분한 용량을 설정한다. 다중파일을 받는다면 해당만큼 올려준다.
//maxFileSize = 1024 * 1024 * 12                // 12MB : 10메가 정도 받는다는 생각으로 12메가를 선언했다.
//)
public class ShareboardController {
	@Autowired
	ShareboardMapper mapper;
	
	MultipartFile upload;
	String photoname;
	
	@GetMapping("/share/count")
	 public int getTotalCount() {
		return mapper.getTotalCount();
	 }
	
	  @GetMapping("/share/list") 
	  public List<ShareboardDto> shareList(@RequestParam int start,@RequestParam int perPage){
		return mapper.getList(start, perPage);
	  
	  }
	  
	  @PostMapping(value = "/share/upload", consumes = {"multipart/form-data"})
		public Map<String, String> fileUpload(@RequestParam MultipartFile uploadFile, HttpServletRequest request){
			String uploadPath = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
			System.out.println(uploadPath);
			
			// 이미지의 확장자 가져오기
			int pos = uploadFile.getOriginalFilename().lastIndexOf("."); // 마지막 도트의 위치
			String ext = uploadFile.getOriginalFilename().substring(pos);
			
			// 저장할 이미지명 변경하기
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			photoname = "jeju" + sdf.format(date) + ext;
			
			upload = uploadFile;
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("photoname", photoname);
			return map;
		}
	  
	  @PostMapping(value = "/share/insert")
	    public void insert(
	    		@RequestParam(value="num",defaultValue = "0") String num,
	           @RequestParam(value="regroup",defaultValue = "0") int regroup,
	           @RequestParam(value="restep",defaultValue = "0") int restep,
	           @RequestParam(value="relevel",defaultValue = "0") int relevel,
	           @RequestBody ShareboardDto dto,
	           HttpServletRequest request)
	        {
	        //ShareboardDto dto=new ShareboardDto();
	     
	        System.out.println(dto.getAddr());
	        
	        if(photoname == null) {
	        	dto.setPhoto("no");
	        }
	        else {
	        	String path=request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
	            System.out.println(path);
	          
	            // 저장폴더에 저장
				try {
					upload.transferTo(new File(path + "\\" + photoname));
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           
	           dto.setPhoto(photoname);
	        }
	        	
	           
	        
	        if(dto.getNum() == null) {
	           regroup=mapper.getMaxNum()+1;
	           restep=0;
	           relevel=0;
	        }else {
	           mapper.updateRestep(regroup, restep);
	           
	           relevel+=1;
	           restep+=1;            
	             
	        }
	        if(dto.getRelevel()!=0) {
	           dto.setSubject("no"); 
	           dto.setAddr("no");  
	           dto.setLikes(0);
	            dto.setStar("0");
	            
	            dto.setRegroup(regroup);
	           dto.setRestep(restep);
	        }else {
	           dto.setLikes(0);
	           dto.setRegroup(regroup);
	           dto.setRelevel(relevel);
	           dto.setRestep(restep);
	           dto.setId(request.getParameter("id"));
	        }
	        mapper.insertBoard(dto);
	  }
	  
//	  @RequestMapping(value = "/share/insert", method = RequestMethod.POST)
//	    public void insert(@RequestParam(value="num",defaultValue = "0") String num,
//	           @RequestParam(value="regroup",defaultValue = "0") int regroup,
//	           @RequestParam(value="restep",defaultValue = "0") int restep,
//	           @RequestParam(value="relevel",defaultValue = "0") int relevel,
//	           @RequestParam MultipartFile upload, HttpServletRequest request)
//	     {
//	        ShareboardDto dto=new ShareboardDto();
//	        
//	        System.out.println(request.getParameter("addr"));
//	        if(upload.isEmpty())
//	           dto.setPhoto("no");
//	        else {
//	           String path=request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
//	           System.out.println(path);
//	           
//	           
//	           
//	           int pos=upload.getOriginalFilename().lastIndexOf(".");
//	           String ext=upload.getOriginalFilename().substring(pos);
//	           Date date=new Date();
//	           SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//	           photoname="jeju"+sdf.format(date)+ext;
//	           //this.upload=upload;
//	           try {
//	            upload.transferTo(new File(path+"\\"+photoname));
//	         } catch (IllegalStateException | IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	         }
//	           
//	           
//	           dto.setPhoto(photoname);
//	         }
//	           
//	        
//	        if(num.equals("0")) {
//	           regroup=mapper.getMaxNum()+1;
//	           restep=0;
//	           relevel=0;
//	        }else {
//	           mapper.updateRestep(regroup, restep);
//	           
//	           relevel+=1;
//	           restep+=1;
//	            
//	            dto.setRelevel(relevel);
//	             
//	        }
//	        if(dto.getRelevel()!=0) {
//	           dto.setSubject("no"); 
//	           dto.setAddr("no");  
//	           dto.setLikes(0);
//	            dto.setStar("0");
//	            dto.setId(request.getParameter("id"));
//	            dto.setContent(request.getParameter("content"));
//	            
//	            dto.setRegroup(regroup);
//	           dto.setRestep(restep);
//	        }else {
//	           dto.setSubject(request.getParameter("subject"));
//	           dto.setContent(request.getParameter("content"));
//	           dto.setAddr(request.getParameter("addr"));
//	           dto.setLikes(0);
//	           dto.setStar(request.getParameter("star"));
//	           dto.setRegroup(regroup);
//	           dto.setRelevel(relevel);
//	           dto.setRestep(restep);
//	           dto.setId(request.getParameter("id"));
//	        }
//	        mapper.insertBoard(dto);
//	  }
	  
	  
	  
	  @GetMapping("/share/select")
	  public ShareboardDto getData(@RequestParam String num) {
		  return mapper.getData(num);
	  }
	  
	  @GetMapping("/share/answer")
	  public List<ShareboardDto> getReviewList(@RequestParam int regroup){
		  return mapper.getReviewList(regroup);
	  }
	  
	  @GetMapping("/share/updatelikes")
	  public void updatelikes(@RequestParam String num) {
		   mapper.updateLikes(num);
	  }
	  
	  @GetMapping("/share/delete")
	  public void sharedelete(@RequestParam String num1,@RequestParam String num2) {
		  mapper.sharedelete(num1, num2);
	  }
	  
	  @GetMapping("/share/deleteanswer")
	  public void deleteReview(@RequestParam String num) {
		  mapper.deleteReview(num);
	  }
	  
	  @GetMapping("/share/answercount")
	  public int getCount(@RequestParam String num) {
		  return mapper.getCount(num);
	  }
	  
	  @GetMapping("/share/update")
	  public void updateShareboard(@RequestBody ShareboardDto dto) {
		  mapper.updateShareBoard(dto);
	  }
	  
	  @GetMapping("/share/search")
	  public List<ShareboardDto> getsearchList(@RequestParam int start,@RequestParam int perPage,@RequestParam String search){
		  return mapper.getSearchList(start, perPage, search);
	  }
	  
	  @GetMapping("/share/searchcount")
	  public int getsearchTotalCount(@RequestParam String search) {
		  return mapper.getSearchTotalCount(search);
	  }
	  
	  @GetMapping("/share/updateanswer")
	  public void updateshareboardanswer(@RequestParam String content,@RequestParam String num) {
		  mapper.updateShareBoardAnswer(content, num);
	  }
	  
	  
}
