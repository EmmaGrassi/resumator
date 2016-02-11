package io.sytac.resumator.user;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.UserPrincipal;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Lists the profiles stored into the Resumator
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
@Path("profiles")
@RolesAllowed(Roles.ADMIN)
public class ProfilesQuery extends BaseResource {

    private static final String REL_SELF = "self";

    private static final String REL_PROFILES = "profiles";

    private static final String REL_NEXT = "next";

    private static final String QUERY_PARAM_PAGE = "page";

    private static final int FIRST_PAGE = 1;

    static final int DEFAULT_PAGE_SIZE = 25;

    private ProfileRepository profileRepository;


    @Inject
    public ProfilesQuery(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation getProfiles(@QueryParam(QUERY_PARAM_PAGE) final Integer page,
                                      @UserPrincipal final Identity identity,
                                      @Context final UriInfo uriInfo) throws NoPermissionException {

        final Representation representation = rest.newRepresentation()
                .withLink(REL_SELF, uriInfo.getRequestUri())
                .withLink(REL_PROFILES, resourceLink(uriInfo, ProfilesQuery.class));

        final int pageNumber = Math.max(Optional.ofNullable(page).orElse(FIRST_PAGE), FIRST_PAGE);
        final List<Profile> allProfiles = profileRepository.getAll();
        allProfiles.stream().sorted(Comparator.comparing(Profile::getSurname).thenComparing(Profile::getName))
                .skip(DEFAULT_PAGE_SIZE * (pageNumber - 1)).limit(DEFAULT_PAGE_SIZE)
                .forEach(profile -> representation.withRepresentation(REL_PROFILES, represent(profile, uriInfo)));

        if (hasNextPage(allProfiles.size(), pageNumber)) {
            representation.withLink(REL_NEXT, createURIForPage(uriInfo, pageNumber + 1));
        }

        return representation;
    }

    /**
     * Translates an {@link Profile} coarsely into a HAL representation
     *
     * @param profile The profile to represent
     * @param uriInfo  The current REST endpoint information
     * @return The {@link Representation} of the {@link Profile}
     */
    private Representation represent(final Profile profile, final UriInfo uriInfo) {
        return rest.newRepresentation()
                .withProperty("email", profile.getEmail())
                .withProperty("name", profile.getName())
                .withProperty("surname", profile.getSurname())
                .withLink(REL_SELF, resourceLink(uriInfo, ProfileQuery.class, profile.getEmail()));
    }

    private boolean hasNextPage(final int numberOfProfiles, final int currentPage) {
        return currentPage * DEFAULT_PAGE_SIZE < numberOfProfiles;
    }

    private URI createURIForPage(final UriInfo uriInfo, int page) {
        return UriBuilder.fromUri(uriInfo.getRequestUri()).replaceQueryParam(QUERY_PARAM_PAGE, page).build();
    }
}
