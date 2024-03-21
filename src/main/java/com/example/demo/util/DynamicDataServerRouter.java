package com.example.demo.util;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class DynamicDataServerRouter extends AbstractRoutingDataSource implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Override
    protected Object determineCurrentLookupKey() {
        // 获取当前线程上下文中存放的数据源标识符
        return DataSourceContextHolder.get();
    }
    @Override
    public void afterPropertiesSet() {
        // 初始化所有数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        List<String> servers = DataSourceContextHolder.getServers();
        for (int i=0; i!=servers.size();++i){
            String serverName = servers.get(i);
            DriverManagerDataSource dataSource = (DriverManagerDataSource)applicationContext.getBean(serverName);
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            targetDataSources.put(serverName, dataSource);
            if(i==0){
                super.setDefaultTargetDataSource(dataSource);
            }
        }
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
}
