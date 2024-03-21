package com.example.demo.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RestService根据配置自动注入Bean
 *
 * @author: xxx
 * @date: 2020-09-11 9:28
 * ApplicationContextAware 是为了重新获取bean而实现，不用可以不添加，后续会说明
 */
@Component
@Slf4j
public class RestServiceBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private Environment environment;

    //配置文件前缀
    private static final String DATA_PREFIX = "data";
    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        try {
            //获取配置文件配置，并绑定
            BindResult<List<DataServer>> dataServerBindResult = Binder.get(environment).bind(DATA_PREFIX, Bindable.listOf(DataServer.class));
            List<DataServer> dataServerList = dataServerBindResult.get();
            //List<String> dataServerNames = new ArrayList<>();
            for(DataServer dataServer: dataServerList){
//                DriverManagerDataSource dataSource = new DriverManagerDataSource();
//                dataSource.setUrl(dataServer.getUrl());
//                dataSource.setUsername(dataServer.username);
//                dataSource.setPassword(dataServer.getPassword());
                //创建bean
                BeanDefinitionBuilder dataSourceBean = BeanDefinitionBuilder.rootBeanDefinition(DriverManagerDataSource.class);
                //向bean中注入配置文件的配置内容
                dataSourceBean.addPropertyValue("url",dataServer.getUrl());
                dataSourceBean.addPropertyValue("username", dataServer.getUsername());
                dataSourceBean.addPropertyValue("password", dataServer.getPassword());
                //注册beanDefinition，并定义好bean的名称
                beanDefinitionRegistry.registerBeanDefinition(dataServer.getName(), dataSourceBean.getBeanDefinition());
                //将bean的名称存储起来
                //dataServerNames.add(dataServer.getName());
                DataSourceContextHolder.addServer(dataServer.getName());
            }
        } catch (Exception e) {
            log.error("Init rest bean failed!", e);
            throw new BeanCreationException("初始化RestService失败!");
        }
    }

    @Override
    public void setEnvironment(@NonNull final Environment environment) {
        this.environment = environment;
    }


    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {


    }

}