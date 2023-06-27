package com.formacionbanca.springbootserviceoauth.clients;

import com.formacionbanca.springbootservicecommonsusers.models.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-users")
public interface UserFeignClient {

    @GetMapping("/users/search/buscar-username")
    public User findUserByUsername(@RequestParam String username);

}
