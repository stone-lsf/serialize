package com.stone.tc.serialize.api;

/**
 * @author shifeng.luo
 * @version created on 2018/6/9 下午6:08
 */
public interface Serializer {

    /**
     * 序列化
     *
     * @param obj 待序列化对象
     * @return 字节数组
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @return 反序列化后的对象
     */
    <T> T deserialize(byte[] bytes);
}
