package gyqx.mdqc.mycerts.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 经销授权书授权树节点
 *
 * @Author liangwu
 * @Date 18-8-23 上午8:50
 */
@Getter
@Setter
public final class TreeNode<T> {
    @JsonIgnore
    private TreeNode<T> parent;
    private List<TreeNode<T>> children;
    private T data;

    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    @JsonIgnore
    public boolean isRoot() {
        return this.parent == null;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public TreeNode<T> addChild(T data){
        TreeNode<T> childNode = new TreeNode<>(data);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    @JsonIgnore
    public int getLevel(){
        if (isRoot()) {
            return 0;
        } else {
            return this.parent.getLevel() + 1;
        }
    }
}
