package com.formacionbanca.springbootserviceusers.models.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="roles")
public class Role implements Serializable {

    private static final long serialVersionUID = 4344639922895534220L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 30)
    private String name;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
//    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }
}
