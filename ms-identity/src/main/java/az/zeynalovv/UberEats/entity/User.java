package az.zeynalovv.UberEats.entity;


import az.zeynalovv.UberEats.entity.enums.UserRole;
import az.zeynalovv.UberEats.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "users")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true, length = 30)
    private String phoneNumber;

    @Column(name = "country_code", nullable = false, length = 10)
    private String countryCode;

    @Column(name = "status", nullable = false)
    private UserStatus userStatus;

    private UserRole userRole;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @LastModifiedDate
    private LocalDateTime lastLoginAt;

    //private String profileImageUrl;

    @PrePersist
    public void prePersist() {
        this.userStatus = UserStatus.ACTIVE;
        this.userRole = UserRole.USER;
    }

}
