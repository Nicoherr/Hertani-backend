package cl.duoc.wim100.model;

import jakarta.persistence.*;

@Entity
@Table(name = "postulantes")
public class Postulante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private Integer aniosExperiencia;

    @Column(nullable = false, length = 1000)
    private String resumenExperiencia;

    @Column(nullable = false)
    private String linkedinUrl;

    private String cvNombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPostulante estado = EstadoPostulante.POSTULADA;

    @Column(nullable = false)
    private Long fechaRegistro = System.currentTimeMillis();

    @Column(nullable = false)
    private String registradaPor;

    public Postulante() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public Integer getAniosExperiencia() { return aniosExperiencia; }
    public void setAniosExperiencia(Integer aniosExperiencia) { this.aniosExperiencia = aniosExperiencia; }

    public String getResumenExperiencia() { return resumenExperiencia; }
    public void setResumenExperiencia(String resumenExperiencia) { this.resumenExperiencia = resumenExperiencia; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public String getCvNombre() { return cvNombre; }
    public void setCvNombre(String cvNombre) { this.cvNombre = cvNombre; }

    public EstadoPostulante getEstado() { return estado; }
    public void setEstado(EstadoPostulante estado) { this.estado = estado; }

    public Long getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Long fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getRegistradaPor() { return registradaPor; }
    public void setRegistradaPor(String registradaPor) { this.registradaPor = registradaPor; }
}
