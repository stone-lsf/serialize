package com.stone.tc.serialize.protostuff;

import com.stone.tc.serialize.api.SerializeTypes;
import com.stone.tc.serialize.api.Serializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shifeng.luo
 * @version created on 2018/6/9 下午6:19
 */
@Slf4j
public class ProtoStuffSerializer implements Serializer {

    private static Map<Class<?>, Schema<?>> schemaMap = new ConcurrentHashMap<>();

    private final SerializeTypes types;

    public ProtoStuffSerializer(SerializeTypes types) {
        this.types = types;
    }

    @Override
    public byte[] serialize(Object obj) {
        return toArrayByte(obj);
    }

    @SuppressWarnings("unchecked")
    private <T> byte[] toArrayByte(Object obj) {
        T instance = (T) obj;
        Class<T> cType = (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(cType);
        LinkedBuffer buffer = LinkedBufferPool.getLinkedBuffer();
        try {
            byte[] value = appendHead(ProtobufIOUtil.toByteArray(instance, schema, buffer), types.getCode(cType));
            if (log.isDebugEnabled()) {
                log.debug("proto stuff serialize " + obj.getClass() + " instance length:" + value.length);
            }
            return value;
        } finally {
            LinkedBufferPool.recycle(buffer);
        }
    }

    /**
     * 增加数组头，用于标识对象类型
     *
     * @param array 字节数组
     * @param type  对象类型
     * @return 最终序列化出来的数据
     */
    private byte[] appendHead(byte[] array, byte type) {
        byte[] value = new byte[array.length + 1];
        value[0] = type;
        System.arraycopy(array, 0, value, 1, array.length);
        return value;
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes) {
        return (T) mergeFrom(bytes, types.getType(bytes[0]));
    }

    private <T> T mergeFrom(byte[] bytes, Class<T> type) {
        Schema<T> schema = getSchema(type);
        T instance = schema.newMessage();
        byte[] to = new byte[bytes.length - 1];
        System.arraycopy(bytes, 1, to, 0, bytes.length - 1);
        ProtobufIOUtil.mergeFrom(to, instance, schema);
        return instance;
    }


    @SuppressWarnings("unchecked")
    private <T> Schema<T> getSchema(Class<T> cType) {
        Schema<T> schema = (Schema<T>) schemaMap.get(cType);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(cType);
            schemaMap.put(cType, schema);
        }
        return schema;
    }
}
