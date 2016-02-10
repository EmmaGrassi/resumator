package io.sytac.resumator.user;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.docx.DocxGenerator;
import io.sytac.resumator.employee.Employee;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.*;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Retrieve information about one employee
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("profiles/{email}")
@RolesAllowed(Roles.USER)
@Slf4j
public class ProfileQuery extends BaseResource {

    private static final String PATH_PARAM_EMAIL = "email";

    private final ProfileRepository profileRepository;


    @Inject
    public ProfileQuery(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getProfile(@PathParam(PATH_PARAM_EMAIL) final String email,
                               @UserPrincipal Identity identity,
                               @Context final UriInfo uriInfo) {

        return represent(email, identity, uriInfo);
    }

    /**
     * Translates an {@link Employee} into its HAL representation
     *
     * @param email The email of desired employee
     * @param identity The current identity
     * @param uriInfo  The current REST endpoint information
     * @return The {@link Representation} of the {@link Employee}
     */
    private Response represent(final String email, final Identity identity, final UriInfo uriInfo) {
        final Profile profile = profileRepository.getProfileByEmail(email);

        if (profile != null) {
            final String representation = rest.newRepresentation()
                    .withProperty("title", profile.getTitle())
                    .withProperty("name", profile.getName())
                    .withProperty("surname", profile.getSurname())
                    .withProperty("dateOfBirth", profile.getDateOfBirth())
                    .withProperty("email", profile.getEmail())
                    .withProperty("phonenumber", profile.getPhoneNumber())
                    .withProperty("nationality", profile.getNationality())
                    .withProperty("cityOfResidence", profile.getCityOfResidence())
                    .withProperty("countryOfResidence", profile.getCountryOfResidence())
                    .withProperty("aboutMe", profile.getAboutMe())
                    .withProperty("github", profile.getGitHub())
                    .withProperty("linkedin", profile.getLinkedIn())
                    .withProperty("admin", (identity.hasRole(Roles.ADMIN) || profile.isAdmin()))
                    .withLink("self", uriInfo.getRequestUri().toString())
                    .toString(RepresentationFactory.HAL_JSON);

            return Response.ok(representation).build();
        } else {
            return Response.ok().status(HttpStatus.NOT_FOUND_404).build();
        }
    }
}
