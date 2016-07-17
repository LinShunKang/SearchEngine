package com.lsk.test;

import com.lsk.search.model.Document;
import com.lsk.search.service.DocumentService;
import com.lsk.search.service.SearchService;
import com.lsk.search.service.impl.DocumentServiceImpl;
import com.lsk.search.service.impl.SearchServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by LinShunkang on 6/27/16.
 */
public class SpringContextTest {

    private static String[] CONFIG_PATH = {"classpath*:spring/*.xml"};

    final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_PATH);

    public static void main(String[] args) throws InterruptedException {
//        new SpringContextTest().test();
        ANSJTest.main(null);
        new SpringContextTest().testSearchService();
    }

    private void test() {
        DocumentService documentService = context.getBean(DocumentServiceImpl.class);
        Document document1 = documentService.getById(1);
        System.out.println(document1);
    }

    private void testSearchService() {
        SearchService searchService = context.getBean(SearchServiceImpl.class);
        searchService.addSearchIndex("全文搜索引擎", "全文搜索引擎是目前广泛应用的主流搜索引擎。它的工作原理是计算机索引程序通过扫描文章中的每一个词，对每一个词建立一个索引，指明该词在文章中出现的次数和位置，当用户查询时，检索程序就根据事先建立的索引进行查找，并将查找的结果反馈给用户的检索方式。这个过程类似于通过字典中的检索字表查字的过程。");

    }
}
