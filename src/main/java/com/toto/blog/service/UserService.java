package com.toto.blog.service;

import com.toto.blog.entity.User;

public interface UserService {

    User checkUser(String username,String password);
}
