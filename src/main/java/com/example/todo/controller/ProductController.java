package com.example.todo.controller;

import com.example.todo.model.Task;
import com.example.todo.service.TaskService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*") //permite peticiones desde cualquier origen
public class ProductController {

    @Autowired
    private TaskService servicio;

    //metodo GET - lista todos
    @GetMapping
    public ResponseEntity<List<Task>> listarTodas() {
        List<Task> tareas = servicio.obtenerTodas();
        return ResponseEntity.ok(tareas);
    }

    //metodo GET - Listar completadas
    @GetMapping("/completadas")
    public ResponseEntity<List<Task>> listarCompletadas() {
        List<Task> tareas = servicio.obtenerPorEstado(true);
        return ResponseEntity.ok(tareas);
    }

    //Get - Listar pendientes
    @GetMapping("/pendientes")
    public ResponseEntity<List<Task>> listarPendientes() {
        List<Task> tareas = servicio.obtenerPorEstado(false);
        return ResponseEntity.ok(tareas);
    }

    //metodo GET - obtener uno por id
    @GetMapping("/{id}")
    public ResponseEntity<Task> obtenerPorId(@PathVariable Long id) {
        return servicio.obtenerPorId(id)
                .map(tarea -> ResponseEntity.ok(tarea))
                .orElse(ResponseEntity.notFound().build());
    }

    //metodo POST - crear nueva
    @PostMapping
    public ResponseEntity<Task> crear(@RequestBody Task tarea) {
        Task nuevaTarea = servicio.crear(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarea);
    }

    //metodo PUT - actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Task> actualizar(@PathVariable Long id, @RequestBody Task tarea) {
        if (!servicio.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        Task actualizada = servicio.actualizar(id, tarea);
        return ResponseEntity.ok(actualizada);
    }

    //Patch - marcar como completada
    @PatchMapping("/{id}/completar")
    public ResponseEntity<Task> marcarCompletada(@PathVariable Long id) {
        Task tarea = servicio.marcarCompletada(id);
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tarea);
    }

    //Patch - marcar como pendiente
    @PatchMapping("/{id}/pendiente")
    public ResponseEntity<Task> marcarPendiente(@PathVariable Long id) {
        Task tarea = servicio.marcarPendiente(id);
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tarea);
    }

    //Delete - eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!servicio.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
