package ma.ens.security.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/user/profile")
    public String userProfile() {
        return "Bienvenue sur votre profil (Accessible par USER et ADMIN)";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "Accès au Panneau d'Administration (Accessible par ADMIN uniquement)";
    }

    @GetMapping("/public/info")
    public String publicInfo() {
        return "Ceci est une information publique (Accessible sans token)";
    }
}