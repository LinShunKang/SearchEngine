package com.lsk.search.service.impl;

import com.lsk.log.LoggerManager;
import com.lsk.search.model.Document;
import com.lsk.search.service.DocumentService;
import com.lsk.search.service.SearchEngine;
import com.lsk.search.service.SearchService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by LinShunkang on 7/3/16.
 */
public class SearchServiceImpl implements SearchService, InitializingBean {

    private static final List<Document> EMPTY_DOC_LIST = Collections.unmodifiableList(new ArrayList<Document>(0));

    private SearchEngine searchEngine;

    private DocumentService documentService;


    @Override
    public boolean addSearchIndex(String title, String body) {
        try {
            Document document = Document.getInstance(title, body);
            boolean saveDoc = documentService.save(document);
            if (!saveDoc) {
                LoggerManager.info("documentService.save({})", document);
                return false;
            }

            boolean addIndex = searchEngine.addSearchIndex(document.getId(), body);
            LoggerManager.info("searchEngine.addSearchIndex({}, {}): {}", document.getId(), body, addIndex);
            return addIndex;
        } catch (Exception e) {
            LoggerManager.error("SearchServiceImpl.addSearchIndex({}, {})", title, body, e);
        }
        return false;
    }


    @Override
    public List<Document> suggest(String sentence, int count) {
        try {
            List<Long> docIdList = searchEngine.search(sentence, count);
            if (CollectionUtils.isEmpty(docIdList)) {
                return EMPTY_DOC_LIST;
            }
             //TODO:这里不能直接这么写，因为直接用id in(1,2)这种的话，MySQL会按照主键的顺序返回，而不是按给的ID的顺序返回！！！
            return documentService.getByIdList(docIdList);
        } catch (Exception e) {
            LoggerManager.error("SearchServiceImpl.suggest({}, {})", sentence, count, e);
        }
        return EMPTY_DOC_LIST;
    }

    public void setSearchEngine(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(searchEngine, "searchEngine is required!");
        Assert.notNull(documentService, "documentService is required!");
    }
}
