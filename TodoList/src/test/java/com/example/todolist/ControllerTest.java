package com.example.todolist;

import com.example.todolist.config.ControllerTestConfig;
import com.example.todolist.controller.ToDoListController;
import com.example.todolist.entity.ToDoList;
import com.example.todolist.service.ToDoListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ControllerTestConfig.class)
@WebAppConfiguration
@DisplayName("тестируем контроллер")
public class ControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ToDoListServiceImpl service;

    @Autowired
    private ToDoListController controller;
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @DisplayName("получаем начальную страницу и лист задач")
    public void testGetList() throws Exception {
        ToDoList list1 = new ToDoList();
        list1.setId(1);
        list1.setName("1");

        ToDoList list2 = new ToDoList();
        list2.setId(2);
        list2.setName("2");

        when(service.findAll()).thenReturn(Arrays.asList(list1, list2));

        mvc.perform(get("/", "/todolist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("lists", hasSize(2)))
                .andExpect(model().attribute("lists", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is("1"))
                        )
                )))
                .andExpect(model().attribute("lists", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is("2"))
                        )
                )));

        verify(service, times(1)).findAll();
    }

    @Test
    @DisplayName("обновляем задачу по id")
    public void testUpdateList() throws Exception {
        int id = 1;

        ToDoList toDoList = new ToDoList("firstList");
        toDoList.setId(id);

        when(service.findById(id)).thenReturn(toDoList);

        mvc.perform(get("/todolist-update/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("list", toDoList))
                .andExpect(view().name("/todolist-update"));
    }

    @Test
    @DisplayName("обновляем новую задачу")
    public void testPostUpdateList() throws Exception {
        ToDoList list = new ToDoList();
        when(service.saveList(list)).thenReturn(list);
        mvc.perform(post("/todolist-update"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/todolist"));
    }

    @Test
    @DisplayName("тестируем переход на страницу создания задачи")
    public void testCreateListForm() throws Exception {
        mvc.perform(get("/todolist-create/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("todolist-create"));
    }

    @Test
    @DisplayName("сохраняем новую задачу")
    public void testCreateList() throws Exception {
        ToDoList list = new ToDoList();
        when(service.saveList(list)).thenReturn(list);
        mvc.perform(post("/todolist-create"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/todolist"));
    }

    @Test
    @DisplayName("удаляем задачу")
    public void testDeleteList() throws Exception {
        int id = 1;
        mvc.perform(get("/todolist-delete/{id}", id))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/todolist"));
        verify(service).deleteById(id);
    }
}