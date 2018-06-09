package com.stone.tc.serialize.api;

/**
 * @author shifeng.luo
 * @version created on 2018/6/9 下午6:12
 */
public interface SerializeTypes {
    /**
     * 根据对象类型获取对应的编码
     *
     * @param type 对象类型
     * @return 编码
     */
    byte getCode(Class<?> type);

    /**
     * 根据编码获取对应的对象类型
     *
     * @param code 编码
     * @return 对象类型
     */
    Class<?> getType(byte code);
}
