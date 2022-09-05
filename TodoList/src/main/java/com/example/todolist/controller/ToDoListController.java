package com.example.todolist.controller;

import com.example.todolist.entity.ToDoList;
import com.example.todolist.service.ToDoListServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ToDoListController {

    private final ToDoListServiceImpl service;

    public ToDoListController(ToDoListServiceImpl service) {
        this.service = service;
    }

    @GetMapping(value = {"/", "/todolist"})
    public String getList(Model model) {
        List<ToDoList> lists = service.findAll();
        model.addAttribute("lists", lists);
        return "index";
    }

    @GetMapping("/todolist-create")
    public String createListForm(ToDoList toDoList) {
        return "todolist-create";
    }

    @PostMapping("/todolist-create")
    public String createList(ToDoList toDoList) {
        service.saveList(toDoList);
        return "redirect:/todolist";
    }


    @GetMapping("/todolist-update/{id}")
    public String updateList(@PathVariable int id, Model model) {
        ToDoList toDoList = service.findById(id);
        model.addAttribute("list", toDoList);
        return "/todolist-update";
    }

    @PostMapping("/todolist-update")
    public String updateList(ToDoList toDoList) {
        service.saveList(toDoList);
        return "redirect:/todolist";
    }

    @GetMapping("todolist-delete/{id}")
    public String deleteList(@PathVariable int id) {
        service.deleteById(id);
        return "redirect:/todolist";
    }

    @ModelAttribute("list")
    public ToDoList defaultInstance() {
        return new ToDoList();
    }
}