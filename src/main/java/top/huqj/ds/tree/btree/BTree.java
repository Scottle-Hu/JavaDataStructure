package top.huqj.ds.tree.btree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * B-树结构
 *
 * @author huqj
 */
public class BTree<T> {

    public BTreeNode<T> root;

    public int degree;

    //构造一颗m阶B-树
    public BTree(int m) {
        degree = m;
        root = new BTreeNode<T>(degree);
    }

    /**
     * search method
     *
     * @param key
     * @return
     */
    public BTreeSearchResult find(T key) {
        BTreeNode<T> now = root, parent = null;
        Comparable<T> key2 = (Comparable<T>) key;
        BTreeSearchResult ret = new BTreeSearchResult();
        boolean shouldContinue;
        while (now != null) {
            shouldContinue = false;
            for (int i = 0; i < now.keyNum; i++) {
                int result = key2.compareTo(now.keys[i]);
                if (result <= 0) {
                    if (result == 0) {  //found
                        ret.node = now;
                        ret.index = i;
                        ret.found = true;
                        return ret;
                    } else {  //to child
                        parent = now;
                        ret.index = i;
                        now = now.children[i];
                        shouldContinue = true;
                        break;
                    }
                }
            }
            if (shouldContinue) {
                continue;
            }
            if (now.keyNum > 0) {
                parent = now;
                ret.index = now.keyNum;
                now = now.children[now.keyNum];
            } else {  //初始情况，没有数据
                parent = now;
                ret.index = 0;
                break;
            }
        }
        ret.found = false;
        ret.node = parent;
        return ret;
    }

    /**
     * 插入一个数据
     *
     * @param data
     * @return
     */
    public boolean insert(T data) {
        BTreeSearchResult result = find(data);
        if (result.found) {
            return false;
        }
        BTreeNode<T> node = result.node;
        node.insertKey(result.index, data);
        //检查根节点是否改变
        while (root.parent != null) {
            root = root.parent;
        }
        return true;
    }

    /**
     * 删除一个数据
     *
     * @param data
     * @return
     */
    public boolean delete(T data) {
        BTreeSearchResult result = find(data);
        if (!result.found) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Queue<BTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            BTreeNode node = queue.poll();
            sb.append(node);
            for (int i = 0; i <= node.keyNum; i++) {
                if (node.children[i] != null) {
                    queue.offer(node.children[i]);
                }
            }
        }
        return "BTree {" + sb.toString() + "}";
    }

}
