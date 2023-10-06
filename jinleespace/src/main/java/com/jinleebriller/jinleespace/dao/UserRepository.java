package com.jinleebriller.jinleespace.dao;

import com.jinleebriller.jinleespace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findOneById(String id);

}
