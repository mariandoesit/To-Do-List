package com.example.todo.repository;

import com.example.todo.model.Task;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class TaskRepository {
    private Map<Long, Task> tareas = new HashMap<>();
    private AtomicLong contadorId = new AtomicLong(1);

    public TaskRepository() {
        tareas.put(1L, new Task(1L, "Comprar víveres", "Leche, pan, huevos"));
        tareas.put(2L, new Task(2L, "Estudiar Springboot", "Repasar controladores"));
        tareas.put(3L, new Task(3L, "Hacer ejercicio", "30 minutos de cardio"));
        contadorId.set(4);
    }

    //obtener todas
    public List<Task> findAll() {
        return new ArrayList<>(tareas.values());
    }

    //obtener por id
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tareas.get(id));
    }

    //obtener solo completadas
    public List<Task> findByCompletada(boolean completed) {
            return tareas.values().stream()
                    .filter(t -> t.isCompleted() == completed)
                    .collect(Collectors.toList());
    }

    //crear
    public Task save(Task tarea) {
        if (tarea.getId() == null) {
            tarea.setId(contadorId.getAndIncrement());
        }
        tareas.put(tarea.getId(), tarea);
        return tarea;
    }

    //actualizar
    public Task update(Long id, Task tarea) {
        if (!tareas.containsKey(id)) {
            return null;
        }
        tarea.setId(id);
        tareas.put(id, tarea);
        return tarea;
    }

    //eliminar
    public boolean deleteById(Long id) {
        return tareas.remove(id) != null;
    }

    //verificar si existe
    public boolean existsById(Long id) {
        return tareas.containsKey(id);
    }
}
