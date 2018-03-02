package com.adrian.simpleandroidframe;

import android.util.Log;

import com.adrian.simpleandroidframe.pojo.Pair;

/**
 * Created by qing on 2018/3/2 0002.
 */

public class AllocationTest {

    public void bestObjectAllocationExample(int[] pairs) {
        if (pairs.length % 2 != 0) {
            throw new IllegalArgumentException("Bad array size!");
        }
        for (int i = 0; i < pairs.length; i += 2) {
            Pair pair = Pair.obtain();
            pair.firstValue = pairs[i];
            pair.secondValue = pairs[i + 1];
            sendPair(pair);
            //切记手动回收
            pair.recycle();
        }
    }

    private void sendPair(Pair pair) {
        //处理pair
        Log.e("UNIT_TEST", "result:" + pair.firstValue + "--" + pair.secondValue);
    }
}
