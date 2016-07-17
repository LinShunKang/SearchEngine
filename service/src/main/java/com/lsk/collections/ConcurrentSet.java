package com.lsk.collections;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by LinShunkang on 7/3/16.
 */
public class ConcurrentSet<T> extends AbstractSet<T> implements Set<T> {

    private ConcurrentMap<T, Boolean> concurrentMap;

    public ConcurrentSet(int size) {
        concurrentMap = new ConcurrentHashMap<T, Boolean>(size);
    }

    public ConcurrentSet() {
        concurrentMap = new ConcurrentHashMap<T, Boolean>();
    }

    @Override
    public int size() {
        return concurrentMap.size();
    }

    @Override
    public boolean isEmpty() {
        return concurrentMap.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return concurrentMap.get(o) != null;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return concurrentMap.put(t, Boolean.TRUE);
    }

    @Override
    public boolean remove(Object o) {
        return concurrentMap.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("暂不支持！");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("暂不支持！");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("暂不支持！");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("暂不支持！");
    }

    @Override
    public void clear() {
        concurrentMap.clear();
    }
}
