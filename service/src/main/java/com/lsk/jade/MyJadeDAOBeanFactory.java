package com.lsk.jade;

import com.alibaba.druid.pool.DruidDataSource;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.context.application.JadeFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * Created by LinShunkang on 7/2/16.
 */
public class MyJadeDAOBeanFactory<T> implements ApplicationContextAware, FactoryBean, InitializingBean {

    private static String URL_TEMPLATE = "jdbc:mysql://127.0.0.1:3306/%s?useUnicode=true&amp;characterEncoding=utf-8";

    private ApplicationContext applicationContext;

    private JadeFactory jadeFactory;

    private Class<T> daoClass;


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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DAO daoAnnotation = daoClass.getAnnotation(DAO.class);
        DruidDataSource dataSource = applicationContext.getBean("druidDataSource", DruidDataSource.class);
        dataSource.setUrl(String.format(URL_TEMPLATE, daoAnnotation.catalog()));
        jadeFactory = new JadeFactory(dataSource);
        Assert.notNull(daoClass, "daoClass is required!");
    }
}
