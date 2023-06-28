package com.formacionbanca.springbootserviceoauth.service;


import com.formacionbanca.springbootserviceoauth.clients.UserFeignClient;
import feign.FeignException;
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
public class UserService implements IUserService, UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            // tipo user de mis models
            com.formacionbanca.springbootservicecommonsusers.models.entity.User userModel = userFeignClient.findUserByUsername(username);

            List<GrantedAuthority> authorities = userModel.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .peek(authority -> logger.info("Role: " + authority.getAuthority()))
                    .collect(Collectors.toList());

            logger.info("usuario autenticado: " + username);

            //tipo userDails
            return new User(userModel.getUsername(), userModel.getPassword(), userModel.getStatus(), true, true, true, authorities);


        }catch (FeignException e) {
            String error = "Error en el login, no existe el usuario '" + username + "' en el sistema";
            logger.error(error);

            throw new UsernameNotFoundException(error);
        }
    }

    @Override
    public com.formacionbanca.springbootservicecommonsusers.models.entity.User findByUsername(String username) {
        return userFeignClient.findUserByUsername(username);
    }

    @Override
    public com.formacionbanca.springbootservicecommonsusers.models.entity.User update(com.formacionbanca.springbootservicecommonsusers.models.entity.User user, Long id) {
        return userFeignClient.update(user,id);
    }
}
