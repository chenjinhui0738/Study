package com.cjh.guava.map;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class MapTest {
    /**
     * map创建
     */
    @Test
    public void Test1(){
        //创建hashmap
        HashMap<Object, Object> hashMap = Maps.newHashMap();
        //初始化预定容量大小的hashmap，返回的map的实际容量为5L + estimatedSize + (estimatedSize / 10)，常用于不确定hashmap大小的情况的初始化
        HashMap<Object, Object> hashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(10);
        //创建concurrentMap,线程安全
        ConcurrentMap<Object, Object> concurrentMap = Maps.newConcurrentMap();
        //创建identityHashMap，键值使用==比较，比较地址值，所以键值可重复
        Map<String, String> identityHashMap = Maps.newIdentityHashMap();
        identityHashMap.put("1","1");
        identityHashMap.put("1","2");
        identityHashMap.put(new String("1"),"3");
        identityHashMap.put(new String("1"),"4");
        System.out.println(identityHashMap);//{1=4, 1=3, 1=2}
        //创建linkedHashMap
        LinkedHashMap<Object, Object> linkedHashMap = Maps.newLinkedHashMap();
        //初始化预定容量大小的linkhashmap
        LinkedHashMap<Object, Object> linkedHashMapWithExpectedSize = Maps.newLinkedHashMapWithExpectedSize(10);
        //创建treeMap
        TreeMap<Comparable, Object> treeMap = Maps.newTreeMap();
    }
    /**
     * set转map
     */
    @Test
    public void Test2(){
        Set<String> set = Sets.newHashSet("a","b","c");
        //将set转成Map,key为set元素,value为每个元素的长度
        Map<String,Integer> map = Maps.asMap(set,String::length);
        System.out.println(map);
    }
    /**
     * 差集、交集、并集
     */
    @Test
    public void Test3(){
        Map<String,String> map1 = Maps.newHashMap();
        map1.put("a","1");
        map1.put("b","2");
        map1.put("c","3");
        Map<String,String> map2 = Maps.newHashMap();
        map2.put("a","1");
        map2.put("e","5");
        map2.put("f","6");
        //mapDifference是将两个map相同的部分剔除
        MapDifference<String,String> mapDifference = Maps.difference(map1,map2);
        //两个Map相同的部分
        System.out.println(mapDifference.entriesInCommon());
        //左边集合剔除相同部分后的剩余
        System.out.println(mapDifference.entriesOnlyOnLeft());
        //右边集合剔除相同部分后的剩余
        System.out.println(mapDifference.entriesOnlyOnRight());
    }
    /**
     * 过滤
     */
    @Test
    public void Test4(){
        Map<String,String> map1 = Maps.newHashMap();
        map1.put("a","1");
        map1.put("b","2");
        map1.put("c","3");
        //通过Key过滤
        Map<String,String> result1 = Maps.filterKeys(map1, item -> !item.equalsIgnoreCase("b"));
        System.out.println(result1);
        //通过Entry过滤
        Map<String,String> result2 = Maps.filterEntries(map1,item -> !item.getValue().equalsIgnoreCase("2"));
        System.out.println(result2);
        //通过value过滤
        Map<String,String> result =  Maps.filterValues(map1,item -> !item.equalsIgnoreCase("3"));
        System.out.println(result);
    }
    /**
     * 对entry操作
     */
    @Test
    public void Test5(){
        Map<String,String> map = Maps.newHashMap();
        map.put("a","1");
        map.put("b","2");
        map.put("c","3");
        Map<String,String> result = Maps.transformEntries(map,(k,v) -> k + v);
        System.out.println(result);
    }
    /**
     * value操作
     */
    @Test
    public void Test6(){
        Map<String,String> map1 = Maps.newHashMap();
        map1.put("a","1");
        map1.put("b","2");
        map1.put("c","3");
        Map<String,String> result = Maps.transformValues(map1, value -> value + 10);
        System.out.println(result);
    }
}
