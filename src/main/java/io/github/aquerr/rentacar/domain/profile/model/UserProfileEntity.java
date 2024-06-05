package io.github.aquerr.rentacar.domain.profile.model;

import io.github.aquerr.rentacar.domain.user.model.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name = "first_name", nullable = true, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = true, length = 100)
    private String lastName;

    @Column(name = "phone_number", nullable = true, length = 15)
    private String phoneNumber;

    //TODO: Zmiana adresu email nie powinna odbywać się przez zwykłą edycję profilu.
    // Należy stworzyć do tego dedykowany proces i powiadomić użytkownika o tym, że
    // zmiana maila wiąże się ze zmianą maila podawanego przy logowaniu.
    // Pole do usunięcia, chyba że chcemy zostawić tutaj takie pole
    // jako dodatkowy mail kontaktowy.
    @Column(name = "email", nullable = true)
    private String contactEmail; // domyślnie ten sam, który został podany przy rejestracji

    @Column(name = "birth_date", nullable = true)
    private LocalDate birthDate;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "zip_code", nullable = true)
    private String zipCode;

    @Column(name = "street", nullable = true)
    private String street;

    @Column(name = "icon_name", nullable = true)
    private String iconName;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserEntity user;
}
