package com.sample.authorization.db;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

// TODO create table
@Data
@Table(name = "registered_client")
@Entity
public class RegisteredClientEntity {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "client_id", nullable = false, unique = true, length = 100)
    private String clientId;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    @Column(name = "client_name", nullable = false, length = 100)
    private String clientName;

    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

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
        RegisteredClientEntity other = (RegisteredClientEntity) obj;
        return Objects.equals(getId(), other.getId());
    }

}
