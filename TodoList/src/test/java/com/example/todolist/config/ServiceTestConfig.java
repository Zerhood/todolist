package com.example.todolist.config;

import com.example.todolist.repository.ToDoListRepository;
import com.example.todolist.service.ToDoListServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class ServiceTestConfig {

    @Bean
    public ToDoListServiceImpl service() {
        return new ToDoListServiceImpl(repository());
    }

    @Bean
    public ToDoListRepository repository() {
        return mock(ToDoListRepository.class);
    }
}
