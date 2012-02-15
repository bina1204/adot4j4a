
package com.gsbina.android.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * �z����w��T�C�Y�ŕ������܂��B�������ꂽ�z��� List (ArrayList) �Ɋi�[���Ė߂��܂��B
     * 
     * @param <T> �������z��̌^
     * @param array �������ƂȂ�z��
     * @param size �����T�C�Y
     * @return �������ꂽ�z��̃��X�g
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
