package com.example.musicdb.service.impl;

import com.example.musicdb.model.entity.UserEntity;
import com.example.musicdb.repo.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MusicDBUserService implements UserDetailsService {
    private final UserRepo userRepo;

    public MusicDBUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByName(username).orElseThrow(
                () -> new UsernameNotFoundException("User with name " + username + " not found."));
        return mapToUserDetails(userEntity);
    }

    private UserDetails mapToUserDetails(UserEntity userEntity) {
        List<GrantedAuthority> authorities =  userEntity.getRoles().stream()
                .map(userRoleEntity -> new SimpleGrantedAuthority(userRoleEntity.getRole().name()))
                .collect(Collectors.toList());

        return new User(userEntity.getName(), userEntity.getPassword(), authorities);
    }
}
