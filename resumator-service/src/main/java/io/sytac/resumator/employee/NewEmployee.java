package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.http.BaseResource;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.UserPrincipal;
import io.sytac.resumator.user.ProfileValidator;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

/**
 * Creates a new {@link Employee}
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Path("employees")
@RolesAllowed(Roles.USER)
public class NewEmployee extends BaseResource {

    private final OrganizationRepository organizations;
    private final CommandFactory descriptors;
    private final EventPublisher events;

    @Inject
    public NewEmployee(final OrganizationRepository organizations, final CommandFactory descriptors, final EventPublisher events) {
        this.organizations = organizations;
        this.descriptors = descriptors;
        this.events = events;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({RepresentationFactory.HAL_JSON, MediaType.APPLICATION_JSON})
    public Response newEmployee(final EmployeeCommandPayload payload,
                                @UserPrincipal final Identity identity,
                                @Context final UriInfo uriInfo) throws NoPermissionException {

        final String checkedEmail = Optional.ofNullable(payload.getProfile().getEmail()).orElseThrow(IllegalArgumentException::new);
        if (!identity.hasRole(Roles.ADMIN)) {
            if (!checkedEmail.equals(identity.getName())) {
                throw new IllegalArgumentException("Email address you've submitted is different from the email you have got in your Google Account");
            } else if (payload.getProfile().isAdmin()) {
                throw new NoPermissionException("Only administrators can create user with admin privileges");
            } else if (payload.getType() != null) {
                throw new NoPermissionException("Only administrators can set an initial employee type");
            }
        }

        final Map<String, String> profileFailures = ProfileValidator.validateProfile(payload.getProfile());
        final Map<String, String> employeeFailures = EmployeeValidator.validateEmployee(payload);
        if (profileFailures.size() > 0 || employeeFailures.size() > 0) {
            profileFailures.putAll(employeeFailures);
            return buildValidationFailedRepresentation(profileFailures);
        }

        final Organization organization = organizations.get(identity.getOrganizationId()).orElseThrow(InvalidOrganizationException::new);
        final String domain = organization.getDomain();
        final NewEmployeeCommand command = descriptors.newEmployeeCommand(payload, domain);
        final Employee newEmployee = organization.addEmployee(command);

        events.publish(command);

        return buildRepresentation(uriInfo, newEmployee);
    }

    private Response buildValidationFailedRepresentation(Map<String, String> notValidatedFields) {
        final Representation halResource = rest.newRepresentation()
                .withProperty("status", "failed")
                .withProperty("fields", notValidatedFields);

        return Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
                .status(HttpStatus.BAD_REQUEST_400)
                .build();
    }

    private Response buildRepresentation(final UriInfo uriInfo, final Employee employee) {
        final URI employeeLink = resourceLink(uriInfo, EmployeeQuery.class, employee.getProfile().getEmail());
        final Representation halResource = rest.newRepresentation()
                .withProperty("status", "created")
                .withProperty("email", employee.getProfile().getEmail())
                .withLink("employee", employeeLink);

        return Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
                .status(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.asString(), employeeLink.toString())
                .build();
    }
}
