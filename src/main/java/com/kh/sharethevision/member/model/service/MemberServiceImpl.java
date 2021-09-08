package com.kh.sharethevision.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.kh.sharethevision.member.model.dao.MemberDAO;
import com.kh.sharethevision.member.model.vo.Member;
import java.util.HashMap;

@Service("service")
@Component
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MemberDAO dao;
		
		@Override
		public Member login(Member m) {
			return dao.login(sqlSession, m);
		}
	
		@Override
		public int enroll(Member m) {
			return dao.enroll(sqlSession, m);
		}
	
		@Override
		public int updateMember(Member m) {
			return dao.updateMember(sqlSession, m);
		}
	
	
		@Override
		public int updatePwd(HashMap<String, String> hash) {
			return dao.updatePwd(sqlSession,hash);
		
		}
	
		@Override
		public int deleteMember(String id) {
			return dao.deleteMember(sqlSession,id);
			
		}

		@Override
		public int checkId(String id) {
			return dao.checkId(sqlSession, id);
		}

}
