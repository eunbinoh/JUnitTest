package com.kh.sharethevision.member.model.exception;

public class MemberException extends RuntimeException{
	public MemberException() {}
	public MemberException(String msg) {
		super(msg);
		
	// ** 자동 에러처리 ** extends RuntimeException	
	}
	

}
