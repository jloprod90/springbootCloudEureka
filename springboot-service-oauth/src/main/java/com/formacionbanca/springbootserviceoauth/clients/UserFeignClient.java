package com.formacionbanca.springbootserviceoauth.clients;

import com.formacionbanca.springbootservicecommonsusers.models.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "service-users")
public interface UserFeignClient {

    @GetMapping("/users/search/buscar-username")
    public User findUserByUsername(@RequestParam String username);

    @PutMapping("/users/{id}")
    public User update(@RequestBody User user, @PathVariable Long id);

}
