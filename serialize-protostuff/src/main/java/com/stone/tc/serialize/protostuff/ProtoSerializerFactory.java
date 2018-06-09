package com.stone.tc.serialize.protostuff;

import com.stone.tc.serialize.api.SerializeTypes;
import com.stone.tc.serialize.api.Serializer;
import com.stone.tc.serialize.api.SerializerFactory;

/**
 * @author shifeng.luo
 * @version created on 2018/6/9 下午6:17
 */
public class ProtoSerializerFactory extends SerializerFactory {
    @Override
    protected Serializer doCreate(SerializeTypes types) {
        return new ProtoStuffSerializer(types);
    }
}
