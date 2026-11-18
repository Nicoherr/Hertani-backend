package cl.duoc.wim100.config;

import cl.duoc.wim100.model.EstadoPostulante;
import cl.duoc.wim100.model.Postulante;
import cl.duoc.wim100.model.Rol;
import cl.duoc.wim100.model.Usuario;
import cl.duoc.wim100.repository.PostulanteRepository;
import cl.duoc.wim100.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PostulanteRepository postulanteRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario u1 = new Usuario();
            u1.setUsername("evaluadora1");
            u1.setPasswordHash(encoder.encode("wim2026"));
            u1.setNombre("Camila Evaluadora");
            u1.setRol(Rol.EVALUADORA);
            usuarioRepository.save(u1);

            Usuario u2 = new Usuario();
            u2.setUsername("direccion");
            u2.setPasswordHash(encoder.encode("wim2026"));
            u2.setNombre("Director General");
            u2.setRol(Rol.DIRECCION);
            usuarioRepository.save(u2);
        }

        if (postulanteRepository.count() == 0) {
            Postulante p1 = new Postulante();
            p1.setNombre("Camila Rojas Vega");
            p1.setCorreo("camila.rojas@hertani.test");
            p1.setTelefono("+56 9 1234 5678");
            p1.setArea("Ciberseguridad ofensiva (Red Team)");
            p1.setAniosExperiencia(8);
            p1.setResumenExperiencia("Pentester en consultora. Lidera ejercicios de Red Team y capacita analistas junior.");
            p1.setLinkedinUrl("https://www.linkedin.com/in/camila-rojas-demo");
            p1.setCvNombre("cv_camila.pdf");
            p1.setEstado(EstadoPostulante.POSTULADA);
            p1.setRegistradaPor("Sistema (datos semilla)");
            postulanteRepository.save(p1);

            Postulante p2 = new Postulante();
            p2.setNombre("Paula Araya Muñoz");
            p2.setCorreo("paula.araya@hertani.test");
            p2.setTelefono("+56 9 8765 4321");
            p2.setArea("Ciberseguridad defensiva (Blue Team / SOC)");
            p2.setAniosExperiencia(6);
            p2.setResumenExperiencia("Jefa de SOC. Diseñó guías de respuesta que redujeron tiempos de atención de incidentes.");
            p2.setLinkedinUrl("https://www.linkedin.com/in/paula-araya-demo");
            p2.setCvNombre("cv_paula.pdf");
            p2.setEstado(EstadoPostulante.POSTULADA);
            p2.setRegistradaPor("Sistema (datos semilla)");
            postulanteRepository.save(p2);
        }
    }
}
