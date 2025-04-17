package com.sample.resource.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisteredClientApiPermissionRepository
        extends JpaRepository<RegisteredClientApiPermission, RegisteredClientApiPermissionId> {

    List<RegisteredClientApiPermission> findByIdClientId(String clientId);

}