package com.kh.sharethevision.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.sharethevision.board.model.dao.BoardDAO;
import com.kh.sharethevision.board.model.vo.Board;
import com.kh.sharethevision.board.model.vo.PageInfo;
import com.kh.sharethevision.board.model.vo.Reply;

@Service("bService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private BoardDAO bDAO;
	
	@Override
	public int getListCount() {
		return bDAO.listCount(sqlSession);
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		return bDAO.selectList(sqlSession, pi);
	}

	@Override
	public int insertBoard(Board b) {
		return bDAO.insertBoard(sqlSession, b);
	}

	@Override
	@Transactional
	public Board selectBoard(int boardId, boolean check) {
		int result = 0;
		Board b = null;
		
		if(check) {
				result= bDAO.addReadCount(sqlSession,boardId);
				
				if(result>0) {
					b=bDAO.selectBoard(sqlSession,boardId);
				}
		}else {
				b= bDAO.selectBoard(sqlSession, boardId);
		}
		return b;
	}

	@Override
	public int updateBoard(Board b) {
		return bDAO.updateBoard(sqlSession, b);
	}

	@Override
	public int deleteBoard(int boardId) {
		return bDAO.deleteBoard(sqlSession,boardId);
	}

	@Override
	public int insertReply(Reply r) {
		return bDAO.insertReply(sqlSession, r);
	}

	@Override
	public ArrayList<Reply> replyList(int boardId) {
		return bDAO.replyList(sqlSession,boardId);
	}

	@Override
	public ArrayList<Board> selectTopList() {
		return bDAO.selectTopList(sqlSession);
	}
	

}
