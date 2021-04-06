package com.first.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.first.vo.BoardVO;
import com.first.vo.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Inject
	private SqlSession sqlSession;
	
	// 게시글 작성
	@Override
	public void write(BoardVO boardVO) throws Exception {
		sqlSession.insert("boardMapper.insert",boardVO);
	}
	
	// 답글 작성
	@Override
	public void commentWrite(BoardVO boardVO) throws Exception {
		sqlSession.insert("boardMapper.commentInsert",boardVO);
	}
	
	//게시물 불러오기
	@Override
	public List<BoardVO> list(SearchCriteria scri) throws Exception {
	
		return sqlSession.selectList("boardMapper.listPage",scri);

	}
	
	//게시글 총 갯수
	@Override
	public int listCount(SearchCriteria scri) throws Exception {
		
		return sqlSession.selectOne("boardMapper.listCount",scri);
	}
	
	// 게시물 조회
	@Override
	public BoardVO read(int bno) throws Exception {
			
		return sqlSession.selectOne("boardMapper.read", bno);
	}
	
	// 게시물 수정
	@Override
	public void update(BoardVO boardVO) throws Exception {
		
		sqlSession.update("boardMapper.update", boardVO);
	}
	
	@Override
	public void commentIndexUpdate(BoardVO boardVO) throws Exception {
		
		sqlSession.update("boardMapper.commentIndexUpdate", boardVO);	
	}
	// 게시물 삭제
	@Override
	public void delete(int parentno) throws Exception {
		
		sqlSession.delete("boardMapper.delete", parentno);
	}
	
	@Override
	public void commentDelete(int bno) throws Exception {
		
		sqlSession.delete("boardMapper.commentDelete", bno);
	}
	
	// 첨부파일 업로드
	@Override
	public void insertFile(Map<String, Object> map) throws Exception {
		sqlSession.insert("boardMapper.insertFile", map);
	}
	
	// 첨부파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int bno) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("boardMapper.selectFileList", bno);
	}
	
	// 첨부파일 다운로드
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("boardMapper.selectFileInfo", map);
	}
	
	// 첨부파일 수정
	@Override
	public void updateFile(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
		sqlSession.update("boardMapper.updateFile", map);
	}
}