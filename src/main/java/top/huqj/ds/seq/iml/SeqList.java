package top.huqj.ds.seq.iml;

import top.huqj.ds.seq.IList;

import java.util.Arrays;

/**
 * 顺序表实现
 *
 * @author huqj
 * @since 1.0
 */
public class SeqList<T> implements IList<T> {

    /**
     * 存储元素的数组
     */
    private T[] objects;

    /**
     * 标识顺序表的当前长度
     */
    private int curLen;

    /**
     * 包含最大长度参数的构造方法
     *
     * @param maxSize
     */
    public SeqList(int maxSize) {
        objects = (T[]) new Object[maxSize];
        curLen = 0;
    }

    public void clear() {
        curLen = 0;
    }

    public boolean isEmpty() {
        return curLen == 0;
    }

    public int length() {
        return curLen;
    }

    public T get(int i) throws Exception {
        if (i < curLen && i >= 0) {
            return objects[i];
        }
        //超出下标范围
        throw new Exception("index " + i + " out of boundary");
    }

    public void insert(int i, T x) throws Exception {
        if (curLen == objects.length) {
            //达到最大长度，无法插入元素
            throw new Exception("array is full, can't insert any element.");
        } else if (i <= curLen && i >= 0) {
            for (int index = curLen; index > i; index--) {
                objects[index] = objects[index - 1];
            }
            objects[i] = x;
            curLen++;
            return;
        }
        //超出下标范围
        throw new Exception("index " + i + "out of boundary");
    }

    public void append(T x) throws Exception {
        insert(curLen, x);
    }

    public void remove(int i) throws Exception {
        if (i < 0 || i >= curLen) {
            throw new Exception("index " + i + " out of boundary");
        }
        //直接删除末尾元素，不需要移动
        if (i == curLen - 1) {
            curLen--;
            return;
        }
        for (int index = i; index < curLen - 1; index++) {
            objects[index] = objects[index + 1];
        }
        curLen--;
    }

    public int indexOf(T x) {
        for (int i = 0; i < curLen; i++) {
            if (x.equals(objects[i])) {  //找到第一个
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return Arrays.toString(objects);
    }
}
