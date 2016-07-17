package com.lsk.search.service.impl;

import com.lsk.log.LoggerManager;
import com.lsk.search.dao.FTSIndexDAO;
import com.lsk.search.model.FTSIndex;
import com.lsk.search.service.FTSIndexService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by LinShunkang on 6/27/16.
 */
public class FTSIndexServiceImpl implements FTSIndexService, InitializingBean {

    private static final List<FTSIndex> EMPTY_FTS_INDEX_LIST = Collections.unmodifiableList(new ArrayList<FTSIndex>(0));

    private FTSIndexDAO ftsIndexDAO;

    @Override
    public boolean save(FTSIndex ftsIndex) {
        try {
            long id = ftsIndexDAO.save(ftsIndex);
            if (id > 0L) {
                ftsIndex.setId(id);
                return true;
            } else {
                FTSIndex ftsIndexFromDB = ftsIndexDAO.getByDocIdAndWordId(ftsIndex.getDocId(), ftsIndex.getWordId());
                ftsIndex.setId(ftsIndexFromDB.getId());
                return true;
            }
        } catch (Exception e) {
            LoggerManager.error("FTSIndexServiceImpl.save({})", ftsIndex, e);
        }
        return false;
    }

    @Override
    public List<FTSIndex> getAllFTSIndex() {
        try {
            return ftsIndexDAO.getAll();
        } catch (Exception e) {
            LoggerManager.error("FTSIndexServiceImpl.getAllFTSIndex({})", e);
        }
        return EMPTY_FTS_INDEX_LIST;
    }

    public void setFtsIndexDAO(FTSIndexDAO ftsIndexDAO) {
        this.ftsIndexDAO = ftsIndexDAO;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(ftsIndexDAO, "ftsIndexDAO is required!");
    }
}
