package ru.gradoboev.sd.mvc.lab4.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gradoboev.sd.mvc.lab4.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> getAllByListId(long id);

    @Transactional
    @Modifying
    @Query("update Task t set t.done = :done where t.id = :id")
    void updateDone(@Param("id") long id, @Param("done") boolean done);
}
