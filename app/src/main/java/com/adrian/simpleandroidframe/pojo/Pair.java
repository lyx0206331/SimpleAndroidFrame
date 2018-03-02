package com.adrian.simpleandroidframe.pojo;

/**
 * Created by qing on 2018/3/2 0002.
 */

public final class Pair {
    public int firstValue;
    public int secondValue;

    //引用对象池中的下一个对象
    private Pair next;

    //同步锁
    private static final Object sPoolSync = new Object();
    //对象池中第一个可用的对象
    private static Pair sPool;

    private static int sPoolSize = 0;
    private static final int MAX_POOL_SIZE = 50;

    /**
     * 只能用obtain()方法获取对象
     */
    public Pair() {
    }

    /**
     * 返回回收的对象或者当对象池为空时创建一个新对象
     *
     * @return
     */
    public static Pair obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                Pair m = sPool;
                sPool = m.next;
                m.next = null;
                sPoolSize--;
                return m;
            }
        }
        return new Pair();
    }

    /**
     * 回收该对象.调用该方法后需要释放所有对该实例的引用
     */
    public void recycle() {
        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }
}
