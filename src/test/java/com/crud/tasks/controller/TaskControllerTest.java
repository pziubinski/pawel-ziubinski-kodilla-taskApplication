package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetEmptyTasks() throws Exception {
        //GIVEN
        List<TaskDto> tasks = new ArrayList<>();

        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(tasks);

        //WHEN & THEN
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouleGetTasks() throws Exception {
        //GIVEN
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(1L, "Test title", "Test content"));

        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(tasks);

        //WHEN & THEN
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                //Task fields
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test title")))
                .andExpect(jsonPath("$[0].content", is("Test content")));
    }

    @Test
    public void shouldGetTask() throws Exception {
        //GIVEN
        TaskDto taskDto = new TaskDto(1L, "Dto title", "Dto content");
        Task task = new Task(1L, "Task title", "Task content");

        when(service.getTask(task.getId())).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //WHEN & THEN
        mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                //Task fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Dto title")))
                .andExpect(jsonPath("$.content", is("Dto content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //GIVEN
        Task task = new Task(1L, "Title", "Content");
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");

        //WHEN
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //THEN
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //GIVEN
        TaskDto taskDto = new TaskDto(1L,"Task1","Task content");
        TaskDto updatedTaskDto = new TaskDto(1L,"Task1", "Updated task content");

        when(taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(any(TaskDto.class))))).thenReturn(updatedTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //WHEN & THEN
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task1")))
                .andExpect(jsonPath("$.content", is("Updated task content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //GIVEN
        Task task = new Task(1L, "Title", "Content");

        //WHEN
        when(service.getTask(task.getId())).thenReturn(Optional.of(task));

        //THEN
        mockMvc.perform(delete("/v1/tasks/1")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/v1/tasks/1")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
