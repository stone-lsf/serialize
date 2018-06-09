package com.stone.tc.serialize.json;

import com.stone.tc.serialize.api.SerializeTypes;
import com.stone.tc.serialize.api.Serializer;
import com.stone.tc.serialize.api.SerializerFactory;

/**
 * @author shifeng.luo
 * @version created on 2018/6/9 下午6:29
 */
public class JsonSerializeFactory extends SerializerFactory {
    @Override
    protected Serializer doCreate(SerializeTypes types) {
        return new JsonSerializer(types);
    }
}
