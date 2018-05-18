package com.luxmart.service.impl;

import org.springframework.stereotype.Service;

import com.luxmart.model.security.CurrentUser;
import com.luxmart.model.security.Role;


@Service
public class CurrentUserService {

	public boolean canAccessUser(CurrentUser currentUser, String userId) {
//       log.debug("Checking if user={} has access to user={}", currentUser, userId);
        return currentUser != null
                && (currentUser.getRole() == Role.ROLE_ADMIN || currentUser.getId().equals(userId));
    }
}
