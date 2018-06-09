package com.stone.tc.serialize.protostuff;

import io.protostuff.LinkedBuffer;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author shifeng.luo
 * @version created on 2018/6/9 下午6:22
 */
public class LinkedBufferPool {
    private static ConcurrentLinkedQueue<LinkedBuffer> linkedQueue = new ConcurrentLinkedQueue<LinkedBuffer>();

    private static final int CORE_LENGTH = 50;

    public static LinkedBuffer getLinkedBuffer() {
        LinkedBuffer linkedBuffer = linkedQueue.poll();
        if (linkedBuffer == null) {
            linkedBuffer = LinkedBuffer.allocate();
        }
        return linkedBuffer;
    }

    public static void recycle(LinkedBuffer linkedBuffer) {
        linkedBuffer.clear();
        if (linkedQueue.size() < CORE_LENGTH) {
            linkedQueue.offer(linkedBuffer);
        }
    }
}
