package com.formacionbanca.springbootserviceusers.repository;


import com.formacionbanca.springbootservicecommonsusers.models.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @RestResource(path = "buscar-username") // nos permite usar este path con RestResource
    User findByUsername(@Param("username") String username); // param -> ?username=
}
