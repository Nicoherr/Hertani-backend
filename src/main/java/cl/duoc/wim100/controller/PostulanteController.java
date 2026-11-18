package cl.duoc.wim100.controller;

import cl.duoc.wim100.dto.EstadoRequest;
import cl.duoc.wim100.model.Postulante;
import cl.duoc.wim100.service.PostulanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postulantes")
@CrossOrigin(origins = "*")
public class PostulanteController {

    @Autowired
    private PostulanteService postulanteService;

    @GetMapping
    public ResponseEntity<List<Postulante>> listar() {
        return ResponseEntity.ok(postulanteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postulante> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(postulanteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Postulante> registrar(@Valid @RequestBody Postulante postulante) {
        Postulante nuevo = postulanteService.registrar(postulante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postulante> editar(@PathVariable Long id, @Valid @RequestBody Postulante postulante) {
        Postulante actualizado = postulanteService.actualizar(id, postulante);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Postulante> cambiarEstado(@PathVariable Long id, @Valid @RequestBody EstadoRequest request) {
        Postulante actualizado = postulanteService.cambiarEstado(id, request.getEstado());
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        postulanteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
