package br.edu.ifsp.dmo.app13_lista_de_tarefas.model.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.ifsp.dmo.app13_lista_de_tarefas.model.Entities.Task;
import br.edu.ifsp.dmo.app13_lista_de_tarefas.utils.Constant;

public class TaskDAOSingleton implements ITaskDAO{
    private static TaskDAOSingleton instance = null;
    private Context context;
    private List<Task> dataset;

    private TaskDAOSingleton() {
        dataset = new ArrayList<>();
    }

    public static TaskDAOSingleton getInstance(){
        if(instance == null)
            instance = new TaskDAOSingleton();
        return instance;
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public void create(Task task) {
        if(task != null){
            dataset.add(task);
            Collections.sort(dataset);
            writeDataset();
            readDatabase();
        }
    }

    @Override
    public boolean update(String oldTitle, Task task) {
        Task inDataset;
        inDataset = dataset.stream()
                .filter(task1 -> task1.getTitle().equals(oldTitle))
                .findAny()
                .orElse(null);
        if(inDataset != null){
            inDataset.setTitle(task.getTitle());
            inDataset.setDescription(task.getDescription());
            inDataset.setImportant(task.isImportant());
            Collections.sort(dataset);
            writeDataset();
            readDatabase();
            return true;
        }
        return false;
    }

    @Override
    public void delete(Task task) {
        dataset.remove(task);
        writeDataset();
        readDatabase();
    }

    @Override
    public Task findByTitle(String title) {
        return dataset.stream()
                .filter(task -> task.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    private void writeDataset(){
        SharedPreferences preferences;
        SharedPreferences.Editor editor;

        JSONObject jsonObject;
        JSONArray jsonArray;

        jsonArray = new JSONArray();
        for(Task t : dataset){
            jsonObject = new JSONObject();
            try{
                jsonObject.put(Constant.ATTR_TITLE, t.getTitle());
                jsonObject.put(Constant.ATTR_DESC, t.getDescription());
                jsonObject.put(Constant.ATTR_DATE, t.getCreationDate());
                jsonObject.put(Constant.ATTR_IMPORTANT, t.isImportant());
                jsonArray.put(jsonObject);
            }catch (JSONException e){
                Log.e("teste", e.getMessage());
            }
        }
        Log.e("teste", jsonArray.toString());
        preferences = context.getSharedPreferences(Constant.DATABASE_FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(Constant.TABLE_NAME, jsonArray.toString());
        editor.commit();

    }

    private void readDatabase(){
        SharedPreferences preferences;
        String json;
        Task task;
        JSONObject jsonObject;
        JSONArray jsonArray;

        preferences = context.getSharedPreferences(Constant.DATABASE_FILE_NAME, Context.MODE_PRIVATE);
        json = preferences.getString(Constant.TABLE_NAME, "");

        if(!json.isEmpty()){
            dataset.clear();
            try{
                jsonArray = new JSONArray(json);
                for(int i=0; i < jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    task = new Task(jsonObject.getString(Constant.ATTR_TITLE), jsonObject.getString(Constant.ATTR_DESC), jsonObject.getBoolean(Constant.ATTR_IMPORTANT));
                    task.setCreationDate(jsonObject.getString(Constant.ATTR_DATE));
                    dataset.add(task);
                }
            }catch (JSONException e){
                Log.e("TaskDAOJson", e.getMessage());
            }
        }else{
            Log.v("TaskDAOJson", "Sem dados para recuperar do JSON");
        }
    }

    @Override
    public List<Task> findAll() {
        readDatabase();
        return dataset;
    }
}
