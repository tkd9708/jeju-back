package boot.jeju.controller;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;


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

import boot.jeju.data.ShareboardDto;
import boot.jeju.mapper.ShareboardMapper;



@RestController
@CrossOrigin
public class ShareboardController {
	@Autowired
	ShareboardMapper mapper;
	
	//MultipartFile upload;
	String photoname;
	
	@GetMapping("/share/count")
	 public int getTotalCount() {
		return mapper.getTotalCount();
	 }
	
	  @GetMapping("/share/list") 
	  public List<ShareboardDto> shareList(@RequestParam int start,@RequestParam int perPage){
		return mapper.getList(start, perPage);
	  
	  }
	  
	
	  
	  @RequestMapping(value = "/share/insert",method = {RequestMethod.POST},consumes = {"multipart/form-data"})
	  public void insert(@RequestParam(value="num",defaultValue = "0") String num,
			  @RequestParam(value="regroup",defaultValue = "0") int regroup,
			  @RequestParam(value="restep",defaultValue = "0") int restep,
			  @RequestParam(value="relevel",defaultValue = "0") int relevel,
			  @RequestParam MultipartFile upload,
			  
			  HttpServletRequest request)
	  {
		  ShareboardDto dto=new ShareboardDto();
		  try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  System.out.println(dto.getAddr());
		  if(upload.isEmpty())
			  dto.setPhoto("no");
		  else {
			  String path=request.getSession().getServletContext().getRealPath("/photo");
			  System.out.println(path);
			  
			  
			  
			  int pos=upload.getOriginalFilename().lastIndexOf(".");
			  String ext=upload.getOriginalFilename().substring(pos);
			  Date date=new Date();
			  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			  photoname="jeju"+sdf.format(date)+ext;
			  //this.upload=upload;
			  try {
				upload.transferTo(new File(path+"\\"+photoname));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			  
			  dto.setPhoto(photoname);
		   }
		  	
		  
		  if(num.equals("0")) {
			  regroup=mapper.getMaxNum()+1;
			  restep=0;
			  relevel=0;
		  }else {
			  mapper.updateRestep(regroup, restep);
			  
			  relevel+=1;
			  restep+=1;
				
		      dto.setRelevel(relevel);
				 
		  }
		  if(dto.getRelevel()!=0) {
			  dto.setSubject("no"); 
			  dto.setAddr("no");  
			  dto.setLikes(0);
		      dto.setStar("0");
		      dto.setId(request.getParameter("id"));
		      dto.setContent(request.getParameter("content"));
		      
		      dto.setRegroup(regroup);
			  dto.setRestep(restep);
		  }else {
			  dto.setSubject(request.getParameter("subject"));
			  dto.setContent(request.getParameter("content"));
			  dto.setAddr(request.getParameter("addr"));
			  dto.setLikes(0);
			  dto.setStar(request.getParameter("star"));
			  dto.setRegroup(regroup);
			  dto.setRelevel(relevel);
			  dto.setRestep(restep);
			  dto.setId(request.getParameter("id"));
		  }
		  mapper.insertBoard(dto);
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
	  public void sharedelete(@RequestParam int regroup,@RequestParam String num,HttpServletRequest request) {
		  List<ShareboardDto> deletePhotos=mapper.getPhotos(regroup);
		  
			  for(int i=0; i<deletePhotos.size(); i++) {
				  String photos = deletePhotos.get(i).getPhoto();
				  
				  if(!photos.equals("no")) {
				  String path=request.getSession().getServletContext().getRealPath("/photo");
				  //System.out.println(path);
				  File file=new File(path+"\\"+photos);
				  if(file.exists())
					  file.delete();
			     }
			  } 
			  
			  mapper.sharedelete(num,regroup);
		  }
			  
		  

	  
	  @GetMapping("/share/deleteanswer")
	  public void deleteReview(@RequestParam String num,HttpServletRequest request) {
		  String deletePhoto=mapper.getData(num).getPhoto();
		  if(!deletePhoto.equals("no")) { 
			  String path=request.getSession().getServletContext().getRealPath("/photo"); 
			  File file =new File(path+"\\"+deletePhoto); 
			  if(file.exists()) 
				  file.delete(); 
		  } 
		  mapper.deleteReview(num);
	  }
	  
	  @GetMapping("/share/answercount")
	  public int getCount(@RequestParam String num) {
		  return mapper.getCount(num);
	  }
	  
	  @PostMapping(value = "/share/update")
	  public void updateShareboard(@RequestParam MultipartFile upload,HttpServletRequest request) {
		  
		  ShareboardDto dto=new ShareboardDto();
		  dto.setNum(request.getParameter("num"));
		  if(upload.isEmpty())
			  dto.setPhoto(null);
		  else {
			//기존 이미지존재할 경우 지우기
			  String deletePhoto=mapper.getData(dto.getNum()).getPhoto();
			  
			  if(!deletePhoto.equals("no")) {
				  String path=request.getSession().getServletContext().getRealPath("/photo");
				  File file=new File(path+"\\"+deletePhoto);
				  if(file.exists())
					  file.delete();
			  }
				  String path=request.getSession().getServletContext().getRealPath("/photo");
				  //System.out.println(path);
				  
				  int pos=upload.getOriginalFilename().lastIndexOf(".");
				  String ext=upload.getOriginalFilename().substring(pos);
				  Date date=new Date();
				  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
				  photoname="jeju"+sdf.format(date)+ext;
				 
				  try {
					upload.transferTo(new File(path+"\\"+photoname));
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  
		  
			  
			 dto.setPhoto(photoname); 
			  
		  }
		  
		  dto.setSubject(request.getParameter("subject"));
		  dto.setContent(request.getParameter("content"));
		  dto.setAddr(request.getParameter("addr"));
		  dto.setStar(request.getParameter("star"));
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
	  
	  @PostMapping("/share/updateanswer")
	  public void updateshareboardanswer(@RequestParam String content,@RequestParam String num) {
		  mapper.updateShareBoardAnswer(content, num);
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	 
	
	
	
	
	
	
	
	
	
}
