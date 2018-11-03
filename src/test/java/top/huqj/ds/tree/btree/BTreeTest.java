package top.huqj.ds.tree.btree;

import org.junit.Test;

/**
 * @author huqj
 */
public class BTreeTest {

    BTree<Integer> tree;

    @Test
    public void testCreateBTree() {
        //构造5阶B-树
        long start = System.currentTimeMillis();
        tree = new BTree<>(5);
        System.out.println(tree);
        tree.insert(1);
        tree.insert(5);
        tree.insert(3);
        tree.insert(10);
        System.out.println(tree);
        tree.insert(20);
        tree.insert(2);
        tree.insert(6);
        System.out.println(tree);
        tree.insert(4);
        tree.insert(10);  //忽略
        tree.insert(7);
        tree.insert(40);
        tree.insert(0);
        System.out.println(tree);
        tree.insert(11);
        tree.insert(12);
        tree.insert(13);
        tree.insert(50);
        System.out.println(tree);
        System.out.println("===========================");
        System.out.println("建树耗时：" + (System.currentTimeMillis() - start) + "ms");
    }

    @Test
    public void testFind() {
        testCreateBTree();
        assert tree.find(2).found;
        assert tree.find(2).index == 0;
        assert tree.find(50).found;
        assert tree.find(50).index == 2;
        assert tree.find(40).found;
        assert tree.find(40).index == 1;
        assert tree.find(13).found;
        assert tree.find(13).index == 3;
        assert !tree.find(30).found;
        assert !tree.find(14).found;
    }

}