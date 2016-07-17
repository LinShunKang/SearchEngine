package com.lsk.search.service.impl;

import com.lsk.log.LoggerManager;
import com.lsk.search.dao.DocumentDAO;
import com.lsk.search.model.Document;
import com.lsk.search.service.DocumentService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;


/**
 * Created by LinShunkang on 6/27/16.
 */
public class DocumentServiceImpl implements DocumentService, InitializingBean {

    private DocumentDAO documentDAO;

    @Override
    public boolean save(Document document) {
        try {
            long id = documentDAO.save(document);
            if (id > 0) {
                document.setId(id);
                return true;
            }
        } catch (Exception e) {
            LoggerManager.error("DocumentServiceImpl.save({})", document, e);
        }
        return false;
    }

    @Override
    public Document getById(long id) {
        try {
            return documentDAO.getById(id);
        } catch (Exception e) {
            LoggerManager.error("DocumentServiceImpl.getById({})", id, e);
        }
        return null;
    }

    @Override
    public List<Document> getByIdList(List<Long> idList) {
        try {
            return documentDAO.getByIdList(idList);
        } catch (Exception e) {
            LoggerManager.error("DocumentServiceImpl.getByIdList({})", idList, e);
        }
        return null;
    }

    public void setDocumentDAO(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(documentDAO, "documentDAO is required!");
    }
}
