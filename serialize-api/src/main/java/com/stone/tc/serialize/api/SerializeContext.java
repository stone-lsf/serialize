package com.stone.tc.serialize.api;

/**
 * @author shifeng.luo
 * @version created on 2018/6/9 下午6:15
 */
public class SerializeContext {

    private final String type;
    private final SerializeTypes types;

    public SerializeContext(String type, SerializeTypes types) {
        this.type = type;
        this.types = types;
    }

    public String getType() {
        return type;
    }

    public SerializeTypes getTypes() {
        return types;
    }
}
