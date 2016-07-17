package com.lsk.search.service.impl;

import com.lsk.log.LoggerManager;
import com.lsk.search.dao.WordPositionDAO;
import com.lsk.search.model.WordPosition;
import com.lsk.search.service.WordPositionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Created by LinShunkang on 6/27/16.
 */
public class WordPositionServiceImpl implements WordPositionService, InitializingBean {

    private WordPositionDAO wordPositionDAO;


    @Override
    public boolean save(WordPosition wordPosition) {
        try {

        } catch (Exception e) {
            LoggerManager.error("WordPositionServiceImpl.save({})", wordPosition, e);
        }
        return false;
    }

    @Override
    public boolean save(List<WordPosition> wordPositionList) {
        try {
            wordPositionDAO.save(wordPositionList);
            return true;
        } catch (Exception e) {
            LoggerManager.error("WordPositionServiceImpl.save({})", wordPositionList, e);
        }
        return false;
    }

    @Override
    public Map<Long, List<Integer>> getAllWordPosMap() {
        try {
            List<WordPosition> all = wordPositionDAO.getAll();
            if (CollectionUtils.isEmpty(all)) {
                return new HashMap<Long, List<Integer>>(0);
            }

            Map<Long, List<Integer>> map = new HashMap<Long, List<Integer>>(all.size() / 10);
            for (int i = 0; i < all.size(); ++i) {
                WordPosition position = all.get(i);
                long ftsIndexId = position.getFtsIndexId();
                List<Integer> offsetList = map.get(ftsIndexId);
                if (offsetList == null) {
                    offsetList = new ArrayList<Integer>(10);
                    map.put(ftsIndexId, offsetList);
                }
                offsetList.add(position.getPosition());
            }
            sortOffsetList(map);
            return map;
        } catch (Exception e) {
            LoggerManager.error("WordPositionServiceImpl.getAllWordPosMap({})", e);
        }
        return new HashMap<Long, List<Integer>>(0);
    }

    private void sortOffsetList(Map<Long, List<Integer>> map) {
        for (Map.Entry<Long, List<Integer>> entry : map.entrySet()) {
            List<Integer> offsetList = entry.getValue();
            Collections.sort(offsetList);
        }
    }

    public void setWordPositionDAO(WordPositionDAO wordPositionDAO) {
        this.wordPositionDAO = wordPositionDAO;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(wordPositionDAO, "wordPositionDAO is required!");
    }
}
