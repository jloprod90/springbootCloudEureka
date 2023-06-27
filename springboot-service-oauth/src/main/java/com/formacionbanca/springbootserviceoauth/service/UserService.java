package com.formacionbanca.springbootserviceoauth.service;


import com.formacionbanca.springbootserviceoauth.clients.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // tipo user de mis models
        com.formacionbanca.springbootservicecommonsusers.models.entity.User user = userFeignClient.findUserByUsername(username);

        if(user == null) {
            logger.error("no encontrado el usuario "+ username +" en el sistema");
            throw new UsernameNotFoundException("no encontrado el usuario "+ username +" en el sistema");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .peek(authority -> logger.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());
        logger.info("usuario autenticado: " + username);
        //tipo userDails
        return new User(user.getUsername(), user.getPassword(), user.getStatus(), true, true, true, authorities);

    }

}
