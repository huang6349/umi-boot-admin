package org.umi.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.umi.boot.domain.Authority;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {

    List<Authority> findByName(String name);

    List<Authority> findByCode(String code);

    List<Authority> findByNameAndIdNot(String name, Long id);

    List<Authority> findByCodeAndIdNot(String code, Long id);
}
