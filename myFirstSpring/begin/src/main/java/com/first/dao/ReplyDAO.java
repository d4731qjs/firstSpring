package com.first.dao;

import java.util.List;

import com.first.vo.ReplyVO;

public interface ReplyDAO {
	
	//댓글 불러오기
	public List<ReplyVO> readReply(int bno) throws Exception;
	
	//댓글 등록
	public void writeReply(ReplyVO vo) throws Exception ;
	
	//댓글 삭제
	public void deleteReply(int rno) throws Exception ;
		
	//댓글 수정
	public void updateReply(ReplyVO vo) throws Exception;
	
}
