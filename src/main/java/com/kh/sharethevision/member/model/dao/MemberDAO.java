package com.kh.sharethevision.member.model.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.sharethevision.member.model.vo.Member;


@Repository("dao")
public class MemberDAO {

	public Member login(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectOne("memberMapper.login",m);
	}

	public int enroll(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.enroll",m);
	}

	public int updateMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updateMember",m);
	}

	public int updatePwd(SqlSessionTemplate sqlSession, HashMap<String, String> hash) {
		return sqlSession.update("memberMapper.updatePwd",hash);
	}

	public int deleteMember(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.update("memberMapper.deleteMember",id);
	}

	public int checkId(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("memberMapper.checkId",id);
	}

}
