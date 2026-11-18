package cl.duoc.wim100.dto;

import cl.duoc.wim100.model.Rol;

public class LoginResponse {
    private Long id;
    private String username;
    private String nombre;
    private Rol rol;

    public LoginResponse(Long id, String username, String nombre, Rol rol) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getNombre() { return nombre; }
    public Rol getRol() { return rol; }
}
