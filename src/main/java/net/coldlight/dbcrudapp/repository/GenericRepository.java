package net.coldlight.dbcrudapp.repository;

import net.coldlight.dbcrudapp.model.Developer;

import java.util.List;

public interface GenericRepository<T, ID> {
        List<T> getAll();
        T getByID(ID id);
        T save(T t);
        T update(T t);
        void delete(T t);
}

