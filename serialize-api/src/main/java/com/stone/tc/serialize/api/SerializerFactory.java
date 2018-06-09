package com.stone.tc.serialize.api;


import com.stone.tc.common.ServiceLoader;

/**
 * @author shifeng.luo
 * @version created on 2018/6/9 下午6:14
 */
public abstract class SerializerFactory {

    public static Serializer create(SerializeContext context) {
        SerializerFactory serializerFactory = ServiceLoader.findService(context.getType(), SerializerFactory.class);

        return serializerFactory.doCreate(context.getTypes());
    }

    protected abstract Serializer doCreate(SerializeTypes types);
}
