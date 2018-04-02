//package common;
//
///**
// * Created by dell on 2016/11/3.
// */
//public class ListMapCommon {
//    /**
//     * 类List4Map.java的实现描述：List4Map.java
//     *
//     * @author ahhqcheng 2012-2-29 下午7:56:11
//     */
//
//    /**
//     * @param 某个对象的List
//     * @return Map<Key,List>
//     * @description 实现将一个List转化为Map<Key,List>的样式
//     */
//    public Map<Integer, List<TestObject>> list4Map(List<TestObject> list) {
//
//        Map<Integer, List<TestObject>> map = new HashMap<Integer, List<TestObject>>();
//
//        if ((list != null) && (list.size() != 0)) {
//            for (TestObject test : list) {
//                // 这一步是关键，也很巧妙吧，哈哈
//                List<TestObject> testList = map.get(test.getId());
//                if (testList == null) {
//                    testList = new ArrayList<TestObject>();
//                }
//                testList.add(test);
//                map.put(test.getId(), testList);
//            }
//        }
//        return map;
//    }
//
//    /**
//     * @param Map<key,List>
//     * @return void
//     * @description 对Map<key,List>的数据结构进行遍历
//     */
//    public void outOfMap(Map<Integer, List<TestObject>> map) {
//        Set<Integer> keySet = map.keySet();
//        for (Iterator<Integer> i = keySet.iterator(); i.hasNext(); ) {
//            Integer key = i.next();
//            System.out.println(key + "  :  ");
//            List<TestObject> testList = map.get(key);
//            for (TestObject test : testList) {
//                System.out.print("<--" + test.getName() + "--" + test.getAddress() + "-->");
//            }
//            System.out.println();
//        }
//
//    }

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* 循环存入数据到Map
* */
//    public static void main(String[] args) {
//        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        int a = 1;
//        int b = 2;
//
//        String key = "";
//        String value = "";
//        for (int i = 0; i < 50; i++) {
//            HashMap<String, String> map = new HashMap<String, String>();
//            key = (a + i * 2) + "";
//            value = (b + i * 2) + "";
//            map.put(key, value);
//            list.add(map);
//        }
//
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
//    }

