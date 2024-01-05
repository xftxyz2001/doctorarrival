package com.xftxyz.doctorarrival.sdk.vo.api;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * 批量更新科室请求
 */
public class BatchUpdateDepartmentRequest implements List<UpdateDepartmentRequest>{
    private List<UpdateDepartmentRequest> updateDepartmentRequests;

    public BatchUpdateDepartmentRequest(){
        updateDepartmentRequests = new ArrayList<>();
    }

    @Override
    public int size() {
        return updateDepartmentRequests.size();
    }

    @Override
    public boolean isEmpty() {
        return updateDepartmentRequests.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return updateDepartmentRequests.contains(o);
    }

    @Override
    public Iterator<UpdateDepartmentRequest> iterator() {
        return updateDepartmentRequests.iterator();
    }

    @Override
    public Object[] toArray() {
        return updateDepartmentRequests.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return updateDepartmentRequests.toArray(a);
    }

    @Override
    public boolean add(UpdateDepartmentRequest updateDepartmentRequest) {
        return updateDepartmentRequests.add(updateDepartmentRequest);
    }

    @Override
    public boolean remove(Object o) {
        return updateDepartmentRequests.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return updateDepartmentRequests.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends UpdateDepartmentRequest> c) {
        return updateDepartmentRequests.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends UpdateDepartmentRequest> c) {
        return updateDepartmentRequests.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return updateDepartmentRequests.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return updateDepartmentRequests.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<UpdateDepartmentRequest> operator) {
        updateDepartmentRequests.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super UpdateDepartmentRequest> c) {
        updateDepartmentRequests.sort(c);
    }

    @Override
    public void clear() {
        updateDepartmentRequests.clear();
    }

    @Override
    public boolean equals(Object o) {
        return updateDepartmentRequests.equals(o);
    }

    @Override
    public int hashCode() {
        return updateDepartmentRequests.hashCode();
    }

    @Override
    public UpdateDepartmentRequest get(int index) {
        return updateDepartmentRequests.get(index);
    }

    @Override
    public UpdateDepartmentRequest set(int index, UpdateDepartmentRequest element) {
        return updateDepartmentRequests.set(index, element);
    }

    @Override
    public void add(int index, UpdateDepartmentRequest element) {
        updateDepartmentRequests.add(index, element);
    }

    @Override
    public UpdateDepartmentRequest remove(int index) {
        return updateDepartmentRequests.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return updateDepartmentRequests.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return updateDepartmentRequests.lastIndexOf(o);
    }

    @Override
    public ListIterator<UpdateDepartmentRequest> listIterator() {
        return updateDepartmentRequests.listIterator();
    }

    @Override
    public ListIterator<UpdateDepartmentRequest> listIterator(int index) {
        return updateDepartmentRequests.listIterator(index);
    }

    @Override
    public List<UpdateDepartmentRequest> subList(int fromIndex, int toIndex) {
        return updateDepartmentRequests.subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<UpdateDepartmentRequest> spliterator() {
        return updateDepartmentRequests.spliterator();
    }

    @Override
    public void addFirst(UpdateDepartmentRequest updateDepartmentRequest) {
        updateDepartmentRequests.addFirst(updateDepartmentRequest);
    }

    @Override
    public void addLast(UpdateDepartmentRequest updateDepartmentRequest) {
        updateDepartmentRequests.addLast(updateDepartmentRequest);
    }

    @Override
    public UpdateDepartmentRequest getFirst() {
        return updateDepartmentRequests.getFirst();
    }

    @Override
    public UpdateDepartmentRequest getLast() {
        return updateDepartmentRequests.getLast();
    }

    @Override
    public UpdateDepartmentRequest removeFirst() {
        return updateDepartmentRequests.removeFirst();
    }

    @Override
    public UpdateDepartmentRequest removeLast() {
        return updateDepartmentRequests.removeLast();
    }

    @Override
    public List<UpdateDepartmentRequest> reversed() {
        return updateDepartmentRequests.reversed();
    }

    public static <E> List<E> of() {
        return List.of();
    }

    public static <E> List<E> of(E e1) {
        return List.of(e1);
    }

    public static <E> List<E> of(E e1, E e2) {
        return List.of(e1, e2);
    }

    public static <E> List<E> of(E e1, E e2, E e3) {
        return List.of(e1, e2, e3);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4) {
        return List.of(e1, e2, e3, e4);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5) {
        return List.of(e1, e2, e3, e4, e5);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6) {
        return List.of(e1, e2, e3, e4, e5, e6);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return List.of(e1, e2, e3, e4, e5, e6, e7);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return List.of(e1, e2, e3, e4, e5, e6, e7, e8);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return List.of(e1, e2, e3, e4, e5, e6, e7, e8, e9);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return List.of(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
    }

    @SafeVarargs
    public static <E> List<E> of(E... elements) {
        return List.of(elements);
    }

    public static <E> List<E> copyOf(Collection<? extends E> coll) {
        return List.copyOf(coll);
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return updateDepartmentRequests.toArray(generator);
    }

    @Override
    public boolean removeIf(Predicate<? super UpdateDepartmentRequest> filter) {
        return updateDepartmentRequests.removeIf(filter);
    }

    @Override
    public Stream<UpdateDepartmentRequest> stream() {
        return updateDepartmentRequests.stream();
    }

    @Override
    public Stream<UpdateDepartmentRequest> parallelStream() {
        return updateDepartmentRequests.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super UpdateDepartmentRequest> action) {
        updateDepartmentRequests.forEach(action);
    }
}
