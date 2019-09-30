package linhVu.service;

import java.util.List;

public interface GeneralService <E>{
    List<E> findAll();
    void save(E e);

    void add(E e);
}
