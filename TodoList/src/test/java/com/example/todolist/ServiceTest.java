package com.example.todolist;

import com.example.todolist.config.ServiceTestConfig;
import com.example.todolist.entity.ToDoList;
import com.example.todolist.repository.ToDoListRepository;
import com.example.todolist.service.ToDoListServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceTestConfig.class)
@WebAppConfiguration
@DisplayName("тестируем сервис")
public class ServiceTest {

    @Autowired
    private ToDoListServiceImpl service;

    @Autowired
    private ToDoListRepository repository;

    private static final Integer id = 1;
    private static final ToDoList todolist = mock(ToDoList.class);

    @BeforeAll
    public static void setUp() {
        doReturn(id).when(todolist).getId();
    }

    @Test
    @DisplayName("достаём задчу по id")
    public void testFindById() {
        Optional<ToDoList> expected = Optional.of(todolist);
        doReturn(expected).when(repository).findById(id);
        service.findById(id);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("достаём все задачи")
    public void testFindAll() {
        List<ToDoList> list = new ArrayList<>();

        doReturn(list).when(repository).findAll();
        service.findAll();
        verify(repository).findAll();
    }

    @Test
    @DisplayName("сохраняем задачу")
    public void testSaveList() {
        doReturn(todolist).when(repository).save(todolist);
        service.saveList(todolist);
        verify(repository).save(todolist);
    }

    @Test
    @DisplayName("удаляем по id")
    public void testDeleteById() {
        doNothing().when(repository).deleteById(id);
        service.deleteById(id);
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("удаляем всех")
    public void testDeleteAll() {
        doNothing().when(repository).deleteAll();
        service.deleteAll();
        verify(repository).deleteAll();
    }
}