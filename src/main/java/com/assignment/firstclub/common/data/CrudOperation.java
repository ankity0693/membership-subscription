package com.assignment.firstclub.common.data;

import java.util.List;

public abstract class CrudOperation<T extends BaseEntity> {

     protected final Storage storage;
     private final Class<T> entityClass;

     protected CrudOperation(Storage storage, Class<T> entityClass) {
          this.storage = storage;
          this.entityClass = entityClass;
     }

     public T create(T entity) {
          return storage.save(entity);
     }

     public T get(Long id) {
          return storage.get(entityClass, id);
     }

     public List<T> getAll() {
          return storage.getAll(entityClass);
     }

     public T update(T entity) {
          return storage.update(entity);
     }

     public boolean delete(Long id) {
          return storage.delete(entityClass, id);
     }
}
