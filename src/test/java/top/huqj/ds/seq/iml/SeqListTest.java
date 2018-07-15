package top.huqj.ds.seq.iml;

import org.junit.Test;
import top.huqj.ds.seq.IList;

import static org.junit.Assert.*;

/**
 * @author huqj
 */
public class SeqListTest {

    @Test
    public void testSeqListWithMaxSizeParam() {
        IList<Integer> list = new SeqList<Integer>(10);
        assert list.isEmpty();
    }

    @Test
    public void testAppend() throws Exception {
        IList<Integer> list = new SeqList<Integer>(10);
        list.append(1);
        assert !list.isEmpty();
        assert list.length() == 1;
        list.append(2);
        assert list.length() == 2;
        assert list.get(0) == 1;
        assert list.get(1) == 2;
    }

    @Test
    public void testInsert() throws Exception {
        IList<Integer> list = new SeqList<Integer>(10);
        list.insert(0, 1);
        assert list.length() == 1;
        list.insert(0, 2);
        assert list.get(0) == 2;
        assert list.length() == 2;
        list.insert(2, 3);
        assert list.length() == 3;
        assert list.get(1) == 1;
        assert list.get(0) == 2;
        assert list.get(2) == 3;
    }

    @Test
    public void testRemove() throws Exception {
        IList<Integer> list = new SeqList<Integer>(10);
        list.append(1);
        list.append(2);
        list.append(3);
        assert list.length() == 3;
        list.remove(1);
        assert list.length() == 2;
        assert list.get(1) == 3;
        list.remove(1);
        assert list.length() == 1;
        assert list.get(0) == 1;
    }

    @Test
    public void testIndexOf() throws Exception {
        IList<Integer> list = new SeqList<Integer>(10);
        list.append(1);
        list.append(2);
        list.append(3);
        assert list.indexOf(1) == 0;
        assert list.indexOf(3) == 2;
        assert list.indexOf(4) == -1;
    }

}
