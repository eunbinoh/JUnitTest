package com.kh.sharethevision.member.model.service;

import java.util.HashMap;

import com.kh.sharethevision.member.model.vo.Member;

public interface MemberService {

		Member login(Member m);

		int enroll(Member m);

		int updateMember(Member m);

		int updatePwd(HashMap<String, String> hash);

		int deleteMember(String id);

		int checkId(String id);
		
		

}
