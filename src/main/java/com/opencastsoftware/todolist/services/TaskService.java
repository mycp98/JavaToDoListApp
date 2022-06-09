package com.opencastsoftware.todolist.services;

import com.opencastsoftware.todolist.models.Importance;
import com.opencastsoftware.todolist.models.Task;
import com.opencastsoftware.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Optional<Task> getTaskById(Integer id){
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(
            String name, Integer duration, LocalDateTime dueDate, String description, Importance importance) {
        Task taskToSave = new Task(name, duration, dueDate, description, importance);

        return taskRepository.saveAndFlush(taskToSave);

    }

    // delete task
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    //update task
    public Optional<Task> updateTask(Integer existingTaskId, String name, Integer duration, LocalDateTime dueDate, String description, Importance importance){

        Optional<Task> optionalExistingTask = taskRepository.findById(existingTaskId);
        if(optionalExistingTask.isPresent()) {
            Task existingTask = optionalExistingTask.get();
            existingTask.setName(name);
            existingTask.setDuration(duration);
            existingTask.setDueDate(dueDate);
            existingTask.setDescription(description);
            existingTask.setImportance(importance);

            taskRepository.save(existingTask);
            return Optional.of(existingTask);
        }

        return Optional.empty();
    };
}
