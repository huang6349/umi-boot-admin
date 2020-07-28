package org.umi.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.umi.boot.domain.Dict;

import java.util.List;

public interface DictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor<Dict> {

    List<Dict> findByCode(String code);

    List<Dict> findByCodeAndIdNot(String code, Long id);

    List<Dict> findByLevelStartsWith(String level);
}
