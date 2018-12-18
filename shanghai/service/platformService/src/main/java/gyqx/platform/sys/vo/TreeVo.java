package gyqx.platform.sys.vo;

import java.util.List;

public class TreeVo<T> {
	private T node;
	private List<TreeVo<T>> children;
	private String id;
	private String name;
	private String nodeType;
	private boolean nodeStatu = true;
	public T getNode() {
		return node;
	}

	public void setNode(T node) {
		this.node = node;
	}

	public List<TreeVo<T>> getChildren() {
		return children;
	}

	public void setChildren(List<TreeVo<T>> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public boolean isNodeStatu() {
		return nodeStatu;
	}

	public void setNodeStatu(boolean nodeStatu) {
		this.nodeStatu = nodeStatu;
	}

	
}
