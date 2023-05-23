package uz.pdp.myFirstBot.service;

import java.util.ArrayList;

public abstract class BaseService<E> {
    protected abstract void create(E e);

    protected abstract E getById(Long id);

    protected abstract ArrayList<E> getAllElements();
}
