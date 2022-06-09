package com.opencastsoftware.todolist.repositories;

import com.opencastsoftware.todolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}

// Created a Task repository that stores Task objects that are identified by an Integer
// 1) Connects to database with columns and table defined in the Task model
// 2) Can use the JPA repos methods to perform crud operations and selects on the database
//
// Created a Task model that holds the properties of a task and defines the tables and data
// 1) Defines the properties of the Task that we want to save and use
// 2) Uses annotations to define database columns and tables
//
// Created a Task service that contained a method that gets a task by its ID from the Task repository
// 1) Created an instance of the task repo and injected it into the service
// 2) Use a method from the task repo called findById()
// 3) That method returns a Task inside an Optional type
//
// Created a Task controller that contained a method that got a task by its ID from the Task service
// 1) Create an instance of the task service and injected it into the controller
// 2) Create a GetMapping for a GET request to our method that returns a ResponseEntity
// 3) Created a HashMap that caches the Tasks when they are retrieved from the database the first time
// 4) We check if we have the Task already in the Map
// 5) If not, we call the task service's getTaskById method that returns an Optional Task
// 6) If there is a task present, then we add the task to the HashMap with the task id as the Key and Task object as the value
// 7) We map over the optional and if its present then we make a new ResponseEntity with that task in it and the OK (200) HTTP code
// 8) Otherwise we create a new ResponseEntity with a NOT_FOUND (404) status code
//
// Next time the method is called with the same ID, the HashMap already contains the value, so we return a ResponseEntity with that in it
//
