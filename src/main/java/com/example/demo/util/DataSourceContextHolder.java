package com.example.demo.util;


import java.util.ArrayList;
import java.util.List;

public class DataSourceContextHolder {
    private static final List<String> dataServerNames = new ArrayList<>();
    private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

    public static void set(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String get() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }

    public static void addServer(String name){
        dataServerNames.add(name);
    }

    public static List<String> getServers(){
        return dataServerNames;
    }
}

