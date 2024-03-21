package com.example.demo.util;


import lombok.NonNull;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Configuration
public class DynamicDataServerConfig{
//
//    private ApplicationContext applicationContext;
//
//    @Primary
//    @Bean(name = "dynamicDataSource")
//    public DataSource dynamicDataSource() {
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        List<String> servers = DataSourceContextHolder.getServers();
//        DynamicDataServerRouter router = new DynamicDataServerRouter();
//        for (int i=0; i!=servers.size();++i){
//            String serverName = servers.get(i);
//            DriverManagerDataSource dataSource = (DriverManagerDataSource)applicationContext.getBean(serverName);
//            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            targetDataSources.put(serverName, dataSource);
//            if(i==0){
//                router.setDefaultTargetDataSource(dataSource);
//            }
//        }
//        router.setTargetDataSources(targetDataSources);
//        return router;
//    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Autowired DataSource DynamicDataServerRouter) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(DynamicDataServerRouter);
        return sessionFactory.getObject();
    }

//    @Override
//    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }

//    @Bean
//    public PlatformTransactionManager transactionManager(@Autowired DataSource dynamicDataSource) {
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setDataSource(dynamicDataSource);
//        return txManager;
//    }
}

