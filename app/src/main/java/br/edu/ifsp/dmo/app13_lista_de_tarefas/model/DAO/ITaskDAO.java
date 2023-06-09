package br.edu.ifsp.dmo.app13_lista_de_tarefas.model.DAO;

import android.content.Context;

import java.util.List;

import br.edu.ifsp.dmo.app13_lista_de_tarefas.model.Entities.Task;

public interface ITaskDAO {
    void create(Task task);

    boolean update(String oldTitle, Task task);

    void delete(Task task);

    void setContext (Context context);

    Task findByTitle(String title);

    List<Task> findAll();
}
