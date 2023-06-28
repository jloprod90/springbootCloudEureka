package com.formacionbanca.springbootserviceoauth.service;

import com.formacionbanca.springbootservicecommonsusers.models.entity.User;

public interface IUserService {

    public User findByUsername(String username);
}
