package io.sytac.resumator.organization;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.security.Roles;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Expose the details of one organization
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("organization")
@RolesAllowed(Roles.USER)
public class OrganizationQuery extends BaseResource {

    private final OrganizationRepository organizations;

    @Inject
    public OrganizationQuery(final OrganizationRepository organizations) {
        this.organizations = organizations;
    }

    @GET
    @Path("{domain}")
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation getOrganization(@PathParam("domain") final String domain, @Context final UriInfo uriInfo) {
        return organizations.fromDomain(domain)
                .map(org -> buildRepresentation(org, uriInfo))
                .orElseThrow(NotFoundException::new);
    }

    private Representation buildRepresentation(final Organization org, final UriInfo uriInfo) {
        return rest.newRepresentation()
                .withProperty("name", org.getName())
                .withProperty("domain", org.getDomain())
                .withLink("employees", resourceMethodLink(uriInfo, OrganizationQuery.class, "getEmployees", org.getDomain()))
                .withLink("self", uriInfo.getRequestUri().toString());
    }

    @GET
    @Path("{domain}/employees")
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation getEmployees(@PathParam("domain") final String domain) {
        throw new WebApplicationException("Not implemented yet", Response.Status.NOT_IMPLEMENTED);
    }
}
