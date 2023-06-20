package com.rtarita.yourbuild.generator;

import com.github.javafaker.service.RandomService;
import com.rtarita.yourbuild.model.ModelSchema;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class RecordCache implements List<Map<String, Object>> {
    private final ModelSchema<?> modelSchema;
    private final List<Map<String, Object>> cache;
    private int alreadyUsedCursor = 0;
    private int end = 0;

    public RecordCache(int initialSize, ModelSchema<?> modelSchema) {
        cache = new ArrayList<>(initialSize);
        this.modelSchema = modelSchema;
    }

    public ModelSchema<?> getModelSchema() {
        return modelSchema;
    }

    public void addRecord(Map<String, Object> record) {
        ++end;
        cache.add(record);
    }

    public Map<String, Object> getNextUnused() {
        if (++alreadyUsedCursor == end) throw new IllegalStateException("all records used");
        if (alreadyUsedCursor == cache.size()) {
            alreadyUsedCursor = 0;
        }
        return cache.get(alreadyUsedCursor);

    }

    public Map<String, Object> getRandom(RandomService rs) {
        return cache.get(rs.nextInt(cache.size()));
    }

    public void reset() {
        end = alreadyUsedCursor;
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return cache.contains(o);
    }

    @NotNull
    @Override
    public Iterator<Map<String, Object>> iterator() {
        return cache.iterator();
    }

    @NotNull
    @Override
    public Object @NotNull [] toArray() {
        return cache.toArray();
    }

    @NotNull
    @Override
    public <T> T @NotNull [] toArray(@NotNull T[] a) {
        return cache.toArray(a);
    }

    @Override
    public boolean add(Map<String, Object> record) {
        return cache.add(record);
    }

    @Override
    public boolean remove(Object o) {
        return cache.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return cache.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Map<String, Object>> c) {
        return cache.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends Map<String, Object>> c) {
        return cache.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return cache.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return cache.retainAll(c);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public Map<String, Object> get(int index) {
        return cache.get(index);
    }

    @Override
    public Map<String, Object> set(int index, Map<String, Object> element) {
        return cache.set(index, element);
    }

    @Override
    public void add(int index, Map<String, Object> element) {
        cache.add(index, element);
    }

    @Override
    public Map<String, Object> remove(int index) {
        return cache.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return cache.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return cache.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<Map<String, Object>> listIterator() {
        return cache.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<Map<String, Object>> listIterator(int index) {
        return cache.listIterator(index);
    }

    @NotNull
    @Override
    public List<Map<String, Object>> subList(int fromIndex, int toIndex) {
        return cache.subList(fromIndex, toIndex);
    }
}
