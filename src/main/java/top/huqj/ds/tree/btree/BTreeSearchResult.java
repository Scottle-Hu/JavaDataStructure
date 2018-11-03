package top.huqj.ds.tree.btree;

/**
 * B-树查找结果的封装
 *
 * @author huqj
 */
public class BTreeSearchResult {

    //结果所在的节点
    public BTreeNode node;

    //关键字的下标
    public int index;

    //是否找到，如果没找到则返回最后一个查找失败的节点便于后面插入
    public boolean found;

}
