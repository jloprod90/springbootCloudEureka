package com.formacionbanca.springbootserviceoauth.security.event;

import com.formacionbanca.springbootservicecommonsusers.models.entity.User;
import com.formacionbanca.springbootserviceoauth.service.IUserService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

    @Autowired
    private IUserService userService;

    @Autowired
    public Tracer tracer;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {

        // if(authentication.getName().equalsIgnoreCase("frontendapp")) {
        if(authentication.getDetails() instanceof WebAuthenticationDetails) {
            return;
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String mensaje = "Success Login: " + user.getUsername();
        System.out.println(mensaje);
        log.info(mensaje);

        User userModel = userService.findByUsername(authentication.getName());

        if(userModel.getIntentos() != null && userModel.getIntentos() > 0) {
            userModel.setIntentos(0);
            userService.update(userModel, userModel.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        String message = "Error en el Login: " + exception.getMessage();
        log.error(message);
        System.out.println(message);

        try {

            StringBuilder errors = new StringBuilder();
            errors.append(message);

            User userModel = userService.findByUsername(authentication.getName());
            if (userModel.getIntentos() == null) {
                userModel.setIntentos(0);
            }

            log.info("Intentos actual es de: " + userModel.getIntentos());

            userModel.setIntentos(userModel.getIntentos()+1);

            log.info("Intentos después es de: " + userModel.getIntentos());

            errors.append(" - Intentos del login: " + userModel.getIntentos());

            if(userModel.getIntentos() >= 3) {
                String errorMaxIntentos = String.format("El usuario %s des-habilitado por máximos intentos.", userModel.getUsername());
                log.error(errorMaxIntentos);
                errors.append(" - " + errorMaxIntentos);
                userModel.setStatus(false);
            }

            userService.update(userModel, userModel.getId());

            tracer.currentSpan().tag("error.mensaje", errors.toString());

        } catch (FeignException e) {
            log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
        }

    }

}