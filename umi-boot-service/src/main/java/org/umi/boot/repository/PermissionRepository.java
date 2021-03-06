package org.umi.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.umi.boot.domain.Authority;
import org.umi.boot.domain.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    List<Permission> findByName(String name);

    List<Permission> findByLevelStartsWith(String level);

    List<Permission> findByNameAndIdNot(String name, Long id);

    List<Permission> findByAuthoritiesInOrderBySeqDesc(Set<Authority> authorities);
}
