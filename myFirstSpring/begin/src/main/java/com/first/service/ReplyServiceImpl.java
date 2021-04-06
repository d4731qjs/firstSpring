package com.first.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.first.dao.ReplyDAO;
import com.first.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Inject private ReplyDAO dao;
	
	@Override
	public List<ReplyVO> readReply(int bno) throws Exception {

		return dao.readReply(bno);
	}
	
	@Override
	public int writeReply(ReplyVO vo) {
		
		try {	dao.writeReply(vo);return 0;}
		catch(Exception e) {e.printStackTrace();return 1;}
		
	}
	
	@Override
	public int deleteReply(int rno){
		
		try {	dao.deleteReply(rno);return 0;}
		catch(Exception e) {e.printStackTrace();return 1;}
		
	}
	
	@Override
	public int updateReply(ReplyVO vo) throws Exception {
	
		try {	dao.updateReply(vo);return 0;}
		catch(Exception e) {e.printStackTrace();return 1;}
		
	}
}
