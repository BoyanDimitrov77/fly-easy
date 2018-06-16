package com.fly.easy.flyeasy.service.interfaces;

import com.fly.easy.flyeasy.api.dto.UserDto;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.model.VerificationToken;

public interface VerificationTokenService {

    VerificationToken generateTokenForUser(User user);

    UserDto verifyToken(String token);

    VerificationToken findByUser(User user);

    VerificationToken findByToken(String token);

    String urlFromToken(VerificationToken token);

    void resetUserPassword(String token, String password);

    void deleteToken(VerificationToken dbToken);

    String urlFromToken(String token, String type);

}
