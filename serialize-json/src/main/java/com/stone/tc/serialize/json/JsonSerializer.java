package com.stone.tc.serialize.json;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Maps;
import com.stone.tc.common.utils.JsonUtil;
import com.stone.tc.serialize.api.SerializeTypes;
import com.stone.tc.serialize.api.Serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shifeng.luo
 * @version created on 2018/6/9 下午6:32
 */
public class JsonSerializer implements Serializer {
    private static final String TYPE_KEY = "type";
    private static final String DATA_KEY = "data";

    private final SerializeTypes types;

    public JsonSerializer(SerializeTypes types) {
        this.types = types;
    }

    @Override
    public byte[] serialize(Object obj) {
        byte type = types.getCode(obj.getClass());
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        map.put(TYPE_KEY, type);
        map.put(DATA_KEY, obj);

        String json = JsonUtil.toJson(map);
        if (json == null) {
            throw new RuntimeException("序列化数据失败");
        }
        return json.getBytes();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes) {
        String jsonStr = new String(bytes);
        JavaType mapType = JsonUtil.createMapType(HashMap.class, String.class, Object.class);

        Map<String, Object> map = JsonUtil.fromJson(jsonStr, mapType);
        if (map == null) {
            throw new RuntimeException("反序列化数据失败");
        }

        Integer type = (Integer) map.get(TYPE_KEY);
        Class<?> dataType = this.types.getType(type.byteValue());
        String dataStr = JsonUtil.toJson(map.get(DATA_KEY));

        return (T) JsonUtil.fromJson(dataStr, dataType);
    }
}
