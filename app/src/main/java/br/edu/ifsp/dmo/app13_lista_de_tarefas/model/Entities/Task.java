package br.edu.ifsp.dmo.app13_lista_de_tarefas.model.Entities;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Task implements Comparable<Task>{
    private String title;
    private String description;
    private String creationDate;
    private boolean important;

    public Task(String title, String description){
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        this.title = title;
        this.description = description;
        this.creationDate = localDate.format(formatter);
    }

    public Task(String title, String description, boolean important) {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        this.title = title;
        this.description = description;
        this.creationDate = localDate.format(formatter);
        this.important = important;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public Boolean isImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: " + title;
    }

    @Override
    public int compareTo(Task task) {
        //return this.isImportant().compareTo(task.isImportant());
        return Comparator.comparing(Task::isImportant).reversed().thenComparing(Task::getTitle).compare(this, task);
        //return this.title.compareTo(task.title);

    }


}
