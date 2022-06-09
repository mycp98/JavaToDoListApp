package com.opencastsoftware.todolist.controllers;

import com.opencastsoftware.todolist.models.Task;
import com.opencastsoftware.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TaskController {

    private final TaskService taskService;

    private final Map<Integer, Task> taskMap = new HashMap<>();

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/get-task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        if(taskMap.containsKey(id)) {
            return new ResponseEntity<>(taskMap.get(id), HttpStatus.OK);
        } else {
            Optional<Task> taskById = taskService.getTaskById(id);
            taskById.ifPresent(task -> taskMap.put(task.getId(), task));

            return taskById
                    .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping("/get-all-tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/add-task")
    public ResponseEntity<Task> addTask(@RequestBody Task task){
        Task taskToAdd = taskService.addTask(
                task.getName(),
                task.getDuration(),
                task.getDueDate(),
                task.getDescription(),
                task.getImportance()
        );
        return new ResponseEntity<>(taskToAdd, HttpStatus.OK);
    }

    @PutMapping("/update-task")
    public ResponseEntity<Task> updateTask(@RequestBody Task taskToUpdate) {
        //call task service Update method with the properties of the taskToUpdate
        //handle the optional in the same way as getTaskById - use as a guide
        //lines 35 to 37

        //test for updated successfully
        //test for when couldnt find existing task (optional empty)
        return null;
    }

    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity deleteTaskById(@PathVariable int id) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if (optionalTask.isPresent()) {
            taskService.deleteTask(id);
            taskMap.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        }

//    @DeleteMapping("/delete-task/{id}")
//    public void deleteTaskById(@PathVariable int id) {
//        taskService.deleteTask(id);
//    }



    /*
    {
        "name": "Laundry",
        "duration": 30,
        "dueDate": 2022-05-25:13:00
        "description: "Whatever"
        "importance": LOW
    }
     */
}
