package com.sample.resource.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;


@Data
@Embeddable
public class RegisteredClientApiPermissionId {

    @Column(name = "client_id", nullable = false, length = 100)
    private String clientId;

    @Column(name = "api_url", nullable = false)
    private String apiUrl;

    @Override
    public int hashCode() {
        return Objects.hash(clientId, apiUrl);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RegisteredClientApiPermissionId other = (RegisteredClientApiPermissionId) obj;
        return Objects.equals(clientId, other.clientId) && Objects.equals(apiUrl, other.apiUrl);
    }

}
