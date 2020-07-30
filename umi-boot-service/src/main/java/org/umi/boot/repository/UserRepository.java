package org.umi.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.umi.boot.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByMobilePhone(String mobilePhone);

    Optional<User> findByEmailAndIdNot(String email, Long id);

    Optional<User> findByMobilePhoneAndIdNot(String mobilePhone, Long id);
}
