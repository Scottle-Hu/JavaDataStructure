package top.huqj.ds.tree.btree;

import java.util.ArrayList;
import java.util.List;

/**
 * B-树插入之后的结果
 *
 * @author huqj
 */
public class InsertResult {

    //返回的节点个数，如果插入数据被分裂到了上面的节点则为2，否则为1
    public int resultNum;

    //结果节点
    public List<BTreeNode> nodes = new ArrayList<>();

    //插入下标
    public int index;
}
