package com.kh.sharethevision.member.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.kh.sharethevision.member.model.exception.MemberException;
import com.kh.sharethevision.member.model.service.MemberService;
import com.kh.sharethevision.member.model.vo.Member;




@SessionAttributes("loginUser")
@Controller
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private MemberService service;
	
		@RequestMapping(value="/login.me", method=RequestMethod.POST)
		public String login(Member m, Model model) {
				Member loginUser = service.login(m);
				
				boolean match = bcrypt.matches(m.getPwd(), loginUser.getPwd());
				if(match) {
					model.addAttribute("loginUser",loginUser);
					
					logger.info(loginUser.getId());
					
					
				}else {
					throw new MemberException("로그인 실패");
				}
				return "redirect:home.do";
		}
		
		
		@RequestMapping("/logout.me")
		public String logout(SessionStatus session) {
			session.setComplete();
			return "redirect:home.do";
		}

		@RequestMapping("enrollView.me")
		public String enrollView () {
			
			if(logger.isDebugEnabled()) { //로거 레벨이 debug인지 확인
				logger.debug("회원등록페이지 로거");
			}
			
			
			
			return "memberJoin";
		}
		
		@RequestMapping("minsert.me")
		public String insertMember( @ModelAttribute Member m, 
									@RequestParam("post") String post,
									@RequestParam("address1") String address1,
									@RequestParam("address2") String address2){
			
		m.setAddress(post+"/"+address1+"/"+address2);	
		
		String encPwd = bcrypt.encode(m.getPwd());
		m.setPwd(encPwd);
		
			int result = service.enroll(m);
			if(result>0){
				return "redirect:home.do";
			}else {
				throw new MemberException("회원가입 실패");
			}
		}
	
		@RequestMapping("myinfo.me")
		public String myInfoView() {
			return "mypage";
		}
		
		@RequestMapping("mupdateView.me")
		public String updateForm() {
			return "memberUpdateForm";
		}
		
		
		@RequestMapping("mupdate.me")
		public String updateMember( @ModelAttribute Member m, 
									@RequestParam("post") String post,
									@RequestParam("address1") String address1,
									@RequestParam("address2") String address2,
									Model model){
			
			m.setAddress(post+"/"+address1+"/"+address2);	
			
			int result=service.updateMember(m);
			if(result>0) {
				model.addAttribute("loginUser",m);
				return "redirect:myinfo.me";
			}else {
				throw new MemberException("회원정보 수정 실패");
			}
		}
		
		@RequestMapping("mpwdUpdateView.me")
		public String updatePwdForm() {
			return "memberPwdUpdateForm";
		}
		
		@RequestMapping("mPwdUpdate.me")
		public String updatePwd( HttpSession session, 
								@RequestParam("pwd") String pwd,
								@RequestParam("newPwd1") String newPwd){		
			
			String userId = ((Member)session.getAttribute("loginUser")).getId();
			String userPwd = ((Member)session.getAttribute("loginUser")).getPwd();
			String newEncPwd = bcrypt.encode(newPwd);
			
			HashMap<String,String> hash = new HashMap<String,String>();
			hash.put("userId", userId);
			hash.put("userPwd", newEncPwd);
			
			int result=0;
			
			if(bcrypt.matches(pwd, userPwd)) {
				result=service.updatePwd(hash);
					if(result>0) {
						return "redirect:myinfo.me";
					}else {
						throw new MemberException("비밀번호 변경 실패");
					}
			}else {
				throw new MemberException("비밀번호 변경 에러");
			}
		}
	
		@RequestMapping("mdelete.me")
		public String deleteMember(@RequestParam("id") String id, SessionStatus session) {
			int result = service.deleteMember(id);
			if(result>0) {
				session.setComplete();
			}else {
				throw new MemberException("실패");
			}
			return "redirect:home.do";
		}
		
		
		@RequestMapping("dupid.me")
		public void checkId(@RequestParam("userId") String id, HttpServletResponse response) {
			
			int result = service.checkId(id);
			boolean bool = result == 0? true: false;

//			boolean bool;
//			if(result==0) {
//				bool = true;
//			}else {
//				bool = false;
//			}
			
			try {
				response.getWriter().println(bool);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
}


