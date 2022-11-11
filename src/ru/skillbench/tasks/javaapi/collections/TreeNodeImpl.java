package ru.skillbench.tasks.javaapi.collections;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class TreeNodeImpl implements TreeNode {

    private TreeNode parent = null;
    private LinkedHashSet<TreeNode> children = new LinkedHashSet<TreeNode>();
    boolean expanded = false;
    Object data = null;

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getRoot() {
        if (parent == null) {
            return null;
        }
        return parent.getRoot();
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public int getChildCount() {
        return children.size();
    }

    public Iterator<TreeNode> getChildrenIterator() {
        return children.iterator();
    }

    public void addChild(TreeNode child) {
        children.add(child);
        child.setParent(this);
    }

    public boolean removeChild(TreeNode child) {
        if (children.remove(child)) {
            child.setParent(null);
            return true;
        }
        return false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        for (TreeNode child : children) {
            child.setExpanded(expanded);
        }
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTreePath() {
        String previousPath = parent != null ? parent.getTreePath() + "->" : "";
        return previousPath + data.toString();
    }

    public TreeNode findParent(Object data) {
        if ((data == null && this.data == null) || this.data.equals(data)) {
            return this;
        }
        return parent == null ? null : parent.findParent(data);
    }

    public TreeNode findChild(Object data) {
        Iterator<TreeNode> iterator = children.iterator();
        while (iterator.hasNext()) {
            TreeNode child = iterator.next();
            Object childData = child.getData();
            if ((data == null && childData == null) || data.equals(childData)) {
                return child;
            }
            TreeNode deepChild = child.findChild(data);
            if (deepChild != null) {
                return deepChild;
            }
        }
        return null;
    }
}
