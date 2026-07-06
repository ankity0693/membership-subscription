package com.assignment.firstclub.common.data;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Storage {

    private final Map<Class<?>, Map<Long, BaseEntity>> storage = new ConcurrentHashMap<>();

    public <T extends BaseEntity> T save(T entity) {

        Map<Long, BaseEntity> entities =
                storage.computeIfAbsent(entity.getClass(), k -> new ConcurrentHashMap<>());

        if (entity.getId() == null) {
            entity.setId(generateId(entities));
        }

        entities.put(entity.getId(), entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> T get(Class<T> clazz, Long id) {

        Map<Long, BaseEntity> entities = storage.get(clazz);

        if (entities == null) {
            return null;
        }

        return (T) entities.get(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> List<T> getAll(Class<T> clazz) {

        Map<Long, BaseEntity> entities = storage.get(clazz);

        if (entities == null) {
            return Collections.emptyList();
        }

        return entities.values()
                .stream()
                .map(entity -> (T) entity)
                .toList();
    }

    public <T extends BaseEntity> boolean delete(Class<T> clazz, Long id) {

        Map<Long, BaseEntity> entities = storage.get(clazz);

        if (entities == null) {
            return false;
        }

        return entities.remove(id) != null;
    }

    public <T extends BaseEntity> T update(T entity) {

        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity id cannot be null.");
        }

        Map<Long, BaseEntity> entities =
                storage.computeIfAbsent(entity.getClass(), k -> new ConcurrentHashMap<>());

        entities.put(entity.getId(), entity);

        return entity;
    }

    private Long generateId(Map<Long, BaseEntity> entities) {
        return (long) (entities.size() + 1);
    }
}