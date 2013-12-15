package com.fellow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeUtil {
	
	public static <T> ExtTreeNode toExtTree(Tree<T> tree){
		return toExtTree(tree, ExtTreeNode.class, null);
	}
	
	public static <T> ExtTreeNode toExtTree(Tree<T> tree, Class<? extends ExtTreeNode> clazz){
		return toExtTree(tree, clazz, null);
	}
	
	public static <T> ExtTreeNode toExtTree(Tree<T> tree, Class<? extends ExtTreeNode> clazz, String parent){
		Tree<T>.TreeNode<T> node = null;
		if(parent == null || parent.length() == 0){
			node = tree.getRoot();
		} else {
			node = tree.get(parent);
		}
		if(node == null) return null;
		

		ExtTreeNode treenode;
		try {
			treenode = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		treenode.setId(node.getId());
		treenode.setText(node.getText());
		treenode.setLeaf((node.getChildren() == null || node.getChildren().size() == 0));
		treenode.setData(node.getEntity());
		
		
		List<Tree<T>.TreeNode<T>> children = node.getChildren();
		if(children != null){
			List<ExtTreeNode> list = new ArrayList<ExtTreeNode>();
			treenode.setChildren(list);
			for(Iterator<Tree<T>.TreeNode<T>> it = children.iterator(); it.hasNext();){
				Tree<T>.TreeNode<T> child = it.next();

				ExtTreeNode childNode = toExtTree(tree, clazz, child.getId());
				list.add(childNode);
			}
		}
		return treenode;
	}
	

	public static boolean checkNode(ExtTreeCheckNode node, String id){
		if(id.equals(node.getId())){
			node.setChecked(true);
			return true;
		}
		if(node.getChildren() != null){
			for(Iterator<ExtTreeNode> it = node.getChildren().iterator(); it.hasNext();){
				ExtTreeCheckNode child = (ExtTreeCheckNode)it.next();
				boolean ok = checkNode(child, id);
				if(ok)return true;
			}
		}
		return false;
	}
	
	
	

	
	public static <T> List<Map<String, Object>> toListMap(Tree<T> tree){
		return toListMap(tree, null);
	}
	
	public static <T> List<Map<String, Object>> toListMap(Tree<T> tree, String parent){
		Tree<T>.TreeNode<T> node = null;
		if(parent == null || parent.length() == 0){
			node = tree.getRoot();
		} else {
			node = tree.get(parent);
		}
		if(node == null) return null;
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		List<Tree<T>.TreeNode<T>> children = node.getChildren();
		if(children != null){
			for(Iterator<Tree<T>.TreeNode<T>> it = children.iterator(); it.hasNext();){
				Tree<T>.TreeNode<T> child = it.next();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", child.getId());
				map.put("text", child.getText());
				map.put("leaf", (child.getChildren() == null || child.getChildren().size() == 0));
				map.put("data", child.getEntity());
				map.put("children", toListMap(tree, child.getId()));
				list.add(map);
			}
		}
		return list;
	}
}
