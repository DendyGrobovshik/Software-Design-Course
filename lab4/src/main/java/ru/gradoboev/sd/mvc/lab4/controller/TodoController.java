package ru.gradoboev.sd.mvc.lab4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gradoboev.sd.mvc.lab4.dao.ListTodosRepository;
import ru.gradoboev.sd.mvc.lab4.dao.TaskRepository;
import ru.gradoboev.sd.mvc.lab4.model.ListTodo;
import ru.gradoboev.sd.mvc.lab4.model.Task;


@Controller
public class TodoController {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ListTodosRepository listRepository;

    @GetMapping("/")
    public String lists(Model map) {
        map.addAttribute("lists", listRepository.findAll());
        map.addAttribute("newList", new ListTodo());

        return "todoLists";
    }

    @PostMapping(value = "/createList")
    public String createListTodo(@ModelAttribute ListTodo listTodos) {
        listRepository.save(listTodos);

        return "redirect:/";
    }

    @PostMapping(value = "/removeList")
    public String removeListTodo(@ModelAttribute("listId") long listId) {
        listRepository.deleteById(listId);

        return "redirect:/";
    }

    @GetMapping("/tasks")
    public String tasks(@RequestParam long listId, Model map) {
        map.addAttribute("tasks", taskRepository.getAllByListId(listId));
        map.addAttribute("newTask", new Task(listId));

        return "todoTasks";
    }

    @PostMapping(value = "/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskRepository.save(task);

        return "redirect:/tasks?listId=" + task.getListId();
    }

    @PostMapping(value = "/markTask")
    public String markTask(@ModelAttribute("listId") long listId,
                               @ModelAttribute("taskId") long taskId, @ModelAttribute("done") boolean done) {
        taskRepository.updateDone(taskId, done);

        return "redirect:/tasks?listId=" + listId;
    }

    @PostMapping(value = "/removeTask")
    public String removeTask(@ModelAttribute("listId") long listId,
                           @ModelAttribute("taskId") long taskId) {
        taskRepository.deleteById(taskId);

        return "redirect:/tasks?listId=" + listId;
    }
}
