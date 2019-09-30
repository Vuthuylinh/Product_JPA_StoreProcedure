package linhVu.repository;

import java.util.List;

public interface GeneralRepository<E> {
    List<E> findAll();
    void save(E e);

    void add(E e);
}
