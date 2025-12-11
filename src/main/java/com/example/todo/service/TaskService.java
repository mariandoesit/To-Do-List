package com.example.todo.service;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repositorio;

    public List<Task> obtenerTodas()
    {
        return repositorio.findAll();
    }

    public List<Task> obtenerPorEstado(boolean completed) {
        return repositorio.findByCompletada(completed);
    }

    public Optional<Task> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public Task crear(Task tarea) {
        return repositorio.save(tarea);
    }

    public Task actualizar(Long id, Task tarea) {
        return repositorio.update(id, tarea);
    }

    public Task marcarCompletada(Long id) {
        Optional<Task> tareaOpt = repositorio.findById(id);
        if (tareaOpt.isPresent()) {
            Task tarea = tareaOpt.get();
            tarea.setCompleted(true);
            return repositorio.save(tarea);
        }
        return null;
    }

    public Task marcarPendiente(Long id) {
        Optional<Task> tareaOpt = repositorio.findById(id);
        if (tareaOpt.isPresent()) {
            Task tarea = tareaOpt.get();
            tarea.setCompleted(false);
            tarea.setDateCompleted(null);
            return repositorio.save(tarea);
        }
        return null;
    }

    public boolean eliminar (Long id) {
        return repositorio.deleteById(id);
    }

    public boolean existe(Long id) {
        return repositorio.existsById(id);
    }
}
