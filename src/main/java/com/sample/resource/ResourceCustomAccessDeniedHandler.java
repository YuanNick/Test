package com.sample.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ResourceCustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        // TODO log原始錯誤訊息

        ErrorInfo errorInfo = new ErrorInfo("OAuth-Resource-0002",
                "The access token does not have permission to access the requested API.");
        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(errorInfoJson);
    }

}
