package com.dental.service;

import com.dental.dao.entity.User;

import java.util.List;

/**
 * Created by light on 3/28/2015.
 */

public interface UserService<T> {

    T get(int id);

    void save(T entity);

    List<T> getList();

    void remove(int id);

    void update(T entity);
}
