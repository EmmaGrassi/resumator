package io.sytac.resumator.user;

import io.sytac.resumator.model.enums.Nationality;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Profile is the base object with basic information about the user.
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Getter
@EqualsAndHashCode
@ToString
public class Profile {

    private final String id;
    private final String title;
    private final String name;
    private final String surname;
    private final Date dateOfBirth;
    private final String email;
    private final String phoneNumber;
    private final Nationality nationality;
    private final String cityOfResidence;
    private final String countryOfResidence;
    private final String gitHub;
    private final String linkedIn;
    private final String aboutMe;
    private final boolean admin;

    @Builder
    public Profile(String id, String title, String name, String surname, Date dateOfBirth, String email,
                   String phoneNumber, Nationality nationality, String cityOfResidence, String countryOfResidence,
                   String gitHub, String linkedIn, String aboutMe, boolean admin) {

        this.id = Optional.ofNullable(id).orElse(UUID.randomUUID().toString());
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.nationality = nationality;
        this.cityOfResidence = cityOfResidence;
        this.countryOfResidence = countryOfResidence;
        this.gitHub = gitHub;
        this.linkedIn = linkedIn;
        this.aboutMe = aboutMe;
        this.admin = admin;
    }
}
