package com.sample.resource.db;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "registered_client_api_permission")
public class RegisteredClientApiPermission {

    @EmbeddedId
    private RegisteredClientApiPermissionId id;

    @Column(name = "granted_time", nullable = false)
    private LocalDateTime grantedTime;

    @Override
    public int hashCode() {
        return Objects.hash(getId());
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
        RegisteredClientApiPermission other = (RegisteredClientApiPermission) obj;
        return Objects.equals(getId(), other.getId());
    }


}