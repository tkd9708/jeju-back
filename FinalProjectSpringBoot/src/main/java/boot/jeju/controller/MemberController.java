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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import boot.jeju.data.MemberDto;
import boot.jeju.data.ShareboardDto;
import boot.jeju.mapper.MemberMapper;

@RestController
@CrossOrigin
public class MemberController {

	@Autowired
	MemberMapper mapper;
	//이미지명을 저장할 멤버변수

	MultipartFile upload;
	String photoname;
	String idcanUse="false";

	@GetMapping("/member/list")
	public List<MemberDto> getList(){
		return mapper.getListOfMember();
	}

	@GetMapping("/member/count")
	public int getTotalCount() {
		return mapper.totalCountOfMember();
	}

	@PostMapping(value = "/member/upload", consumes = {"multipart/form-data"})
	public Map<String, String> fileUpload(@RequestParam MultipartFile uploadFile, HttpServletRequest request){
		String uploadPath = request.getSession().getServletContext().getRealPath("");
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
		map.put("photoname", uploadFile.getOriginalFilename());
		return map;
	}

	@GetMapping("/member/delupload")
	public void delUpload(@RequestParam String id, HttpServletRequest request) {
		
		if(mapper.getDataOfMember(id).getPhoto() != "no") {
			MemberDto dto = mapper.getDataOfMember(id);
    		String path = request.getSession().getServletContext().getRealPath("");
            
    		File file = new File(path + mapper.getDataOfMember(id).getPhoto());
            if (file.exists())
                file.delete();
            
            dto.setPhoto("no");
            mapper.updateOfMember(dto);
    	}
		
		photoname = null;
		upload = null;
	}

	@GetMapping("/member/checkid")
	public boolean checkid(@RequestParam String id)
	{
		//중복 아이디 있는지 체크
		if (mapper.idCheckOfMember(id) == 0) {
			return true;
		}
		return false;
	}

	   @PostMapping("/member/login")
	   public boolean login(@RequestBody MemberDto dto)
	   {
	      if (mapper.passCheckOfMember(dto) == 0) {
	         return false;
	      }else
	         return true;
	   }

	@PostMapping(value = "/member/insert")
	public void insert(HttpServletRequest request, @RequestBody MemberDto dto)
	{
		if(photoname == null)
			dto.setPhoto("no");
		else {
			//이미지 저장경로 구하기
			String path=request.getSession().getServletContext().getRealPath("");
			System.out.println(path);

			try {
				//이미지를 photo 폴더에 저장하기
				upload.transferTo(new File(path+photoname));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dto.setPhoto(photoname);//db에 저장할 파일명(실제 업로드된 파일명)
		}
		//db 에 저장
		mapper.insertOfMember(dto);

		upload = null;
		photoname = null;
	}

	@PostMapping("/member/insertsocial")
	public void insertSosial(@RequestBody MemberDto dto)
	{
		if(this.checkid(dto.getId()))
			mapper.insertOfSosialMember(dto);
		else {
			String num = mapper.getDataOfMember(dto.getId()).getNum();
			dto.setNum(num);
			photoname = dto.getPhoto();
			mapper.updateOfMember(dto);
		}
	}

	@PostMapping("/member/delete")
	public boolean delete(@RequestBody MemberDto dto,
				HttpServletRequest request)
	{


		if (mapper.passCheckOfMember(dto) == 0) {
			return false;
		}

		String path=request.getSession().getServletContext().getRealPath("");
		System.out.println(path);
		String deleteFileName=mapper.getDataOfMember(dto.getId()).getPhoto();
		if(deleteFileName!=null)
		{
			File file=new File(path+deleteFileName);
			if(file.exists())
				file.delete();//업로드했던 이미지 삭제
		}

		mapper.deleteOfMember(dto.getId());
		return true;
	}

	@GetMapping("/member/getdata")
	public MemberDto getData(@RequestParam String id)
	{
		return mapper.getDataOfMember(id);
	}

	@PostMapping(value = "/member/update")
	public void update(@RequestBody MemberDto dto, HttpServletRequest request)
	{
		if(photoname == null)
			dto.setPhoto(null);
		else {
			// 기존 이미지 지우기
				String deletePhoto = mapper.getDataOfMember(dto.getId()).getPhoto();

				//이미지 저장경로 구하기
				String path=request.getSession().getServletContext().getRealPath("");
				System.out.println(path);

				if(!deletePhoto.equals("no") && !deletePhoto.substring(0, 3).equals("http")) { // 기존 이미지가 존재할 경우 삭제
					File file = new File(path  + deletePhoto);

					if(file.exists())
						file.delete();
				}


			try {
				//이미지를 photo 폴더에 저장하기
				upload.transferTo(new File(path+photoname));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dto.setPhoto(photoname);//db에 저장할 파일명(실제 업로드된 파일명)
		}
		//db 에 저장
		mapper.updateOfMember(dto);

		upload = null;
		photoname = null;
	}

	@PostMapping("/member/updatepass")
	public void updatePass(@ModelAttribute MemberDto dto)
	{
		mapper.updateOfPass(dto);
	}
	
	@GetMapping("/member/oauthKakao")
	public String oauthKakao(@RequestParam("code") String code)
	{
		System.out.println("Kakao code: " + code);
		return "코드는 : " + code;
	}
	
//	@PostMapping("/member/tokenKakaoUser")
//	public String tokenKakaoUser(@RequestBody)
}
