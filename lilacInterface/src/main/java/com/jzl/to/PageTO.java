package com.jzl.to;

public class PageTO {
	private int currentPage;
	
	private int pageSize;
	
	private int isLast;
	
	private int count;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getIsLast() {
		return isLast;
	}

	public void setIsLast(int isLast) {
		this.isLast = isLast;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
}
