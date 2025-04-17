package com.sample.resource;

import com.sample.resource.db.RegisteredClientApiPermission;
import com.sample.resource.db.RegisteredClientApiPermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final RegisteredClientApiPermissionRepository permissionRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier,
            RequestAuthorizationContext context) {
        Authentication authentication = authenticationSupplier.get();

        boolean authorized;
        try {
            authorized = checkUrl(authentication, context);
        } catch (Exception e) {
            // FIXME by Nick
            log.error("Authentication error", e);
            authorized = false;
        }

        return new AuthorizationDecision(authorized);
    }

    private boolean checkUrl(Authentication authentication, RequestAuthorizationContext context) {
        String clientId = authentication.getName();
        List<RegisteredClientApiPermission> apiPermissionList = permissionRepository.findByIdClientId(clientId);

        String requestURI = context.getRequest().getRequestURI();
        String toCheckPath = extractFirstPathSegment(requestURI);

        for (RegisteredClientApiPermission apiPermission : apiPermissionList) {
            String allowedPath = extractFirstPathSegment(apiPermission.getId().getApiUrl());
            if (StringUtils.equals(allowedPath, toCheckPath)) {
                return true;
            }
        }

        return false;
    }

    private String extractFirstPathSegment(String path) {
        String[] segments = StringUtils.strip(path, "/").split("/");
        return segments[0];
    }

}
