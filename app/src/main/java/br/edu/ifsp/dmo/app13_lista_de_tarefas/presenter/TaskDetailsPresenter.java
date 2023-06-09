package br.edu.ifsp.dmo.app13_lista_de_tarefas.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import br.edu.ifsp.dmo.app13_lista_de_tarefas.model.DAO.ITaskDAO;
import br.edu.ifsp.dmo.app13_lista_de_tarefas.model.DAO.TaskDAOSingleton;
import br.edu.ifsp.dmo.app13_lista_de_tarefas.model.Entities.Task;
import br.edu.ifsp.dmo.app13_lista_de_tarefas.mvp.TaskDetailsMVP;
import br.edu.ifsp.dmo.app13_lista_de_tarefas.utils.Constant;

public class TaskDetailsPresenter  implements TaskDetailsMVP.Presenter{
    private TaskDetailsMVP.View view;
    private Task task;
    private Context context;
    private ITaskDAO dao;

    public TaskDetailsPresenter(TaskDetailsMVP.View view) {
        this.view = view;
        task = null;
        dao = TaskDAOSingleton.getInstance();
        context = view.getContext();
        dao.setContext(context);
    }

    @Override
    public void deatach() {
        this.view = null;
    }

    @Override
    public void verifyUpdate() {
        String title;
        Bundle bundle = view.getBundle();
        if(bundle != null){
            title = bundle.getString(Constant.ATTR_TITLE);
            task = dao.findByTitle(title);
            view.updateUI(task.getTitle(), task.getDescription(), task.getCreationDate());
        }
    }

    @Override
    public void saveTask(String title, String description) {
        if (task == null) {
            //Nova tarefa
            task = new Task(title, description);
            dao.create(task);
            view.showToast("Nova tarefa adicionada com sucesso.");
            view.close();
        } else {
            //Atualizar tarefa existente
            String oldTitle = task.getTitle();
            Task newTask = new Task(title, description, task.isImportant());
            if (dao.update(oldTitle, newTask)) {
                view.showToast("Tarefa atualizada com sucesso.");
                view.close();
            } else {
                view.showToast("Erro ao atualizar a tarefa.");
            }
        }

    }



}
