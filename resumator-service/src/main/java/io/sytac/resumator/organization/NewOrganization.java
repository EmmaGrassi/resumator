package io.sytac.resumator.organization;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.security.Roles;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Map;

/**
 * Exposes a command API to create a new Organization
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("organizations")
@RolesAllowed({Roles.SYSADMIN})
public class NewOrganization extends BaseResource {

    private final OrganizationRepository organizations;
    private final CommandFactory descriptors;

    @Inject
    public NewOrganization(final OrganizationRepository organizations, final CommandFactory descriptors) {
        this.organizations = organizations;
        this.descriptors = descriptors;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({RepresentationFactory.HAL_JSON, MediaType.APPLICATION_JSON})
    public Response createOrganization(final Map<String, String> input,
                                             @Context final UriInfo uriInfo,
                                             @Context final SecurityContext securityContext) {
        final NewOrganizationCommand command = descriptors.newOrganizationCommand(input);
        final Organization org = organizations.register(command);
        return buildRepresentation(uriInfo, org);
    }

    private Response buildRepresentation(final UriInfo uriInfo, final Organization org) {
        final URI orgLink = resourceLink(uriInfo, OrganizationQuery.class, org.getId());
        final Representation halResource = rest.newRepresentation()
                .withProperty("status", "created")
                .withProperty("id", org.getId())
                .withLink("organisation", orgLink);

        return Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
                .status(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.asString(), orgLink.toString())
                .build();
    }
}
