package io.github.aquerr.rentacar.domain.user.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "user_credentials")
@Builder
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserCredentialsEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_credentials_generator")
    @SequenceGenerator(name = "user_credentials_generator", sequenceName = "user_credentials_seq", allocationSize = 5)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection
    @CollectionTable(name = "user_authority", joinColumns = @JoinColumn(name = "credentials_id"))
    @Column(name = "authority")
    private Set<String> authorities;

    @Column(name = "activated", nullable = false, columnDefinition = "BOOLEAN default 0")
    private boolean activated;

    @Column(name = "locked", nullable = false, columnDefinition = "BOOLEAN default 0")
    private boolean locked;

    @CreationTimestamp
    @Column(name = "created_date_time", nullable = false)
    private ZonedDateTime createdDateTime;

    @Column(name = "activated_date_time", nullable = true)
    private ZonedDateTime activatedDateTime;

    public UserCredentialsEntity()
    {

    }

    public UserCredentialsEntity(Long id, String username, String email, String password, Set<String> authorities)
    {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
