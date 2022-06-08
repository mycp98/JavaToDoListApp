package com.opencastsoftware.todolist.services;

import com.opencastsoftware.todolist.models.Importance;
import com.opencastsoftware.todolist.models.Task;
import com.opencastsoftware.todolist.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTests {

    private TaskService taskService;

    private TaskRepository mockTaskRepository;

    @BeforeEach
    public void setUp() {
        mockTaskRepository = mock(TaskRepository.class);
        taskService = new TaskService(mockTaskRepository);
    }

    @Test
    public void getTaskById_mustReturnCorrectTask(){
        Task fakeTask = new Task(
                1, "laundry", 30, LocalDateTime.now(), "persil", Importance.MEDIUM
        );
        when(mockTaskRepository.findById(fakeTask.getId())).thenReturn(Optional.of(fakeTask));

        Optional<Task> actual = taskService.getTaskById(fakeTask.getId());
        assertEquals(Optional.of(fakeTask), actual);
    }

    @Test
    public void getTaskById_mustReturnAnOptionalEmpty_whenIdDoesntExist(){

        when(mockTaskRepository.findById(6)).thenReturn(Optional.empty());

        Optional<Task> actual = taskService.getTaskById(6);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void addTask_mustSaveTheTaskAndReturnTaskThatWasSaved(){
        Task fakeTask = new Task(
                1, "laundry", 30, LocalDateTime.now(), "persil", Importance.MEDIUM
        );
        when(mockTaskRepository.saveAndFlush(any())).thenReturn(fakeTask);

        Task actual = taskService.addTask(
                "laundry",
                30,
                LocalDateTime.now(),
                "persil",
                Importance.MEDIUM
        );
        assertEquals(fakeTask.getName(), actual.getName());
        assertEquals(fakeTask.getDuration(), actual.getDuration());
        assertEquals(fakeTask.getDueDate(), actual.getDueDate());
        assertEquals(fakeTask.getDescription(), actual.getDescription());
        assertEquals(fakeTask.getImportance(), actual.getImportance());
    }

    @Test
    public void getAllTasks_mustReturnListOfAllTasks() {
        Task fakeTask1 = new Task(1,"laundry", 30, LocalDateTime.now(), "persil", Importance.MEDIUM
        );
        Task fakeTask2 = new Task(2, "shopping", 45, LocalDateTime.now(), "sainsburys", Importance.LOW
        );

        List<Task> expectedTaskList = new ArrayList<>();
        expectedTaskList.add(fakeTask1);
        expectedTaskList.add(fakeTask2);

        when(mockTaskRepository.findAll()).thenReturn(expectedTaskList);

        List<Task> actualTaskList = taskService.getAllTasks();

        assertEquals(expectedTaskList, actualTaskList);
    }

    @Test
    public void deleteTask() {
        taskService.deleteTask(1);
        verify(mockTaskRepository, times(1)).deleteById(1);
    }
}
