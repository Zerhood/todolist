package com.example.todolist.service;


import com.example.todolist.entity.ToDoList;

import java.util.List;

public interface ToDoListService {
    ToDoList findById(int id);
    List<ToDoList> findAll();
    ToDoList saveList(ToDoList list);
    void deleteById(int id);
    void deleteAll();
}