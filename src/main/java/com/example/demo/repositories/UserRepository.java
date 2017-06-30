package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by student on 6/29/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    //@Query("INSERT INTO user (password, username, enabled) values (:password, :username, true)")
    //public void insertUser(@Param("password") String password, @Param("username") String username);


}