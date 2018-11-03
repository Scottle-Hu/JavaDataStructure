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
