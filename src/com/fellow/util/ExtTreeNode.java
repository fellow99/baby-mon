package com.fellow.util;

import java.util.List;

public class ExtTreeNode {
    private String id;
    
    private int state;
    
    private String text;
    
    private String qtip;
     
    private String icon;
    
    private boolean leaf;
     
    private boolean expanded;
    
    private Object data;

    private List<ExtTreeNode> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public List<ExtTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ExtTreeNode> children) {
		this.children = children;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
