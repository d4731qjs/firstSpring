package com.first.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.first.service.BoardService;
import com.first.service.ReplyService;
import com.first.vo.BoardVO;
import com.first.vo.PageMaker;
import com.first.vo.ReplyVO;
import com.first.vo.SearchCriteria;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardService service;
	
	@Inject
	ReplyService replyService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("/");
				
		return "redirect:/board/list";
	}
	
	// 게시판 글 작성 화면
	@RequestMapping(value = "/board/writeView", method = RequestMethod.GET)
	public void writeView() throws Exception{
		logger.info("writeView");
		
	}
	
	// 게시판 글 작성
	@ResponseBody
	@RequestMapping(value = "/board/writeView", method = RequestMethod.POST)
	public  int write(@RequestBody Map<String, Object> params, HttpServletRequest request, Model model) throws Exception{
		BoardVO boardVO = new BoardVO();		
		boardVO.setTitle((String)params.get("title"));
		boardVO.setContent((String)params.get("content"));
		boardVO.setWriter((String)params.get("writer"));
		
		service.write(boardVO);	
		return boardVO.getBno();
	}
	
	// 게시판 답글달기 화면
	@RequestMapping(value = "/commentWriteView", method = RequestMethod.GET)
	public String commentWriteView(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, Model model)
		throws Exception {
		logger.info("commentWriteView");
		
		model.addAttribute("comment", service.read(boardVO.getBno()));
		model.addAttribute("scri", scri);

		List<Map<String, Object>> fileList = service.selectFileList(boardVO.getBno());
		model.addAttribute("file", fileList);
		return "board/commentWriteView";
	}
	
	// 게시판 답글달기 
	@ResponseBody
	@RequestMapping(value = "/commentWriteView", method = RequestMethod.POST)
	public  int commentWrite(@RequestBody Map<String, Object> params, HttpServletRequest request, Model model) throws Exception{
		BoardVO boardVO = new BoardVO();		
		boardVO.setTitle((String)params.get("title"));
		boardVO.setContent((String)params.get("content"));
		boardVO.setWriter((String)params.get("writer"));
		boardVO.setParentno(Integer.parseInt((String)params.get("parentno")));
		
		service.commentWrite(boardVO);	
	    return boardVO.getBno();
	}
			
	
	// 게시판 목록 조회
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, @ModelAttribute("scri") SearchCriteria scri) throws Exception{
		logger.info("list");
		
		model.addAttribute("list", service.list(scri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount(scri));
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "board/list";
		
	}
	
				
	// 게시판 수정뷰
	@RequestMapping(value = "/updateView", method = RequestMethod.GET)
	public String updateView(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, Model model)
		throws Exception {
		logger.info("updateView");

		model.addAttribute("update", service.read(boardVO.getBno()));
		model.addAttribute("scri", scri);

		List<Map<String, Object>> fileList = service.selectFileList(boardVO.getBno());
		model.addAttribute("file", fileList);
		return "board/updateView";
	}

	
	// 게시판 수정
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(BoardVO boardVO, 
						@ModelAttribute("scri") SearchCriteria scri, 
						 RedirectAttributes rttr,
						 @RequestParam(value="fileNoDel[]") String[] files,
						 @RequestParam(value="fileNameDel[]") String[] fileNames,
						 MultipartHttpServletRequest mpRequest) throws Exception {
		logger.info("update");
		service.update(boardVO, files, fileNames, mpRequest);
		
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	
		
	// 게시판 삭제
	@ResponseBody
	@RequestMapping(value = "/board/delete", method = RequestMethod.POST)
	public int delete(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception{
		logger.info("delete");
		return service.delete(
				Integer.parseInt((String)params.get("bno")),
				Integer.parseInt((String)params.get("parentno"))
				);
	}
	
	// 게시판 조회
	@RequestMapping(value = "/readView", method = RequestMethod.GET)
	public String read(BoardVO boardVO,@ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("read");
		
		model.addAttribute("read", service.read(boardVO.getBno()));
		model.addAttribute("scri",scri);
		
//		List<ReplyVO> replyList = replyService.readReply(boardVO.getBno());
//		model.addAttribute("replyList", replyList);

		List<Map<String, Object>> fileList = service.selectFileList(boardVO.getBno());
		model.addAttribute("file", fileList);
		
		return "board/readView";
	}
		
	//덧글 불러오기	
	@RequestMapping(value="/board/replyLoad", produces="application/json; charset=utf8")
    @ResponseBody
    public ResponseEntity replyLoad(@ModelAttribute("ReplyVO") ReplyVO replyVO, HttpServletRequest request) throws Exception{
        
        HttpHeaders responseHeaders = new HttpHeaders();
        ArrayList<HashMap> hmlist = new ArrayList<HashMap>();
        
        
        List<ReplyVO> replyList = replyService.readReply(replyVO.getBno());
        
        if(replyList.size() > 0){
            for(int i=0; i<replyList.size(); i++){
                HashMap hm = new HashMap();
                hm.put("rno", replyList.get(i).getRno());
                hm.put("content", replyList.get(i).getContent());
                hm.put("writer", replyList.get(i).getWriter());
                
                hmlist.add(hm);
            }
            
        }
        
        JSONArray json = new JSONArray(hmlist);        
        return new ResponseEntity(json.toString(), responseHeaders, HttpStatus.CREATED);
        
    }
	
	//댓글 쓰기
	@RequestMapping(value="/board/replyWrite")
    @ResponseBody
    public int replyWrite(@ModelAttribute("ReplyVO") ReplyVO replyVO,  HttpServletRequest request) throws Exception{

        return replyService.writeReply(replyVO); 
    }
	
	//댓글 삭제
	@ResponseBody
	@RequestMapping(value = "/board/replyDelete", method = RequestMethod.POST)
	public int replyDelete(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception{
		logger.info("replydelete");
		//return 1;
		return replyService.deleteReply((int)params.get("rno"));
	}
	
	//댓글 수정
	@ResponseBody
	@RequestMapping(value = "/board/replyUpdate", method = RequestMethod.POST)
	public int replyUpdate(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception{
		logger.info("replydelete");
		
		ReplyVO vo = new ReplyVO();
		vo.setRno(Integer.parseInt((String)params.get("rno")));
		vo.setContent((String)params.get("content"));

		return replyService.updateReply(vo);
	}
	
	// 파일 첨부
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public String fileInput(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception{
		logger.info("file");
		service.tenfu(boardVO.getBno(),mpRequest);
		return  "redirect:/board/list";
	}
	
	//파일 다운로드
	@RequestMapping(value="/fileDown")
	public void fileDown(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = service.selectFileInfo(map);
		String storedFileName = (String) resultMap.get("STORED_FILE_NAME");
		String originalFileName = (String) resultMap.get("ORG_FILE_NAME");
		
		// 파일을 저장했던 위치에서 첨부파일을 읽어 byte[]형식으로 변환한다.
		byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(new File("C:\\files\\"+storedFileName));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition",  "attachment; fileName=\""+URLEncoder.encode(originalFileName, "UTF-8")+"\";");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
	}
	
}
