package cl.duoc.wim100.service;

import cl.duoc.wim100.exception.DuplicateEmailException;
import cl.duoc.wim100.exception.ResourceNotFoundException;
import cl.duoc.wim100.model.EstadoPostulante;
import cl.duoc.wim100.model.Postulante;
import cl.duoc.wim100.repository.PostulanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostulanteService {

    @Autowired
    private PostulanteRepository postulanteRepository;

    public List<Postulante> listarTodos() {
        return postulanteRepository.findAll();
    }

    public Postulante obtenerPorId(Long id) {
        return postulanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Postulante no encontrado con id: " + id));
    }

    public Postulante registrar(Postulante postulante) {
        postulanteRepository.findByCorreo(postulante.getCorreo().trim().toLowerCase())
                .ifPresent(p -> {
                    throw new DuplicateEmailException("Ya existe una candidatura con este correo");
                });
        postulante.setId(null);
        if (postulante.getEstado() == null) {
            postulante.setEstado(EstadoPostulante.POSTULADA);
        }
        if (postulante.getFechaRegistro() == null) {
            postulante.setFechaRegistro(System.currentTimeMillis());
        }
        return postulanteRepository.save(postulante);
    }

    public Postulante actualizar(Long id, Postulante datosNuevos) {
        Postulante existente = obtenerPorId(id);

        postulanteRepository.findByCorreo(datosNuevos.getCorreo().trim().toLowerCase())
                .filter(p -> !p.getId().equals(id))
                .ifPresent(p -> {
                    throw new DuplicateEmailException("Ya existe otra candidatura con este correo");
                });

        existente.setNombre(datosNuevos.getNombre());
        existente.setCorreo(datosNuevos.getCorreo());
        existente.setTelefono(datosNuevos.getTelefono());
        existente.setArea(datosNuevos.getArea());
        existente.setAniosExperiencia(datosNuevos.getAniosExperiencia());
        existente.setResumenExperiencia(datosNuevos.getResumenExperiencia());
        existente.setLinkedinUrl(datosNuevos.getLinkedinUrl());
        if (datosNuevos.getCvNombre() != null) {
            existente.setCvNombre(datosNuevos.getCvNombre());
        }

        return postulanteRepository.save(existente);
    }

    public Postulante cambiarEstado(Long id, EstadoPostulante nuevoEstado) {
        Postulante postulante = obtenerPorId(id);
        postulante.setEstado(nuevoEstado);
        return postulanteRepository.save(postulante);
    }

    public void eliminar(Long id) {
        Postulante postulante = obtenerPorId(id);
        postulanteRepository.delete(postulante);
    }
}
