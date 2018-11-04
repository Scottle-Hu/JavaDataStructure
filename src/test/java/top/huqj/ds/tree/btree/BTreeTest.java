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

    @Test
    public void testDelete() {
        testCreateBTree();
        tree.delete(50);
        System.out.println("删除50\n" + tree);
        tree.delete(40);
        System.out.println("删除40\n" + tree);
        tree.delete(11);
        System.out.println("删除11\n" + tree);
        tree.delete(10);
        System.out.println("删除10\n" + tree);
        tree.delete(12);
        System.out.println("删除12\n" + tree);
        tree.delete(1);
        System.out.println("删除1\n" + tree);
        tree.delete(10);
        System.out.println("删除10\n" + tree);
        tree.delete(5);
        System.out.println("删除5\n" + tree);
        tree.delete(2);
        System.out.println("删除2\n" + tree);
        tree.delete(20);
        System.out.println("删除20\n" + tree);
        tree.delete(7);
        System.out.println("删除7\n" + tree);
        tree.delete(6);
        System.out.println("删除6\n" + tree);
        tree.delete(0);
        System.out.println("删除0\n" + tree);
        tree.delete(4);
        System.out.println("删除4\n" + tree);
        tree.delete(13);
        System.out.println("删除13\n" + tree);
        tree.delete(3);
        System.out.println("删除3\n" + tree);
        assert tree.isEmpty();
    }

}