package io.sytac.resumator.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sytac.resumator.command.CommandPayload;
import lombok.Getter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;

/**
 * Defines the payload of a new Profile command
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Getter
public class ProfileCommandPayload implements CommandPayload {

    @NotBlank(message = "Title is mandatory")
    private final String title;

    @NotBlank(message = "name is mandatory")
    private final String name;

    @NotBlank(message = "surname is mandatory")
    private final String surname;

    @NotBlank(message = "email is mandatory")
    @Email(message = "email format is not correct")
    private final String email;

    @NotBlank(message = "date of birth is mandatory")
    private final String dateOfBirth;

    @NotBlank(message = "phone number is mandatory")
    @Digits(message = "phone number should consist of digits", fraction = 0, integer = 15)
    private final String phonenumber;

    @NotBlank(message = "nationality is mandatory")
    private final String nationality;

    private final String github;

    private final String linkedin;

    @NotBlank(message = "city of residence is mandatory")
    private final String cityOfResidence;

    @NotBlank(message = "country of residence is mandatory")
    private final String countryOfResidence;

    @NotBlank(message = "aboutMe is mandatory")
    private final String aboutMe;

    private final boolean admin;


    @JsonCreator
    public ProfileCommandPayload(@JsonProperty("title") final String title,
                                 @JsonProperty("name") final String name,
                                 @JsonProperty("surname") final String surname,
                                 @JsonProperty("dateOfBirth") final String dateOfBirth,
                                 @JsonProperty("email") final String email,
                                 @JsonProperty("phonenumber") final String phonenumber,
                                 @JsonProperty("nationality") final String nationality,
                                 @JsonProperty("cityOfResidence") final String cityOfResidence,
                                 @JsonProperty("countryOfResidence") final String countryOfResidence,
                                 @JsonProperty("aboutMe") final String aboutMe,
                                 @JsonProperty("github") final String github,
                                 @JsonProperty("linkedin") final String linkedin,
                                 @JsonProperty("admin") final boolean admin) {
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phonenumber = phonenumber;
        this.nationality = nationality;
        this.cityOfResidence = cityOfResidence;
        this.countryOfResidence = countryOfResidence;
        this.github = github;
        this.linkedin = linkedin;
        this.aboutMe = aboutMe;
        this.admin = admin;
    }
}
