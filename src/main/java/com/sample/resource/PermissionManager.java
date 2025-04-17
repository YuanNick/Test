package com.sample.resource;

import com.sample.resource.db.RegisteredClientApiPermission;
import com.sample.resource.db.RegisteredClientApiPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class PermissionManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final RegisteredClientApiPermissionRepository permissionRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        String clientId = authentication.get().getName();
        List<RegisteredClientApiPermission> apiPermissionList = permissionRepository.findByIdClientId(clientId);

        String url = context.getRequest().getRequestURI();



        // TODO test
        return new AuthorizationDecision(true);
    }

}
