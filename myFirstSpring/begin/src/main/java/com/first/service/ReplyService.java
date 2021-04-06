package com.first.service;

import java.util.List;

import com.first.vo.ReplyVO;

public  interface ReplyService {
	
	//댓글 불러오기
	public List<ReplyVO> readReply(int bno) throws Exception;
	
	//댓글 등록
	public int writeReply(ReplyVO vo);
	
	//댓글 삭제
	public int deleteReply(int rno);
	
	//댓글 수정
	public int updateReply(ReplyVO vo) throws Exception;
}
