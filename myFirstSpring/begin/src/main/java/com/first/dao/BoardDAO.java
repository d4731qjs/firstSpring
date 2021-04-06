package com.first.dao;

import java.util.List;
import java.util.Map;

import com.first.vo.BoardVO;
import com.first.vo.SearchCriteria;

public interface BoardDAO {

	// 게시글 작성
	public void write(BoardVO boardVO) throws Exception;
	
	// 답글 작성
	public void commentWrite(BoardVO boardVO) throws Exception;
	
	// 게시글 불러오기
	public List<BoardVO> list(SearchCriteria scri) throws Exception;
	
	// 게시글 총 갯수
	public int listCount(SearchCriteria scri) throws Exception;
	// 게시물 조회
	public BoardVO read(int bno) throws Exception;
	
	// 게시물 수정
	public void update(BoardVO boardVO) throws Exception;
	
	// 게시물 수정
	public void commentIndexUpdate(BoardVO boardVO) throws Exception;
	
	// 게시물 삭제
	public void delete(int parentno) throws Exception;
	
	// 답글 삭제
	public void commentDelete(int bno) throws Exception;
	
	//파일 입력
	public void insertFile(Map<String, Object> map) throws Exception;
	
    // 첨부파일 조회
	public List<Map<String, Object>> selectFileList(int bno) throws Exception;
	
	// 첨부파일 다운
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;
	
	// 첨부파일 수정
	public void updateFile(Map<String, Object> map) throws Exception;
	
}
