package top.huqj.ds.tree.btree;

/**
 * B-树的节点类
 *
 * @author huqj
 */
public class BTreeNode<T> {

    //该节点关键字的个数
    public int keyNum;

    //是否为叶子节点
    public boolean isLeaf;

    //关键字数组
    public T[] keys;

    //子节点指针数组
    public BTreeNode<T>[] children;

    //父节点
    public BTreeNode parent;

    //构造方法传入m表示叉数
    public BTreeNode(int m) {
        isLeaf = true;
        keyNum = 0;
        keys = (T[]) new Object[m - 1];
        children = new BTreeNode[m];
        parent = null;
    }

}
