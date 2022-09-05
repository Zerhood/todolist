package com.example.todolist.config;

import com.example.todolist.controller.ToDoListController;
import com.example.todolist.service.ToDoListServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class ControllerTestConfig {

    @Bean
    public ToDoListController controller(){
        return new ToDoListController(service());
    }

    @Bean
    public ToDoListServiceImpl service() {
        return mock(ToDoListServiceImpl.class);
    }
}