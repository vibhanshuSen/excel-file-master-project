package com.example.uploadexcelfile.utils;


import com.example.uploadexcelfile.constants.ConstantsUtils;
import com.example.uploadexcelfile.model.UserDetails;
import com.google.common.collect.Lists;

import java.util.List;

public class UserHelper {

    public static List<UserDetails> getUsers() {
        return Lists.newArrayList(
                new UserDetails(1, ConstantsUtils.DEFAULT_USER, ConstantsUtils.DEFAULT_USER_PASSWORD, ConstantsUtils.USER_ROLE),
                new UserDetails(2, ConstantsUtils.DEFAULT_ADMIN, ConstantsUtils.DEFAULT_ADMIN_PASSWORD, ConstantsUtils.ADMIN_ROLE));
    }

    public static UserDetails getUser(String userName) {
        return getUsers()
                .stream()
                .filter(userDetails -> userDetails
                        .getUserName()
                        .equals(userName))
                .findFirst()
                .orElse(null);
    }

}
