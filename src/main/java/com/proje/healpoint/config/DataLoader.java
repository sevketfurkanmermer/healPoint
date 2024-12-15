package com.proje.healpoint.config;

import com.proje.healpoint.model.Admin;
import com.proje.healpoint.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Admin> defaultAdmins = List.of(
                new Admin("admin", "password"),
                new Admin("superadmin", "superpass"),
                new Admin("manager", "managerpass")
        );

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for (Admin admin : defaultAdmins) {
            if (adminRepository.findByUsername(admin.getUsername()).isEmpty()) {
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
                adminRepository.save(admin);
            }
        }
    }
}
