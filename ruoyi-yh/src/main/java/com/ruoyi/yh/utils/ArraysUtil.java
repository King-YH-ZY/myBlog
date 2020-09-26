package com.ruoyi.yh.utils;

import java.util.*;

/**
 * 判断两个数组的交集
 */
public class ArraysUtil {

    /**
     * 求并集
     *
     * @param m
     * @param n
     * @return
     */
    public static String[] getB(String[] m, String[] n)
    {
        // 将数组转换为set集合
        Set<String> set1 = new HashSet<String>(Arrays.asList(m));
        Set<String> set2 = new HashSet<String>(Arrays.asList(n));

        // 合并两个集合
        set1.addAll(set2);

        String[] arr = {};
        return set1.toArray(arr);
    }

    /**
     * 求交集
     *
     * @param m
     * @param n
     * @return
     */
    public static String[] getJ(String[] m, String[] n)
    {
        List<String> rs = new ArrayList<String>();

        // 将较长的数组转换为set
        Set<String> set = new HashSet<String>(Arrays.asList(m.length > n.length ? m : n));

        // 遍历较短的数组，实现最少循环
        for (String i : m.length > n.length ? n : m)
        {
            if (set.contains(i))
            {
                rs.add(i);
            }
        }

        String[] arr = {};
        return rs.toArray(arr);
    }

    /**
     * 求差集
     *
     * @param m
     * @param n
     * @return
     */
    public static String[] getC(String[] m, String[] n)
    {
        // 将较长的数组转换为set
        Set<String> set = new HashSet<String>(Arrays.asList(m.length > n.length ? m : n));

        // 遍历较短的数组，实现最少循环
        for (String i : m.length > n.length ? n : m)
        {
            // 如果集合里有相同的就删掉，如果没有就将值添加到集合
            if (set.contains(i))
            {
                set.remove(i);
            } else
            {
                set.add(i);
            }
        }

        String[] arr = {};
        return set.toArray(arr);
    }

}