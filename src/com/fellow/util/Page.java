package com.fellow.util;

import java.util.List;

public class Page<T> {
	private int count;
	private int start;
	private int limit;
	private List<T> items;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getPageNo(){
		return this.getStart() / this.getLimit() + 1;
	}
	
	public int getPageSize(){
		return getLimit();
	}
}
