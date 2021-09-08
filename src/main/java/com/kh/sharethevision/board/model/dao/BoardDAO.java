package com.kh.sharethevision.board.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.sharethevision.board.model.vo.Board;
import com.kh.sharethevision.board.model.vo.PageInfo;
import com.kh.sharethevision.board.model.vo.Reply;

@Repository("bDAO")
public class BoardDAO {

	public int listCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("boardMapper.listCount");
	}

	public ArrayList<Board> selectList(SqlSessionTemplate sqlSession, PageInfo pi) {
		
		int offset = pi.getboardLimit()*(pi.getCurrentPage()-1);
		RowBounds rb = new RowBounds(offset, pi.getboardLimit());
		
		return (ArrayList)sqlSession.selectList("boardMapper.selectList",null,rb);
	}

	public int insertBoard(SqlSessionTemplate sqlSession, Board b) {
		return sqlSession.insert("boardMapper.insertBoard",b);
	}

	public int addReadCount(SqlSessionTemplate sqlSession, int boardId) {
		return sqlSession.update("boardMapper.addReadCount",boardId);
	}

	public Board selectBoard(SqlSessionTemplate sqlSession, int boardId) {
		return sqlSession.selectOne("boardMapper.selectBoard",boardId);
	}

	public int updateBoard(SqlSessionTemplate sqlSession, Board b) {
		return sqlSession.update("boardMapper.updateBoard",b);
	}

	public int deleteBoard(SqlSessionTemplate sqlSession, int boardId) {
		return sqlSession.update("boardMapper.deleteBoard",boardId);
	}

	public int insertReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.insert("boardMapper.insertReply", r);
	}

	public ArrayList<Reply> replyList(SqlSessionTemplate sqlSession, int boardId) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectReplyList",boardId);
	}

	public ArrayList<Board> selectTopList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectTopList");
	}
}
