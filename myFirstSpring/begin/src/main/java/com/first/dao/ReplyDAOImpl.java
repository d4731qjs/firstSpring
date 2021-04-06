package com.first.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.first.vo.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	@Inject SqlSession sql;
	
	//댓글 조회
	@Override
	public List<ReplyVO> readReply(int bno) throws Exception {
		
		return  sql.selectList("replyMapper.readReply", bno);
	}
	
	//댓글 등록
	@Override
	public void writeReply(ReplyVO vo) throws Exception {
			
		sql.insert("replyMapper.writeReply", vo);
	}
	
	//댓글 삭제
	@Override
	public void deleteReply(int rno) throws Exception {
		 sql.delete("replyMapper.deleteReply",rno);
	}
	
	//댓글 수정
	@Override
	public void updateReply(ReplyVO vo) throws Exception {
		sql.update("replyMapper.updateReply",vo);
		
	}
	
}
