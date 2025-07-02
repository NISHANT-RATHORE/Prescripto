package com.example.authservice.Mapper;

import com.example.authservice.Model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper extends User {
    public static User toUser(User user) {
        return new User(user.getName(), user.getUsername(), user.getPassword());
    }
}
