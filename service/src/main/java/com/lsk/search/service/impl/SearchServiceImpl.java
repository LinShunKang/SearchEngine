package com.lsk.search.service.impl;

import com.lsk.log.LoggerManager;
import com.lsk.search.model.Document;
import com.lsk.search.service.DocumentService;
import com.lsk.search.service.SearchEngine;
import com.lsk.search.service.SearchService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
            return getDocList(docIdList);
        } catch (Exception e) {
            LoggerManager.error("SearchServiceImpl.suggest({}, {})", sentence, count, e);
        }
        return EMPTY_DOC_LIST;
    }

    private List<Document> getDocList(List<Long> docIdList) {
        List<Document> documentList = documentService.getByIdList(docIdList);
        if (CollectionUtils.isEmpty(docIdList)) {
            return EMPTY_DOC_LIST;
        }

        Map<Long, Document> idDocMap = getIdDocMap(documentList);
        return getDocList(docIdList, idDocMap);
    }

    private Map<Long, Document> getIdDocMap(List<Document> documentList) {
        Map<Long, Document> idDocMap = new HashedMap<Long, Document>((int) (documentList.size() / 0.75 + 1));
        for (int i = 0; i < documentList.size(); ++i) {
            Document doc = documentList.get(i);
            idDocMap.put(doc.getId(), doc);
        }
        return idDocMap;
    }

    private List<Document> getDocList(List<Long> docIdList, Map<Long, Document> idDocMap) {
        List<Document> result = new ArrayList<Document>(docIdList.size());
        for (int i = 0; i < docIdList.size(); ++i) {
            long docId = docIdList.get(i);
            Document doc = idDocMap.get(docId);
            if (doc != null) {
                result.add(doc);
            }
        }
        return result;
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
