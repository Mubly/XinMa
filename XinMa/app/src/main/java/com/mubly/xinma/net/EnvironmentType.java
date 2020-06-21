package com.mubly.xinma.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/7/13.
 */
public enum EnvironmentType {

    DEVELOP(0),
    TEST(1),
    ONLINE(2);


    private int environmentType;

    private EnvironmentType(int environmentType) {
        this.environmentType = environmentType;
    }

    public int getValue() {
        return environmentType;
    }

    public boolean equal (Object obj)
    {
        if(!(obj instanceof EnvironmentType)) {
            return false;
        }

        EnvironmentType environmentType = (EnvironmentType)obj;

        if (environmentType.getValue() != this.getValue()) {
            return false;
        }
        return true;
    }

    private static volatile Map<Integer, EnvironmentType> directory = null;

    private static Map<Integer, EnvironmentType> getDirectory() {

        if (directory == null) {
            Map<Integer, EnvironmentType> environmentType = new HashMap<Integer, EnvironmentType>();
            for (EnvironmentType v : values()) {
                environmentType.put(v.getValue(), v);
            }
            directory = environmentType;
        }
        return directory;
    }

    public static EnvironmentType fromInt(int environmentType) {
        return getDirectory().get(environmentType);
    }
}
