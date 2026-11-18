package cl.duoc.wim100.dto;

import cl.duoc.wim100.model.EstadoPostulante;
import jakarta.validation.constraints.NotNull;

public class EstadoRequest {
    @NotNull
    private EstadoPostulante estado;

    public EstadoRequest() {}

    public EstadoPostulante getEstado() { return estado; }
    public void setEstado(EstadoPostulante estado) { this.estado = estado; }
}
