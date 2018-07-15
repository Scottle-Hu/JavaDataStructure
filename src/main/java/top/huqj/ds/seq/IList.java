package top.huqj.ds.seq;

/**
 * 线性表数据操作接口
 *
 * @author huqj
 * @since 1.0
 */
public interface IList<T> {

    /**
     * 清空线性表
     */
    void clear();

    /**
     * 判断线性表是否为空
     *
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 返回线性表的当前长度
     *
     * @return 当前长度
     */
    int length();

    /**
     * 获取第1个下标的元素
     *
     * @param i 下标
     * @return 该位置的元素对象
     */
    T get(int i) throws Exception;

    /**
     * 在第i个位置插入一个元素
     *
     * @param i 插入位置
     * @param x 插入元素
     */
    void insert(int i, T x) throws Exception;

    /**
     * 在线性表末尾添加元素
     *
     * @param x
     * @throws Exception
     */
    void append(T x) throws Exception;

    /**
     * 删除第i个位置的元素
     *
     * @param i 下标
     */
    void remove(int i) throws Exception;

    /**
     * 查找第一个值为x的元素出现的位置，若没有则返回-1
     *
     * @param x
     * @return
     */
    int indexOf(T x);

}
