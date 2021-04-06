package com.first.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.first.dao.BoardDAO;
import com.first.util.FileUtils;
import com.first.vo.BoardVO;
import com.first.vo.SearchCriteria;

@Service
public class BoardServiceImple implements BoardService {

	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Inject
	private BoardDAO dao;
	
	//게시물 쓰기
	@Override
	public int write(BoardVO boardVO) throws Exception {
		
		try {dao.write(boardVO); return 0;}
		catch(Exception e) {e.printStackTrace();return 1;}
	}
	
	//답글 쓰기
	@Override
	public int commentWrite(BoardVO boardVO) throws Exception {

		try {dao.commentWrite(boardVO); return 0;}
		catch(Exception e) {e.printStackTrace();return 1;}
	}
	
	// 게시물 리스트 불러오기
	@Override
	public List<BoardVO> list(SearchCriteria scri) throws Exception {

		return dao.list(scri);
	}
	
	//게시글 총 갯수
	@Override
	public int listCount(SearchCriteria scri) throws Exception {
		
		return dao.listCount(scri);
		
	}
	// 게시물 목록 조회
	@Override
	public BoardVO read(int bno) throws Exception {

		return dao.read(bno);
	}
	
	@Override
	public void update(BoardVO boardVO, String[] files, String[] fileNames, MultipartHttpServletRequest mpRequest) throws Exception {
		
		dao.update(boardVO);
		
		List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(boardVO, files, fileNames, mpRequest);
		Map<String, Object> tempMap = null;
		int size = list.size();
		for(int i = 0; i<size; i++) {
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")) {
				dao.insertFile(tempMap);
			}else {
				dao.updateFile(tempMap);
			}
		}
	}
	
	//게시글 삭제
	@Override
	public int delete(int bno, int parentno){
		
		try {
			if(bno == parentno ) {
				dao.delete(parentno); 
				return 0;
			}
			else{
				BoardVO boardVo = new BoardVO();
				boardVo.setBno(bno);
				boardVo.setParentno(parentno);
				dao.commentIndexUpdate(boardVo);
				dao.commentDelete(bno); 
				return 0;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	@Override
	public void tenfu(int bno, MultipartHttpServletRequest mpRequest) throws Exception {
		
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(bno, mpRequest); 
		int size = list.size();
		for(int i=0; i<size; i++){ 
			dao.insertFile(list.get(i)); 
		}
		
	}
	
	// 첨부파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectFileList(bno);
	}
	
	// 첨부파일 다운로드
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectFileInfo(map);
	}
}