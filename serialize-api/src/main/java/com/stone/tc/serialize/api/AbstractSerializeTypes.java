package com.stone.tc.serialize.api;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author shifeng.luo
 * @version created on 2018/6/9 下午6:44
 */
@Slf4j
public class AbstractSerializeTypes implements SerializeTypes {
    private ConcurrentMap<Byte, Class<?>> typeClass = new ConcurrentHashMap<>();
    private ConcurrentMap<Class<?>, Byte> classType = new ConcurrentHashMap<>();

    protected void register(byte type, Class<?> clazz) {
        Class<?> exist = typeClass.putIfAbsent(type, clazz);
        if (exist != null && exist != clazz) {
            log.error("type:{} has exist Serializable class:{}", type, exist);
            throw new IllegalStateException("type:" + type + " has exist Serializable class:" + exist);
        }

        classType.put(clazz, type);
    }

    /**
     * 根据对象类型获取对应的编码
     *
     * @param clazz 对象类型
     * @return 编码
     */
    @Override
    public byte getCode(Class<?> clazz) {
        Byte type = classType.get(clazz);
        if (type == null) {
            log.error("未知的Class类型:{}", clazz);
            throw new IllegalArgumentException("未知的Class类型:" + clazz);
        }
        return type;
    }

    /**
     * 根据编码获取对应的对象类型
     *
     * @param type 编码
     * @return 对象类型
     */
    @Override
    public Class<?> getType(byte type) {
        Class<?> clazz = typeClass.get(type);
        if (clazz == null) {
            log.error("未知的类型type:{}", type);
            throw new IllegalArgumentException("未知的类型key:" + type);
        }
        return clazz;
    }
}
