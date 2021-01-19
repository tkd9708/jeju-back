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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import boot.jeju.data.MemberDto;
import boot.jeju.mapper.MemberMapper;

@RestController
@CrossOrigin
public class MemberController {

	@Autowired
	MemberMapper mapper;
	//이미지명을 저장할 멤버변수
	String photoName;
	
	@GetMapping("/member/list")
	public List<MemberDto> getList(){
		return mapper.getListOfMember();
	}
	
	@GetMapping("/member/count")
	public int getTotalCount() {
		return mapper.totalCountOfMember();
	}
	
	@PostMapping(value = "/member/insert")
	public void insert(@RequestParam MultipartFile photo,
			HttpServletRequest request)
	{
		MemberDto dto = new MemberDto();
		if(photo.isEmpty())
			dto.setPhoto("no");
		else {
			//이미지 저장경로 구하기
			String path=request.getSession().getServletContext().getRealPath("/WEB-INF/photo/member");
			System.out.println(path);
			//이미지의 확장자 가져오기
			int pos=photo.getOriginalFilename().lastIndexOf(".");//마지막 도트의 위치
			String ext=photo.getOriginalFilename().substring(pos);//예  [.jpg] 형태로 얻음
			//저장할 이미지명 변경하기
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			String photoname="member"+sdf.format(date)+ext;	
			
			try {
				//이미지를 photo 폴더에 저장하기
				photo.transferTo(new File(path+"\\"+photoname));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dto.setPhoto(photoname);//db에 저장할 파일명(실제 업로드된 파일명)
		}
		//db 에 저장
		mapper.insertOfMember(dto);
	}
	
	@GetMapping("/member/delete")
	public void delete(@RequestParam String num,
			HttpServletRequest request)
	{	
		String path=request.getSession().getServletContext().getRealPath("/photo/member");
		System.out.println(path);
		String deleteFileName=mapper.getDataOfMember(num).getPhoto();
		if(deleteFileName!=null)
		{
			File file=new File(path+"\\"+deleteFileName);
			if(file.exists())
				file.delete();//업로드했던 이미지 삭제
		}

		mapper.deleteOfMember(num);
	}
	
	@GetMapping("/member/detail")
	public MemberDto getData(@RequestParam String num)
	{
		return mapper.getDataOfMember(num);
	}
	
	@PostMapping(value = "/member/update")
	public void update(@RequestParam MultipartFile photo,
			HttpServletRequest request)
	{
		MemberDto dto = new MemberDto();
		if(photo.isEmpty())
			dto.setPhoto(null);
		else {
			// 기존 이미지 지우기
				String deletePhoto = mapper.getDataOfMember(dto.getNum()).getPhoto();
					
				if(!deletePhoto.equals("no")) { // 기존 이미지가 존재할 경우 삭제
					String path = request.getSession().getServletContext().getRealPath("/WEB-INF/photo");
					File file = new File(path + "\\" + deletePhoto);
				
					if(file.exists())
						file.delete();
				}
			//이미지 저장경로 구하기
			String path=request.getSession().getServletContext().getRealPath("/WEB-INF/photo/member");
			System.out.println(path);
			//이미지의 확장자 가져오기
			int pos=photo.getOriginalFilename().lastIndexOf(".");//마지막 도트의 위치
			String ext=photo.getOriginalFilename().substring(pos);//예  [.jpg] 형태로 얻음
			//저장할 이미지명 변경하기
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			String photoname="member"+sdf.format(date)+ext;	
			
			try {
				//이미지를 photo 폴더에 저장하기
				photo.transferTo(new File(path+"\\"+photoname));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dto.setPhoto(photoname);//db에 저장할 파일명(실제 업로드된 파일명)
		}
		//db 에 저장
		mapper.updateOfMember(dto);
	}
	
	@PostMapping("/member/updatepass")
	public void updatePass(@ModelAttribute MemberDto dto)
	{
		mapper.updateOfPass(dto);
	}
}