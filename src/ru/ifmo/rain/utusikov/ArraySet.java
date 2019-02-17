package ru.ifmo.rain.utusikov;

import java.util.*;

public class ArraySet<T> extends AbstractSet<T> implements SortedSet<T> {
    private  final List<T> data;
    private final Comparator<? super T> comparator;

    public ArraySet() {
        data = Collections.emptyList();
        comparator = null;
    }

    public ArraySet(List<T> data, Comparator<? super T> comparator) {
        this.data = data;
        this.comparator = comparator;
    }

    public ArraySet(Collection<? extends T> collection, Comparator<? super T> cmp) {
        comparator = cmp;
        TreeSet<T> tmp = new TreeSet<>(cmp);
        tmp.addAll(collection);
        data = new ArrayList<>(tmp);
    }

    public ArraySet(Collection<? extends T> collection) {
        comparator = null;
        TreeSet<T> tmp = new TreeSet<>(comparator);
        tmp.addAll(collection);
        data = new ArrayList<>(tmp);
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableList(data).iterator();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean contains(Object o) {
        return Collections.binarySearch(data, (T) Objects.requireNonNull(o), comparator) >= 0;
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        int first = getIndex(fromElement);
        int last = getIndex(toElement);
        if (last == -1 || first == -1 || first > last) {
            return new ArraySet<>();
        }
        return new ArraySet<>(data.subList(first, last), comparator);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        if (data.size() == 0) {
            return this;
        }
        return subSet(data.get(0), toElement);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        if (data.size() == 0) {
            return this;
        }
        return subSet(fromElement, data.get(data.size() - 1));
    }

    @Override
    public T first() {
        if (data.size() == 0) {
            throw new NoSuchElementException("Fuck");
        }
        return  data.get(0);
    }

    @Override
    public T last() {
        if (data.size() == 0) {
            throw new NoSuchElementException("Fuck");
        }
        return  data.get(data.size() - 1);
    }

    private int getIndex(T key) {
        int index = Collections.binarySearch(data, key, comparator);
        if (index < 0) {
            index = -index - 1;
        }
        return index;
    }
}
