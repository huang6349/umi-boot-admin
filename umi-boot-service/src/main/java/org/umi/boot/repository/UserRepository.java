package org.umi.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.umi.boot.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findByUsername(String username);

    List<User> findByEmail(String email);

    List<User> findByMobilePhone(String mobilePhone);

    List<User> findByEmailAndIdNot(String email, Long id);

    List<User> findByMobilePhoneAndIdNot(String mobilePhone, Long id);
}
