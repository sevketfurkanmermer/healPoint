package com.proje.healpoint.config;

import com.proje.healpoint.model.Admin;
import com.proje.healpoint.model.SubscriptionPlan;
import com.proje.healpoint.repository.AdminRepository;
import com.proje.healpoint.repository.SubscriptionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public void run(String... args) throws Exception {
        loadDefaultAdmins();
        loadDefaultSubscriptionPlans();
    }

    private void loadDefaultAdmins() {
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

    private void loadDefaultSubscriptionPlans() {
        List<SubscriptionPlan> defaultPlans = List.of(
                new SubscriptionPlan("Aylık", BigDecimal.valueOf(500), 1),
                new SubscriptionPlan("3 Aylık", BigDecimal.valueOf(1300), 3),
                new SubscriptionPlan("6 Aylık", BigDecimal.valueOf(2500), 6),
                new SubscriptionPlan("Yıllık", BigDecimal.valueOf(5000), 12)
        );

        for (SubscriptionPlan plan : defaultPlans) {
            if (!subscriptionPlanRepository.existsByName(plan.getName())) {
                subscriptionPlanRepository.save(plan);
            }
        }
    }
}
