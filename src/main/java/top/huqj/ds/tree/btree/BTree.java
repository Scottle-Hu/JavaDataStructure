package top.huqj.ds.tree.btree;

import java.util.Comparator;
import java.util.Optional;

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
    public Optional<BTreeSearchResult> find(T key) {
        BTreeNode<T> now = root;
        Comparable<T> key2 = (Comparable<T>) key;
        BTreeSearchResult ret = new BTreeSearchResult();
        boolean shouldContinue = false;
        while (now != null) {
            for (int i = 0; i < now.keyNum; i++) {
                int result = key2.compareTo(now.keys[i]);
                if (result <= 0) {
                    if (result == 0) {  //found
                        ret.node = now;
                        ret.index = i;
                        return Optional.of(ret);
                    } else {  //to child
                        now = now.children[i];
                        shouldContinue = true;
                        break;
                    }
                }
            }
            if (!shouldContinue) {
                now = now.children[now.children.length - 1];
            }
        }
        return Optional.empty();
    }

}
