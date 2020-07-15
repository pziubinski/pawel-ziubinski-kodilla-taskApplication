package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {
    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void shouldGetAllTasksTest() {
        //GIVEN
        Task task1 = new Task(1L, "test1", "test1");
        Task task2 = new Task(2L, "test2", "test2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        //WHEN
        List<Task> getTasks = dbService.getAllTasks();

        //THEN
        assertEquals(2, getTasks.size());
        assertEquals("test2", getTasks.get(1).getTitle());
    }

    @Test
    public void shouldSaveTaskTest() {
        //GIVEN
        Task task = new Task(1L, "test", "test");

        when(taskRepository.save(task)).thenReturn(task);

        //WHEN
        Task savedTask = dbService.saveTask(task);

        //THEN
        assertEquals("test", savedTask.getTitle());
        assertEquals("test", savedTask.getContent());
    }

    @Test
    public void shouldGetTaskTest() {
        //GIVEN
        Optional<Task> task = Optional.of(new Task(10L, "test", "test"));

        when(taskRepository.findById(10L)).thenReturn(task);

        //WHEN
        Optional<Task> optionalTask = dbService.getTask(10L);

        //THEN
        assertEquals(true, optionalTask.isPresent());
        optionalTask.ifPresent(task1 -> assertEquals("test", task1.getTitle()));
    }

    @Test
    public void shouldDeleteTaskTest() {
        //GIVEN
        Task task1 = new Task(1L, "test1", "test1");
        Task task2 = new Task(2L, "test2", "test2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        when(taskRepository.save(task1)).thenReturn(task1);
        dbService.saveTask(task1);

        //when(taskRepository.deleteById(1L)).then(dbService.getTask(1L));

        //WHEN
        List<Task> taskList = dbService.getAllTasks();

        //THEN
        assertEquals(0, taskList.size());

    }

}
