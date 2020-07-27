package org.umi.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.umi.boot.domain.File;

public interface FileRepository extends JpaRepository<File, Long>, JpaSpecificationExecutor<File> {
}
