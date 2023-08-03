package io.github.aquerr.rentacar.domain.profile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "user_profile")
public class UserProfileEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_generator")
    @SequenceGenerator(name = "user_profile_generator", sequenceName = "user_profile_seq", allocationSize = 5)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "credentials_id", unique = true, nullable = false)
    private Long credentialsId;

    @Column(name = "first_name", nullable = true, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = true, length = 100)
    private String lastName;

    @Column(name = "phone_number", nullable = true, length = 15)
    private String phoneNumber;

    //TODO: A może zmienić nazwę pola na "contactEmail"?
    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "birth_date", nullable = true)
    private LocalDate birthDate;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "zip_code", nullable = true)
    private String zipCode;

    @Column(name = "street", nullable = true)
    private String street;
}
