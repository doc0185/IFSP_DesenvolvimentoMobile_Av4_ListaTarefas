package br.edu.ifsp.dmo.app13_lista_de_tarefas.model.Entities;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private String title;
    private String description;
    private String creationDate;
    private Boolean important;
    private List<Tag> tags;

    private void init(){
        tags = new ArrayList<>();
    }

    public Task(String title, String description, String creationDate){
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        init();
    }

    public Task(String title, String description, String creationDate, boolean important) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.important = important;
        init();
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

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public boolean removeTag(Tag tag){
        return this.tags.remove(tag);
    }

    public List<Tag> getTags() {
        return tags;
    }


}
