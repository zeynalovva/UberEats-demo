package az.zeynalovv.UberEats.repository;

import az.zeynalovv.UberEats.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsUserByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<User> findUserByEmail(String email);
}
