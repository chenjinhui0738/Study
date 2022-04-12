package com.cjh.guava.set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.*;

public class SetTest {
    /**
     * set创建
     */
    @Test
    public void Test1(){
        //创建不可变的set，线程安全
        ImmutableSet immutableSet = ImmutableSet.of("a","b","c","d");
        //创建hashset
        HashSet<String> hashSet = Sets.newHashSet();
        //创建linkhashset
        LinkedHashSet<Object> linkedHashSet = Sets.newLinkedHashSet();
        //初始化预定容量大小的hashSet，返回的set的实际容量为5L + estimatedSize + (estimatedSize / 10)，常用于不确定hashset大小的情况的初始化
        HashSet<Object> newHashSetWithExpectedSize = Sets.newHashSetWithExpectedSize(10);
        //创建treeset
        TreeSet<Comparable> treeSet = Sets.newTreeSet();
        //创建线程安全类concurrentHashSet
        Set<Object> concurrentHashSet = Sets.newConcurrentHashSet();
    }
    /**
     * 笛卡尔集
     */
    @Test
    public void Test2(){
        Set<String> set1 = Sets.newHashSet("1","2","3");
        Set<String> set2 = Sets.newHashSet("4","5","6");
        Set<String> set3 = Sets.newHashSet("7","8","9");
        //多个Set的笛卡尔集，参数接收多个set集合
        Set<List<String>> sets = Sets.cartesianProduct(set1,set2,set3);
        System.out.println(sets);

        List<Set<String>> list = Lists.newArrayList(set1,set2,set3);
        //也可以把多个Set集合，放到一个list中，再计算笛卡尔集
        Set<List<String>> sets1 = Sets.cartesianProduct(list);
        System.out.println(sets1);
    }

    /**
     * 按指定大小进行排列组合
     */
    @Test
    public void Test3(){
        //将集合中的元素按指定的大小分隔，指定大小的所有组合
        Set<String> set1 = Sets.newHashSet("a","b","c","d");
        Set<Set<String>> sets = Sets.combinations(set1,3);
        for(Set<String> set : sets){
            System.out.println(set);
        }
    }

    /**
     * 差集
     */
    @Test
    public void Test4(){
        Set<String> set1 = Sets.newHashSet("a","b","d");
        Set<String> set2 = Sets.newHashSet("d","e","f");
        //difference返回：从set1中剔除两个set公共的元素
        System.out.println(Sets.difference(set1,set2));
        //symmetricDifference返回：剔除两个set公共的元素，再取两个集合的并集
        System.out.println(Sets.symmetricDifference(set1,set2));
    }

    /**
     * 交集
     */
    @Test
    public void Test5(){
        Set<String> set1 = Sets.newHashSet("a","b","c");
        Set<String> set2 = Sets.newHashSet("a","b","f");
        //取两个集合的交集
        System.out.println(Sets.intersection(set1,set2));
    }

    /**
     * 过滤
     */
    @Test
    public void Test6(){
        Set<String> set1 = Sets.newHashSet("a","b","c");
        //建议可以直接使用java8的过滤，比较方便
        Set<String> set2 = Sets.filter(set1,str -> str.equalsIgnoreCase("B"));
        System.out.println(set2);
    }
    /**
     * 排列组合
     */
    @Test
    public void Test7(){
        Set<String> set1 = Sets.newHashSet("a","b","c");
        //获取set可分隔成的所有子集
        Set<Set<String>> allSet = Sets.powerSet(set1);
        for(Set<String> set : allSet){
            System.out.println(set);
        }
    }
    /**
     * 并集
     */
    @Test
    public void Test8(){
        Set<String> set1 = Sets.newHashSet("a","b","c");
        Set<String> set2 = Sets.newHashSet("1","2","3");
        //取两个集合的并集
        System.out.println(Sets.union(set1,set2));
    }
}

