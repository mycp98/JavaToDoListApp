package com.opencastsoftware.todolist.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "task_name", nullable = false)
    private String name;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "importance", nullable = false)
    private Importance importance;

    public Task(String name, Integer duration, LocalDateTime dueDate, String description, Importance importance) {
        this.name = name;
        this.duration = duration;
        this.dueDate = dueDate;
        this.description = description;
        this.importance = importance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return name.equals(task.name)
                && duration.equals(task.duration)
                && dueDate.equals(task.dueDate)
                && description.equals(task.description)
                && importance == task.importance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, dueDate, description, importance);
    }
}
