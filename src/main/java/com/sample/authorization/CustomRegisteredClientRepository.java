package com.sample.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        RegisteredClientEntity entity;
        try {
            entity = convert(registeredClient);
        } catch (JsonProcessingException e) {
            // FIXME
            throw new RuntimeException(e);
        }

        entity.setUpdatedTime(LocalDateTime.now());
        entityRepository.save(entity);
    }

    @Override
    public RegisteredClient findById(String id) {
        Optional<RegisteredClientEntity> optional = entityRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }

        try {
            return convert(optional.get());
        } catch (JsonProcessingException e) {
            // FIXME
            throw new RuntimeException(e);
        }
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<RegisteredClientEntity> optional = entityRepository.findByClientId(clientId);
        if (optional.isEmpty()) {
            return null;
        }

        try {
            return convert(optional.get());
        } catch (JsonProcessingException e) {
            // FIXME
            throw new RuntimeException(e);
        }
    }

    private RegisteredClient convert(RegisteredClientEntity entity) throws JsonProcessingException {
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

    private RegisteredClientEntity convert(RegisteredClient registeredClient) throws JsonProcessingException {
        RegisteredClientEntity entity = new RegisteredClientEntity();
        entity.setId(registeredClient.getId());
        entity.setClientId(registeredClient.getClientId());
        entity.setClientSecret(registeredClient.getClientSecret());
        entity.setClientName(registeredClient.getClientName());
        return entity;
    }

}
