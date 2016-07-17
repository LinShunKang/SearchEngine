package com.lsk.database;

import com.alibaba.druid.pool.DruidDataSource;
import net.paoding.rose.jade.context.application.JadeFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * Created by LinShunkang on 7/2/16.
 */
public class MyJadeDAOBeanFactory<T> implements ApplicationContextAware, FactoryBean, InitializingBean {

    private ApplicationContext applicationContext;

    private JadeFactory jadeFactory;

    private Class<T> daoClass;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataSource dataSource = applicationContext.getBean("druidDataSource", DruidDataSource.class);
        jadeFactory = new JadeFactory(dataSource);
        Assert.notNull(daoClass, "daoClass is required!");
    }

    @Override
    public Object getObject() {
        return jadeFactory.create(daoClass);
    }

    @Override
    public Class<?> getObjectType() {
        return daoClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setDaoClass(Class<T> daoClass) {
        this.daoClass = daoClass;
    }
}
