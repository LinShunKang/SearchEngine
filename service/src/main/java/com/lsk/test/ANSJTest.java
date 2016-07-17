package com.lsk.test;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.IndexAnalysis;

import java.util.List;

/**
 * Created by LinShunkang on 7/3/16.
 */
public class ANSJTest {
    public static void main(String[] args) {
        Result result = IndexAnalysis.parse("你好吗？ 你好吗?你好！我很好啊！");
        List<Term> termList = result.getTerms();
        for (int i = 0; i < termList.size(); ++i) {
            Term term = termList.get(i);
            System.out.println(term);
        }
    }
}
