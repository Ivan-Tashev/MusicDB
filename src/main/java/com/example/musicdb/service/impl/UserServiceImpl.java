package com.example.musicdb.service.impl;

import com.example.musicdb.model.entity.UserEntity;
import com.example.musicdb.model.service.UserServiceModel;
import com.example.musicdb.repo.UserRepo;
import com.example.musicdb.repo.UserRoleRepo;
import com.example.musicdb.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MusicDBUserDetailsService musicDBUserDetailsService;
    private final UserRoleRepo userRoleRepo;

    public UserServiceImpl(ModelMapper modelMapper, UserRepo userRepo, PasswordEncoder passwordEncoder, MusicDBUserDetailsService musicDBUserDetailsService, UserRoleRepo userRoleRepo) {
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.musicDBUserDetailsService = musicDBUserDetailsService;
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    public UserServiceModel registerAndLogin(UserServiceModel userServiceModel) {
        UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class)
                .setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .setRoles(List.of(userRoleRepo.getById(2L)));
        UserEntity registeredUser = userRepo.save(userEntity);

        UserDetails principal = musicDBUserDetailsService.loadUserByUsername(userServiceModel.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, principal.getUsername(), principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return modelMapper.map(registeredUser, UserServiceModel.class);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(IllegalArgumentException::new);
    }
}
