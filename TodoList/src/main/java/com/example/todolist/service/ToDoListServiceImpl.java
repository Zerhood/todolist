package com.example.todolist.service;

import com.example.todolist.entity.ToDoList;
import com.example.todolist.repository.ToDoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    private final ToDoListRepository repository;

    public ToDoListServiceImpl(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public ToDoList findById(int id) {
        ToDoList toDoList = null;
        Optional<ToDoList> optional = repository.findById(id);
        if (optional.isPresent()) toDoList = optional.get();
        return toDoList;
    }

    @Override
    public List<ToDoList> findAll() {
        return repository.findAll();
    }

    @Override
    public ToDoList saveList(ToDoList list) {
        return repository.save(list);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}