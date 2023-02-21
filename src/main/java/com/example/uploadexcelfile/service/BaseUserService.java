package com.example.uploadexcelfile.service;

import com.example.uploadexcelfile.exception.FileException;
import com.example.uploadexcelfile.model.UserDetails;
import com.example.uploadexcelfile.utils.UserHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BaseUserService {
    /**
     * This method is used to get authenticated user
     * @return user
     */
    public UserDetails getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        UserDetails userDetails = UserHelper.getUser(auth.getName());
        if (userDetails == null) {
            throw new FileException(HttpStatus.UNAUTHORIZED.value(), "Please login.!!");
        }
        return userDetails;
    }

}
