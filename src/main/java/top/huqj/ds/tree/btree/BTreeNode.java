package top.huqj.ds.tree.btree;

import java.util.Arrays;

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
    public BTreeNode<T> parent;

    //构造方法传入m表示叉数
    public BTreeNode(int m) {
        isLeaf = true;
        keyNum = 0;
        keys = (T[]) new Object[m - 1];
        children = new BTreeNode[m];
        parent = null;
    }

    /**
     * 在节点插入一个数据，如果需要分裂则递归插入
     *
     * @param index
     * @param data
     * @return
     */
    public InsertResult insertKey(int index, T data) {
        InsertResult insertResult = new InsertResult();
        if (keyNum < keys.length) {
            for (int i = keyNum; i > index; i--) {
                keys[i] = keys[i - 1];
            }
            keys[index] = data;
            if (!isLeaf) {  //如果不是叶子节点还需要移动孩子指针
                for (int i = keyNum + 1; i > index; i--) {
                    children[i] = children[i - 1];
                }
                children[index] = null;
            }
            keyNum++;
            insertResult.resultNum = 1;
            insertResult.nodes.add(this);
            insertResult.index = index;
        } else {
            T[] arr = (T[]) new Object[keyNum + 1];
            int k = 0;
            for (int i = 0; i < index; i++) {
                arr[k++] = keys[i];
            }
            arr[k++] = data;
            for (int i = index; i < keys.length; i++) {
                arr[k++] = keys[i];
            }
            int mid = (keyNum + 1) / 2;
            Comparable<T> midData = (Comparable<T>) arr[mid];
            BTreeNode<T> left = new BTreeNode<>(children.length);
            k = 0;
            for (int i = 0; i < mid; i++) {
                left.keys[k++] = arr[i];
            }
            left.keyNum = k;
            BTreeNode<T> right = new BTreeNode<>(children.length);
            k = 0;
            for (int i = mid + 1; i < arr.length; i++) {
                right.keys[k++] = arr[i];
            }
            right.keyNum = k;
            //是根节点
            if (parent == null) {
                parent = new BTreeNode(children.length);
            }
            parent.isLeaf = false;
            T[] pKeys = (T[]) parent.keys;
            int pIndex = -1;
            for (int i = 0; i < parent.keyNum; i++) {
                if (midData.compareTo(pKeys[i]) < 0) {
                    pIndex = i;
                    break;
                }
            }
            if (pIndex == -1) {
                pIndex = parent.keyNum;
            }
            //递归插入中间节点
            InsertResult parentResult = parent.insertKey(pIndex, arr[mid]);
            if (parentResult.resultNum == 1) {
                left.parent = right.parent = parentResult.nodes.get(0);
                parentResult.nodes.get(0).children[parentResult.index] = left;
                parentResult.nodes.get(0).children[parentResult.index + 1] = right;
            } else if (parentResult.resultNum == 2) {
                left.parent = parentResult.nodes.get(0);
                right.parent = parentResult.nodes.get(1);
                BTreeNode leftParent = parentResult.nodes.get(0);
                BTreeNode rightParent = parentResult.nodes.get(1);
                leftParent.children[leftParent.keyNum] = left;
                rightParent.children[rightParent.keyNum] = right;
            }
            int comResult = midData.compareTo(data);
            if (comResult == 0) {
                insertResult.resultNum = 2;
                insertResult.nodes.add(left);
                insertResult.nodes.add(right);
            } else {
                insertResult.resultNum = 1;
                insertResult.nodes.add(comResult > 0 ? left : right);
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] == data) {
                        if (i < mid) {
                            insertResult.index = i;
                        } else {
                            insertResult.index = i - mid - 1;
                        }
                        break;
                    }
                }
            }
        }
        return insertResult;
    }

    /**
     * 删除该节点某个索引的数据
     *
     * @param index
     */
    public void delete(int index) {
        BTreeNode deleteNode = this;
        if (isLeaf) {
            //叶子节点直接删除
            for (int i = index; i < keyNum - 1; i++) {
                keys[i] = keys[i + 1];
            }
            keys[keyNum - 1] = null;
            keyNum--;
            deleteNode.merge();
        } else {
            //非叶子节点用右边最小元素代替
            deleteNode = deleteNode.children[index + 1];
            while (deleteNode.children[0] != null) {
                deleteNode = deleteNode.children[0];
            }
            keys[index] = (T) deleteNode.keys[0];
            //删除叶子节点数据
            deleteNode.delete(0);
        }
    }

    /**
     * 删除之后的合并操作
     */
    private void merge() {
        if (parent == null) {
            //根节点无需合并
            return;
        }
        int least = (int) Math.ceil(children.length / 2.0) - 1;
        if (keyNum >= least) {
            return;
        }
        Comparable<T> min = (Comparable<T>) keys[0];
        //找到左右兄弟节点
        BTreeNode left = null, right = null;
        int index = -1;
        for (int i = 0; i < parent.keyNum; i++) {
            if (min.compareTo(parent.keys[i]) < 0) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            index = parent.keyNum;
        }
        if (index > 0) {
            left = parent.children[index - 1];
        }
        if (index < parent.keyNum) {
            right = parent.children[index + 1];
        }
        if (right != null && right.keyNum > least) {
            //旋转右兄弟最小元素
            T rightMin = (T) right.keys[0];
            right.delete(0);
            T parentData = parent.keys[index];
            parent.keys[index] = rightMin;
            insertKey(keyNum, parentData);

        } else if (left != null && left.keyNum > least) {
            //旋转左兄弟最大元素
            T leftMax = (T) left.keys[left.keyNum - 1];
            left.delete(left.keyNum - 1);
            T parentData = parent.keys[index - 1];
            parent.keys[index - 1] = leftMax;
            insertKey(0, parentData);
        } else {
            //合并
            if (right != null) {
                right.insertKey(0, parent.keys[index]);
                for (int i = keyNum - 1; i >= 0; i--) {
                    right.insertKey(0, keys[i]);
                }
                for (int i = index; i < parent.keyNum - 1; i++) {
                    parent.keys[i] = parent.keys[i + 1];
                }
                parent.keys[parent.keyNum - 1] = null;
                for (int i = index; i < parent.keyNum; i++) {
                    parent.children[i] = parent.children[i + 1];
                }
                parent.children[parent.keyNum] = null;
                parent.keyNum--;
                parent.merge();
            } else {
                left.insertKey(left.keyNum, parent.keys[index - 1]);
                for (int i = 0; i < keyNum; i++) {
                    left.insertKey(left.keyNum, keys[i]);
                }
                for (int i = index; i < parent.keyNum - 1; i++) {
                    parent.keys[i] = parent.keys[i + 1];
                }
                parent.keys[parent.keyNum - 1] = null;
                for (int i = index; i < parent.keyNum; i++) {
                    parent.children[i] = parent.children[i + 1];
                }
                parent.children[parent.keyNum] = null;
                parent.keyNum--;
                parent.merge();
            }
        }
    }

    @Override
    public String toString() {
        String tail = " ";
        if (parent != null && parent.children[0] == this) {
            tail = "\n";  //每层换一行
        }
        return tail + "BTreeNode{" +
                "keys=" + Arrays.toString(keys) +
                '}';
    }

}
