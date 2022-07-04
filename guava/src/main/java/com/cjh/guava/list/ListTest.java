package com.cjh.guava.list;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTest {
    /**
     * list创建
     */
    @Test
    public void Test1() {
        //将数组转成list,并在开头位置插入元素
        List<String> list = Lists.asList("a", new String[]{"b", "c", "d"});
        List<String> list1 = Lists.asList("a", "b", new String[]{"c", "d", "e"});
        //直接创建ArrayList
        ArrayList<String> arrayList = Lists.newArrayList();
        //创建ArrayList,并初始化
        ArrayList<String> arrayList1 = Lists.newArrayList("a", "b", "c");
        //基于现有的arrayList,创建一个arrayList
        ArrayList<String> arrayList2 = Lists.newArrayList(arrayList1);
        //初始化指定容量大小的ArrayList，其中容量指ArrayList底层依赖的数组的length属性值，常用于提前知道ArrayList大小的情况的初始化
        ArrayList<String> arrayList3 = Lists.newArrayListWithCapacity(10);
        //初始化预定容量大小的ArrayList，返回的list的实际容量为5L + estimatedSize + (estimatedSize / 10)，常用于不确定ArrayList大小的情况的初始化
        ArrayList<String> arrayList4 = Lists.newArrayListWithExpectedSize(20);
        //创建CopyOnWriteArrayList
        CopyOnWriteArrayList<String> copyOnWriteArrayList = Lists.newCopyOnWriteArrayList();
        //创建linkedList
        LinkedList<String> linkedList = Lists.newLinkedList();
    }

    /**
     * 将list按指定的长度切割成多个
     */
    @Test
    public void Test2() {
        List<String> list = Lists.newArrayList("a", "b", "c", "d", "e");
        //将list按大小为2分隔成多个list
        List<List<String>> splitList = Lists.partition(list, 2);
        System.out.println(splitList);
    }

    /**
     * 笛卡尔集
     */
    @Test
    public void Test3() {
        List<String> list1 = Lists.newArrayList("a", "b", "c");
        List<String> list2 = Lists.newArrayList("d", "e", "f");
        List<String> list3 = Lists.newArrayList("1", "2", "3");
        //获取多个list的笛卡尔集
        List<List<String>> list = Lists.cartesianProduct(list1, list2, list3);
        System.out.println(list);
    }

    /**
     * 字符串转成字符集合
     */
    @Test
    public void Test4() {
        //将字符串转成字符集合
        ImmutableList<Character> list = Lists.charactersOf("ababcdfb");
        System.out.println(list);
    }

    /**
     * list反转
     */
    @Test
    public void Test5() {
        List<String> list = Lists.newArrayList("a", "b", "c", "1", "2", "3");
        //反转list
        List<String> reverseList = Lists.reverse(list);
        System.out.println(reverseList);
    }

    /**
     * 对集合中的元素进行操作
     */
    @Test
    public void Test6() {
        List<String> list = Lists.newArrayList("a", "b", "c");
        //把list中的每个元素拼接一个1
        List<String> list1 = Lists.transform(list, str -> str + "1");
        System.out.println(list1);
    }
}
