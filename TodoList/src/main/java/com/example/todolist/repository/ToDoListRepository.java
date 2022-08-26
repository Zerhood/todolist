package com.example.todolist.repository;

import com.example.todolist.entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {}