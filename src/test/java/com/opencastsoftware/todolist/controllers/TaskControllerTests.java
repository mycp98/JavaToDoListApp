package com.opencastsoftware.todolist.controllers;

import com.opencastsoftware.todolist.models.Importance;
import com.opencastsoftware.todolist.models.Task;
import com.opencastsoftware.todolist.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class TaskControllerTests {

    private TaskController taskController;

    private TaskService mockTaskService;

    @BeforeEach
    public void setUp() {
        mockTaskService= mock(TaskService.class);
        taskController = new TaskController(mockTaskService);
    }

    @Test
    public void getTaskById_mustReturnOkWithCorrectTaskObject() {
        Task fakeTask = new Task(
                1, "laundry", 30, LocalDateTime.now(), "persil", Importance.MEDIUM
        );
        ResponseEntity<Task> expectedResponse = new ResponseEntity<>(fakeTask, HttpStatus.OK);

        when(mockTaskService.getTaskById(fakeTask.getId())).thenReturn(Optional.of(fakeTask));

        ResponseEntity<Task> actualResponse = taskController.getTaskById(1);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getTaskById_mustReturnNotFound_whenTheServiceReturnsAnEmptyOptional(){

        ResponseEntity<Task> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        when(mockTaskService.getTaskById(6)).thenReturn(Optional.empty());

        ResponseEntity<Task> actualResponse = taskController.getTaskById(6);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void addTask_mustReturnOkWithNewTaskAdded() {
        Task fakeTask = new Task("laundry", 30, LocalDateTime.now(), "persil", Importance.MEDIUM
        );
        ResponseEntity<Task> expectedResponse = new ResponseEntity<>(fakeTask, HttpStatus.OK);

        when(mockTaskService.addTask(
                fakeTask.getName(),
                fakeTask.getDuration(),
                fakeTask.getDueDate(),
                fakeTask.getDescription(),
                fakeTask.getImportance())
        ).thenReturn(fakeTask);

        ResponseEntity<Task> actualResponse = taskController.addTask(fakeTask);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllTasks_mustReturnOkAndAllTasksInDb() {
        Task fakeTask1 = new Task("laundry", 30, LocalDateTime.now(), "persil", Importance.MEDIUM
        );
        Task fakeTask2 = new Task("shopping", 45, LocalDateTime.now(), "sainsburys", Importance.LOW
        );

        List<Task> expectedTaskList = new ArrayList<>();
        expectedTaskList.add(fakeTask1);
        expectedTaskList.add(fakeTask2);

        when(mockTaskService.getAllTasks()).thenReturn(expectedTaskList);

        List<Task> actualTaskList = taskController.getAllTasks();

        assertEquals(expectedTaskList, actualTaskList);

    }

    @Test
    public void deleteTaskById_mustReturnOkAndDeleteCorrectTaskObject() {
        Task fakeTask = new Task(
                1, "laundry", 30, LocalDateTime.now(), "persil", Importance.MEDIUM
        );

        when(mockTaskService.getTaskById(fakeTask.getId())).thenReturn(Optional.of(fakeTask));

        ResponseEntity expectedResponse = new ResponseEntity<>(HttpStatus.OK);

        ResponseEntity actualResponse = taskController.deleteTaskById(fakeTask.getId());

        verify(mockTaskService, times(1)).deleteTask(1);

        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    public void updateTask_mustUpdateTaskSuccessfully(){
        Integer id = 1;
//        Task fakeTask1 = new Task(id,"laundry", 30, LocalDateTime.now(), "persil", Importance.MEDIUM
//        );
        Task fakeTaskUpdated = new Task(id, "shopping", 30, LocalDateTime.now(), "sainsburys", Importance.MEDIUM
        );

        ResponseEntity<Task> expectedResponse = new ResponseEntity<>(fakeTaskUpdated, HttpStatus.OK);

        when(mockTaskService.updateTask(
                fakeTaskUpdated.getId(),
                fakeTaskUpdated.getName(),
                fakeTaskUpdated.getDuration(),
                fakeTaskUpdated.getDueDate(),
                fakeTaskUpdated.getDescription(),
                fakeTaskUpdated.getImportance())
        ).thenReturn(Optional.of(fakeTaskUpdated));

        ResponseEntity<Task> actualResponse = taskController.updateTask(fakeTaskUpdated);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void updateTask_mustReturnNotFound_whenTheServiceReturnsAnEmptyOptional() {
        Task fakeTaskUpdated = new Task(99, "shopping", 30, LocalDateTime.now(), "sainsburys", Importance.MEDIUM
        );
        ResponseEntity<Task> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        when(mockTaskService.updateTask(
                99,
                "cleaning",
                30,
                LocalDateTime.now(),
                "something",
                Importance.HIGH
        )).thenReturn(Optional.empty());

        ResponseEntity<Task> actualResponse = taskController.updateTask(fakeTaskUpdated);

        assertEquals(expectedResponse, actualResponse);

    }
}
