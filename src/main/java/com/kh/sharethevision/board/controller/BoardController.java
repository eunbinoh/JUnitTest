package com.kh.sharethevision.board.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.kh.sharethevision.board.model.exception.BoardException;
import com.kh.sharethevision.board.model.service.BoardService;
import com.kh.sharethevision.board.model.vo.Board;
import com.kh.sharethevision.board.model.vo.PageInfo;
import com.kh.sharethevision.board.model.vo.Reply;
import com.kh.sharethevision.common.Pagination;
import com.kh.sharethevision.member.model.vo.Member;

@Controller
public class BoardController {

	@Autowired
	private BoardService bService;
	
	@RequestMapping("blist.bo")
	public ModelAndView boardList(@RequestParam(value="page", required=false) Integer page, ModelAndView mv) {
		
		int currentPage=1;
		if(page !=null) {
			currentPage = page;
		}
		
		int listCount = bService.getListCount();
		PageInfo pi = Pagination.getPageInfo(currentPage,listCount);
		
		ArrayList<Board> list = bService.selectList(pi);
		if(list!=null) {
			mv.addObject("list",list).addObject("pi",pi);
			mv.setViewName("boardListView");
		}else {
			throw new BoardException("게시물 조회 실패");
		}
		return mv;
	}
	
	@RequestMapping("binsertView.bo")
	public String boardInsertForm() {
		return "boardInsertForm";
		
	}
	
	@RequestMapping("binsert.bo")
	public String insertBoard(@ModelAttribute Board b, 
							 @RequestParam("uploadFile") MultipartFile uploadFile, 
							 HttpServletRequest request) {
		if(!uploadFile.getOriginalFilename().equals("")) {
			String renameFileName = saveFile(uploadFile,request);
			
			if(renameFileName!=null) {
				b.setOriginalFileName(uploadFile.getOriginalFilename());
				b.setRenameFileName(renameFileName);
			}
		}
		
		int result = bService.insertBoard(b);
		if(result>0) {
			return "redirect:blist.bo";
		}else {
			throw new BoardException("게시글 등록 실패");
		}
	}
	
	public String saveFile(MultipartFile file, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\buploadFiles";
		
		File folder = new File(savePath);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String originalFileName = file.getOriginalFilename();
		String renameFileName = sdf.format(new Date(System.currentTimeMillis()))+"."+
								originalFileName.substring(originalFileName.lastIndexOf(".")+1);
		
		String renamePath = folder + "\\" + renameFileName;
		try {
			file.transferTo(new File(renamePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return renameFileName;
		
	}
	
	
	
	@RequestMapping("bdetail.bo")
	public ModelAndView bdetail(@RequestParam("boardId") int boardId, @RequestParam("page") Integer page, ModelAndView mv) {
		Board b = bService.selectBoard(boardId,true);
		if(b!=null) {
			mv.addObject("b",b);
			mv.addObject("page",page);
			mv.setViewName("boardDetailView");
		}else {
			throw new BoardException("게시글 상세보기 실패");
		}
		return mv;
	}
	
	@RequestMapping("bupView.bo")
	public String boardUpdateForm(@RequestParam("boardId") int bId, @RequestParam("page") int page, Model model) {
		Board board = bService.selectBoard(bId, false);
		model.addAttribute("board",board).addAttribute("page",page);
		return "boardUpdateForm";
	}
	
	
	@RequestMapping("bupdate.bo")
	public ModelAndView updateBoard(@ModelAttribute Board b, 
									@RequestParam("page") int page,
									@RequestParam("reloadFile") MultipartFile reloadFile,
									HttpServletRequest request,
									ModelAndView mv) {
		
		if(reloadFile!=null && !reloadFile.isEmpty()) {
			if(b.getRenameFileName()!=null) {
				String root = request.getSession().getServletContext().getRealPath("resources");
				String savePath = root + "\\buploadFiles";
				
				File f = new File(savePath+"\\"+ b.getRenameFileName());
				if(f.exists()) {
					f.delete();
				}
			}
			
			String renameFileName = saveFile(reloadFile,request);
			if(renameFileName!=null) {
				b.setOriginalFileName(reloadFile.getOriginalFilename());
				b.setRenameFileName(renameFileName);
			}		
		}
		
		int result = bService.updateBoard(b);
		if(result>0) {
			Board board = bService.selectBoard(b.getBoardId(), false);
			mv.addObject("b",b);
			mv.addObject("page",page);
			mv.setViewName("boardDetailView");
		}else {
			throw new BoardException("게시글 수정 실패");
		}
		
		return mv;
	}
	
	@RequestMapping("bdelete.bo")
	public String deleteBoard(@RequestParam("boardId") int boardId, HttpServletRequest request) {
		
		Board board = bService.selectBoard(boardId, false);
		
		if(board.getOriginalFileName()!=null) {
			deleteFile(request,board);
		}
		
		int result = bService.deleteBoard(boardId);
		if(result>0) {
			return "redirect:blist.bo";
		}else {
			throw new BoardException("게시물 삭제 실패");
		}
	}
	
	
	public void deleteFile(HttpServletRequest request, Board board) {
	String root = request.getSession().getServletContext().getRealPath("resources");
	String savePath = root + "\\buploadFiles";
	
	File f = new File(savePath +"\\"+ board.getRenameFileName());
	if(f.exists()) {
		f.delete();
		}
	}
	
	@RequestMapping("addReply.bo")
	@ResponseBody
	public String insertReply(@ModelAttribute Reply r,
							HttpSession session ) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		String replyWriter = loginUser.getId();
		r.setReplyWriter(replyWriter);
		
		int result = bService.insertReply(r);
		
		if(result>0) {
			return "success";
		}else {
			throw new BoardException("댓글 등록 실패");
		}
		
	}
	
//	@RequestMapping(value="rList.bo", produces="application/json; charset=UTF-8") //response 안쓸경우 (아래에서 인코딩)
	@RequestMapping(value="rList.bo")
	@ResponseBody
	public void listReply(@RequestParam("boardId") int boardId, HttpServletResponse response) {
		
		ArrayList<Reply> rList = bService.replyList(boardId);
		
//		JSONArray jarr = new JSONArray();
//		
//		for(Reply r : rList) {
//			JSONObject obj = new JSONObject();
//			obj.put("replyId",r.getReplyId());
//			obj.put("replyContent",r.getReplyContent());
//			obj.put("refBoardId",r.getRefBoardId());
//			obj.put("replyWriter",r.getReplyWriter());
//			obj.put("replyCreateDate",r.getReplyCreateDate().toString());
//			//json은 기본자료형만 출력가능( Date x -> toString())
//			obj.put("replyModifyDate",r.getReplyModifyDate().toString());
//			//json은 기본자료형만 출력가능( Date x -> toString())
//			obj.put("replyStatus",r.getReplyStatus());
//			
//			jarr.add(obj);
//		return jarr.toJSONString();
//		}
		
		GsonBuilder gb = new GsonBuilder().setDateFormat("yyyy-MM-dd");
		Gson gson = gb.create();
		try {
			
			response.setContentType("application/json; charset=UTF-8");
			gson.toJson(rList, response.getWriter());
			
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@RequestMapping("topList.bo")
	public void selectTopList(HttpServletResponse response) {
		ArrayList<Board> list = bService.selectTopList();
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		response.setContentType("application/json; charset=UTF-8");
			try {
				gson.toJson(list, response.getWriter());
				
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
	
}
