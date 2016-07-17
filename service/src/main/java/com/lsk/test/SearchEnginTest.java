package com.lsk.test;

import com.lsk.search.model.Document;
import com.lsk.search.service.SearchService;
import com.lsk.search.service.impl.SearchServiceImpl;
import com.lsk.utils.Performance;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinShunkang on 7/4/16.
 */
public class SearchEnginTest {
    private static String[] CONFIG_PATH = {"classpath*:spring/*.xml"};

    final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_PATH);

    public static void main(String[] args) throws InterruptedException {
//        new SpringContextTest().test();
        ANSJTest.main(null);
        SearchEnginTest searchEnginTest = new SearchEnginTest();
//        searchEnginTest.initDocList();

        for (int i = 0; i < 1; ++i) {
            searchEnginTest.search();
        }
    }

    private void initDocList() {
        SearchService searchService = context.getBean(SearchServiceImpl.class);
        List<Document> documentList = getDocumentList();
        for (int i = 0; i < documentList.size(); ++i) {
            Document document = documentList.get(i);
            searchService.addSearchIndex(document.getTitle(), document.getBody());
        }
        System.out.println("done!");
    }

    private List<Document> getDocumentList() {
        List<Document> documentList = new ArrayList<Document>();
        documentList.add(Document.getInstance("算法", "算法（Algorithm）是指解题方案的准确而完整的描述描述描述描述，是一系列解决问题的清晰指令，算法代表着用系统的方法描述解决问题的策略机制。"));
        documentList.add(Document.getInstance("也就是说", "也就是说，能够对一定规范的输入，在有限时间内获得所要求的输出\""));
        documentList.add(Document.getInstance("如果一个算法有缺陷", "如果一个算法有缺陷，或不适合于某个问题，执行这个算法将不会解决这个问题。"));
        documentList.add(Document.getInstance("空间复杂度与时间复杂度", "一个算法的优劣可以用空间复杂度与时间复杂度来衡量。"));
        documentList.add(Document.getInstance("指令描述", "算法中的指令描述的是一个计算，当其运行时能从一个初始状态和（可能为空的）初始输入开始，经过一系列有限而清晰定义的状态，最终产生输出并停止于一个终态。"));
        documentList.add(Document.getInstance("状态的转移", "一个状态到另一个状态的转移不一定是确定的。"));
        return documentList;
    }

    private void search() {
        SearchService searchService = context.getBean(SearchServiceImpl.class);
        long start = System.nanoTime();
//        List<Document> documents = searchService.suggest("算法", 3);
//        List<Document> documents = searchService.suggest("描述", 3);
        List<Document> documents = searchService.suggest("状态的转移", 3);
        System.out.println(Performance.getTimeCost2Millis(start));
        print(documents);
    }

    private void print(List<Document> documents) {
        for (int i = 0; i < documents.size(); ++i) {
            System.out.println(documents.get(i));
        }
    }
}
