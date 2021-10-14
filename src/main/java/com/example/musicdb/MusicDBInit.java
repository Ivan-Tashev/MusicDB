package com.example.musicdb;

import com.example.musicdb.model.entity.UserEntity;
import com.example.musicdb.model.entity.UserRoleEntity;
import com.example.musicdb.model.entity.enums.UserRole;
import com.example.musicdb.repo.UserRepo;
import com.example.musicdb.repo.UserRoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MusicDBInit implements CommandLineRunner {
    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    private final PasswordEncoder passwordEncoder;

    public MusicDBInit(UserRepo userRepo, UserRoleRepo userRoleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() == 0) {
        UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);
        UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
        userRoleRepo.saveAll(List.of(adminRole, userRole));

        UserEntity user = new UserEntity()
                .setUsername("user").setPassword(passwordEncoder.encode("123"))
                .setFullName("User Userov").setEmail("a@b.c").setRoles(List.of(userRole));
        UserEntity admin = new UserEntity()
                .setUsername("admin").setPassword(passwordEncoder.encode("123"))
                .setFullName("Admin Adminov").setEmail("a@b.c").setRoles(List.of(userRole, adminRole));
        userRepo.saveAll(List.of(user, admin));

        }
    }
}
