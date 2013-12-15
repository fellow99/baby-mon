package com.fellow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Tree<T> {
	private static final String ROOT_ID = "__ROOT__";
	private TreeNode<T> root;
	private Map<String, Pair<TreeNode<T>, TreeNode<T>>> location;

	public Tree(){
		root = new TreeNode<T>(ROOT_ID, ROOT_ID, null);
		location = new HashMap<String, Pair<TreeNode<T>, TreeNode<T>>>();
	}
	
	public TreeNode<T> getRoot() {
		return root;
	}
	
	protected Map<String, Pair<TreeNode<T>, TreeNode<T>>> getLocation() {
		return location;
	}
	
	public TreeNode<T> get(String id){
		Pair<TreeNode<T>, TreeNode<T>> pair = getLocation().get(id);
		return (pair == null ? null : pair.getRight());
	}
	
	public TreeNode<T> getParent(String id){
		Pair<TreeNode<T>, TreeNode<T>> pair = getLocation().get(id);
		return (pair == null ? null : pair.getLeft());
	}
	
	public TreeNode<T> put(String parent, String id, String text, T entity){
		TreeNode<T> node = null;
		if(parent == null || parent.length() == 0){
			node = this.getRoot();
		} else {
			node = get(parent);
			if(node == null){
				throw new java.lang.NullPointerException("Parent node is null: " + parent);
			}
		}
		
		TreeNode<T> child = new TreeNode<T>(id, text, entity);
		node.getChildren().add(child);
		getLocation().put(id, new Pair<TreeNode<T>, TreeNode<T>>(node, child));
		return child;
	}
	
	public void remove(String id){
		TreeNode<T> node = getParent(id);
		if(node == null){
			throw new java.lang.NullPointerException("Node is null: " + id);
		}
		for(Iterator<TreeNode<T>> it = node.getChildren().iterator(); it.hasNext();){
			TreeNode<T> child = it.next();
			if(id.equals(child.getId())){
				it.remove();
			}
		}
		location.remove(id);
	}

	public void join(String id, Tree<T> tree){
	}

	public List<TreeNode<T>> toList(){
		return this.toList(null);
	}
	
	public List<TreeNode<T>> toList(String parent){
		TreeNode<T> node = null;
		if(parent == null){
			node = this.getRoot();
		} else {
			node = this.get(parent);
		}
		if(node == null || node.getChildren() == null)return null;
		
		List<TreeNode<T>> list = new ArrayList<TreeNode<T>>();
		
		for(Iterator<TreeNode<T>> it = node.getChildren().iterator(); it.hasNext();){
			TreeNode<T> child = it.next();
			list.add(child);
			list.addAll(this.toList(child.getId()));
		}
		
		return list;
	}
	
	public class TreeNode<TT>{
		private String id;
		private String text;
		private List<TreeNode<TT>> children;
		private TT entity;
		
		public TreeNode(String id, String text, TT entity){
			this.id = id;
			this.text = text;
			this.entity = entity;
			this.children = new ArrayList<TreeNode<TT>>();
		}
		
		public String getId(){
			return id;
		}
		
		public String getText(){
			return text;
		}
		
		public TT getEntity(){
			return entity;
		}

		public List<TreeNode<TT>> getChildren() {
			return children;
		}
	}
}
