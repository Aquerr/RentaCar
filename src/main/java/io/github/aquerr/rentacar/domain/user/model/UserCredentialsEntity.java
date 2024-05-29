package io.github.aquerr.rentacar.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "user_credentials")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsEntity
{
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "activated", nullable = false, columnDefinition = "BOOLEAN default 0")
    private boolean activated;

    @Column(name = "locked", nullable = false, columnDefinition = "BOOLEAN default 0")
    private boolean locked;

    @CreationTimestamp
    @Column(name = "created_date_time", nullable = false)
    private ZonedDateTime createdDateTime;

    @Column(name = "activated_date_time", nullable = true)
    private ZonedDateTime activatedDateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserEntity user;

    public UserCredentialsEntity(Long userId, String username, String email, String password)
    {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
