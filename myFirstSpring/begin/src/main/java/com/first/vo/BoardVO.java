package com.first.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardVO {
	
	private int bno;
	private String title;
	private String content;
	private String writer;
	private String regdate;
	private int parentno;
	private int groupno;
	private int depth;
	
	public int getGroupno() {
		return groupno;
	}
	public void setGroupno(int groupno) {
		this.groupno = groupno;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getParentno() {
		return parentno;
	}
	public void setParentno(int parentno) {
		this.parentno = parentno;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegdate() throws Exception {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date to = fm.parse(regdate);
		return to;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	
}
