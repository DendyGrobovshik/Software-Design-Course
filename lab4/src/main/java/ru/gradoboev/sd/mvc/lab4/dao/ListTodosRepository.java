package ru.gradoboev.sd.mvc.lab4.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gradoboev.sd.mvc.lab4.model.ListTodo;

@Repository
public interface ListTodosRepository extends CrudRepository<ListTodo, Long> {
}
