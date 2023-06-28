package com.formacionbanca.springbootserviceoauth.security;

import com.formacionbanca.springbootservicecommonsusers.models.entity.User;
import com.formacionbanca.springbootserviceoauth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class InfoAdditionalToken implements TokenEnhancer {
    @Autowired
    private IUserService userService;

    public OAuth2AccessToken enhance (OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new HashMap<>();

        User user = userService.findByUsername(authentication.getName());
        info.put("name", user.getName());
        info.put("surname", user.getSurname());
        info.put("email", user.getEmail());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

        return accessToken;
    }


}
