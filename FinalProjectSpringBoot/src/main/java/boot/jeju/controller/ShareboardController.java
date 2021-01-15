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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import boot.jeju.data.ShareboardDto;
import boot.jeju.mapper.ShareboardMapper;



@RestController
@CrossOrigin
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
	  
	
	  
	  @PostMapping("/share/insert")
	  public Map<String, String> insert(@RequestParam(value="num",defaultValue = "0") String num,
			  @RequestParam(value="regroup",defaultValue = "0") int regroup,
			  @RequestParam(value="restep",defaultValue = "0") int restep,
			  @RequestParam(value="relevel",defaultValue = "0") int relevel,
			  @RequestBody ShareboardDto dto,
			  @RequestParam MultipartFile upload,
			  HttpServletRequest request)
	  {
		  
		  
		  
		  String path=request.getSession().getServletContext().getRealPath("/photo");
		  System.out.println(path);
		  
		  if(upload==null) {
			photoname="no";
				  
		}
		  
		  int pos=upload.getOriginalFilename().lastIndexOf(".");
		  String ext=upload.getOriginalFilename().substring(pos);
		  Date date=new Date();
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		  photoname="jeju"+sdf.format(date)+ext;
		  this.upload=upload;
		  try {
			upload.transferTo(new File(path+"\\"+photoname));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  if(num.equals("0")) {
			  regroup=mapper.getMaxNum()+1;
			  restep=0;
			  relevel=0;
		  }else {
			  mapper.updateRestep(regroup, restep);
			  relevel+=1;
			  restep+=1;
		  }
		  
		  dto.setRegroup(regroup);
		  dto.setRelevel(relevel);
		  dto.setRestep(restep);
		  dto.setPhoto(photoname);
		  
		  mapper.insertBoard(dto);
		  
		  Map<String,String> map=new HashMap<String, String>();
		  map.put("photo", photoname);
		  return map;
		  
		  
	  }
	  
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
