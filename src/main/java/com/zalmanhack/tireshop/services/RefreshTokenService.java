package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.RefreshToken;
import com.zalmanhack.tireshop.exceptions.TokenRefreshException;
import com.zalmanhack.tireshop.repos.RefreshTokenRepo;
import com.zalmanhack.tireshop.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.expiration.ms}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepo refreshTokenRepo;

    private final UserService userService;

    public RefreshTokenService(RefreshTokenRepo refreshTokenRepo, UserService userService) {
        this.refreshTokenRepo = refreshTokenRepo;
        this.userService = userService;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userService.findById(userId));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepo.deleteByUser(userService.findById(userId));
    }
}
