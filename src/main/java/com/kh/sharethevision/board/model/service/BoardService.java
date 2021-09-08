package com.kh.sharethevision.board.model.service;

import java.util.ArrayList;

import com.kh.sharethevision.board.model.vo.Board;
import com.kh.sharethevision.board.model.vo.PageInfo;
import com.kh.sharethevision.board.model.vo.Reply;

public interface BoardService {

	int getListCount();

	ArrayList<Board> selectList(PageInfo pi);

	int insertBoard(Board b);

	Board selectBoard(int boardId, boolean b);

	int updateBoard(Board b);

	int deleteBoard(int boardId);

	int insertReply(Reply r);

	ArrayList<Reply> replyList(int boardId);

	ArrayList<Board> selectTopList();
	
}
