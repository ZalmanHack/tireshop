package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.RefreshToken;
import com.zalmanhack.tireshop.exceptions.TokenRefreshException;
import com.zalmanhack.tireshop.repos.RefreshTokenRepo;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.expiration.ms}")
    private Long refreshTokenDurationMs;

    private final TransactionHandler transactionHandler;

    private final RefreshTokenRepo refreshTokenRepo;

    private final UserService userService;

    public RefreshTokenService(TransactionHandler transactionHandler, RefreshTokenRepo refreshTokenRepo, UserService userService) {
        this.transactionHandler = transactionHandler;
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

        RefreshToken finalRefreshToken = refreshToken;
        refreshToken = transactionHandler.runInTransaction(() -> refreshTokenRepo.save(finalRefreshToken));
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
