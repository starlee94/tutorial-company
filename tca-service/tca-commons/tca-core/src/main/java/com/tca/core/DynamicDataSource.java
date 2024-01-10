package com.tca.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<Integer> CONTEXTHOLDER = new ThreadLocal<>();

    public DynamicDataSource(Map<Object, Object> targetDataSources) {
        //设置数据源列表
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    /**
     * 绑定当前线程数据源路由的key
     * 使用完成后必须调用removeRouteKey()方法删除
     */
    public static void setDataSource(Integer dataSource) {
        CONTEXTHOLDER.set(dataSource);
    }

    /**
     * 获取当前线程的数据源路由的key
     */
    public static Integer getDataSource() {
        return CONTEXTHOLDER.get();
    }

    /**
     * 删除与当前线程绑定的数据源路由的key
     */
    public static void clearDataSource() {
        CONTEXTHOLDER.remove();
    }
}
