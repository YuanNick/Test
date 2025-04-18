package com.sample.authorization;

import com.sample.authorization.db.RegisteredClientEntity;
import com.sample.authorization.db.RegisteredClientEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final RegisteredClientEntityRepository entityRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
        RegisteredClientEntity entity = convert(registeredClient);
        entity.setUpdatedTime(LocalDateTime.now());

        entityRepository.save(entity);
    }

    @Override
    public RegisteredClient findById(String id) {
        Optional<RegisteredClientEntity> optional = entityRepository.findById(id);
        return optional.map(this::convert)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<RegisteredClientEntity> optional = entityRepository.findByClientId(clientId);
        return optional.map(this::convert)
                .orElse(null);
    }

    private RegisteredClient convert(RegisteredClientEntity entity) {
        TokenSettings tokenSettings = TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofHours(25))
                .build();
        return RegisteredClient.withId(entity.getId())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .clientName(entity.getClientName())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(tokenSettings)
                .build();
    }

    private RegisteredClientEntity convert(RegisteredClient registeredClient) {
        RegisteredClientEntity entity = new RegisteredClientEntity();
        entity.setId(registeredClient.getId());
        entity.setClientId(registeredClient.getClientId());
        entity.setClientSecret(registeredClient.getClientSecret());
        entity.setClientName(registeredClient.getClientName());
        return entity;
    }

}
