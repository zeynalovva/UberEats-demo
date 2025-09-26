package az.zeynalovv.UberEats.identity.repository;

import az.zeynalovv.UberEats.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

  boolean existsUserByEmailOrPhoneNumber(String email, String phoneNumber);

  Optional<User> findUserByEmail(String email);

  boolean existsUserByEmail(String email);
}
