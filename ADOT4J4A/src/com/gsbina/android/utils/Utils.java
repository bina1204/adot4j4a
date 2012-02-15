
package com.gsbina.android.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * 配列を指定サイズで分割します。分割された配列は List (ArrayList) に格納して戻します。
     * 
     * @param <T> 分割元配列の型
     * @param array 分割元となる配列
     * @param size 分割サイズ
     * @return 分割された配列のリスト
     */
    public static List<long[]> splitArray(long[] array, int size) {
        List<long[]> list = new ArrayList<long[]>();
        for (int i = 0; i < array.length; i += size) {
            int subArrayLength = (array.length - i > size) ? size : (array.length - i);
            long[] subArray = new long[subArrayLength];
            System.arraycopy(array, i, subArray, 0, subArrayLength);
            list.add(subArray);
        }
        return list;
    }
}
