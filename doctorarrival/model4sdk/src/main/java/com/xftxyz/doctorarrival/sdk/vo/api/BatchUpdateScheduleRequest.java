package com.xftxyz.doctorarrival.sdk.vo.api;

import lombok.Data;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * 批量更新排班请求
 */
public class BatchUpdateScheduleRequest implements List<UpdateScheduleRequest>{
    private List<UpdateScheduleRequest> updateScheduleRequests;

    public BatchUpdateScheduleRequest(){
        updateScheduleRequests = new ArrayList<>();
    }

    @Override
    public int size() {
        return updateScheduleRequests.size();
    }

    @Override
    public boolean isEmpty() {
        return updateScheduleRequests.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return updateScheduleRequests.contains(o);
    }

    @Override
    public Iterator<UpdateScheduleRequest> iterator() {
        return updateScheduleRequests.iterator();
    }

    @Override
    public Object[] toArray() {
        return updateScheduleRequests.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return updateScheduleRequests.toArray(a);
    }

    @Override
    public boolean add(UpdateScheduleRequest updateScheduleRequest) {
        return updateScheduleRequests.add(updateScheduleRequest);
    }

    @Override
    public boolean remove(Object o) {
        return updateScheduleRequests.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return updateScheduleRequests.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends UpdateScheduleRequest> c) {
        return updateScheduleRequests.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends UpdateScheduleRequest> c) {
        return updateScheduleRequests.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return updateScheduleRequests.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return updateScheduleRequests.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<UpdateScheduleRequest> operator) {
        updateScheduleRequests.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super UpdateScheduleRequest> c) {
        updateScheduleRequests.sort(c);
    }

    @Override
    public void clear() {
        updateScheduleRequests.clear();
    }

    @Override
    public boolean equals(Object o) {
        return updateScheduleRequests.equals(o);
    }

    @Override
    public int hashCode() {
        return updateScheduleRequests.hashCode();
    }

    @Override
    public UpdateScheduleRequest get(int index) {
        return updateScheduleRequests.get(index);
    }

    @Override
    public UpdateScheduleRequest set(int index, UpdateScheduleRequest element) {
        return updateScheduleRequests.set(index, element);
    }

    @Override
    public void add(int index, UpdateScheduleRequest element) {
        updateScheduleRequests.add(index, element);
    }

    @Override
    public UpdateScheduleRequest remove(int index) {
        return updateScheduleRequests.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return updateScheduleRequests.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return updateScheduleRequests.lastIndexOf(o);
    }

    @Override
    public ListIterator<UpdateScheduleRequest> listIterator() {
        return updateScheduleRequests.listIterator();
    }

    @Override
    public ListIterator<UpdateScheduleRequest> listIterator(int index) {
        return updateScheduleRequests.listIterator(index);
    }

    @Override
    public List<UpdateScheduleRequest> subList(int fromIndex, int toIndex) {
        return updateScheduleRequests.subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<UpdateScheduleRequest> spliterator() {
        return updateScheduleRequests.spliterator();
    }

    @Override
    public void addFirst(UpdateScheduleRequest updateScheduleRequest) {
        updateScheduleRequests.addFirst(updateScheduleRequest);
    }

    @Override
    public void addLast(UpdateScheduleRequest updateScheduleRequest) {
        updateScheduleRequests.addLast(updateScheduleRequest);
    }

    @Override
    public UpdateScheduleRequest getFirst() {
        return updateScheduleRequests.getFirst();
    }

    @Override
    public UpdateScheduleRequest getLast() {
        return updateScheduleRequests.getLast();
    }

    @Override
    public UpdateScheduleRequest removeFirst() {
        return updateScheduleRequests.removeFirst();
    }

    @Override
    public UpdateScheduleRequest removeLast() {
        return updateScheduleRequests.removeLast();
    }

    @Override
    public List<UpdateScheduleRequest> reversed() {
        return updateScheduleRequests.reversed();
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
        return updateScheduleRequests.toArray(generator);
    }

    @Override
    public boolean removeIf(Predicate<? super UpdateScheduleRequest> filter) {
        return updateScheduleRequests.removeIf(filter);
    }

    @Override
    public Stream<UpdateScheduleRequest> stream() {
        return updateScheduleRequests.stream();
    }

    @Override
    public Stream<UpdateScheduleRequest> parallelStream() {
        return updateScheduleRequests.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super UpdateScheduleRequest> action) {
        updateScheduleRequests.forEach(action);
    }
}
