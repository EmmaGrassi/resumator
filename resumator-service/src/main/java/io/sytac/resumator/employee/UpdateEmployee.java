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
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.*;
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
@Path("employees/{email}")
@RolesAllowed(Roles.USER)
public class UpdateEmployee extends BaseResource {

    private final OrganizationRepository organizations;
    private final CommandFactory descriptors;
    private final EventPublisher events;

    @Inject
    public UpdateEmployee(final OrganizationRepository organizations, final CommandFactory descriptors, final EventPublisher events) {
        this.organizations = organizations;
        this.descriptors = descriptors;
        this.events = events;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({RepresentationFactory.HAL_JSON, MediaType.APPLICATION_JSON})
    public Response updateEmployee(@PathParam("email") final String email,
                                   final EmployeeCommandPayload payload,
                                   @UserPrincipal final Identity identity,
                                   @Context final UriInfo uriInfo) throws NoPermissionException, OperationNotSupportedException {
    	
        Map<String, String> notValidatedFields=EmployeeValidator.validateEmployee(payload);
        if(notValidatedFields.size()>0)
        	return buildValidationFailedRepresentation(uriInfo, notValidatedFields);

        final String checkedEmail = Optional.ofNullable(email).orElseThrow(IllegalArgumentException::new);
        validateEmails(checkedEmail, payload.getEmail());
        final Organization organization = organizations.get(identity.getOrganizationId()).orElseThrow(InvalidOrganizationException::new);

        if (!identity.hasRole(Roles.ADMIN)) {
            checkPermissionsForUpdate(identity, email);
            validateRestrictedFields(organization, payload);
        }

        final Employee employee = organization.getEmployeeByEmail(email);
        if (employee == null) {
            return Response.status(HttpStatus.NOT_FOUND_404).build();
        }
        

        final UpdateEmployeeCommand command = descriptors.updateEmployeeCommand(employee.getId(), payload, organization.getDomain());
        final Employee updatedEmployee = organization.updateEmployee(command);


        events.publish(command);

        return buildRepresentation(uriInfo, updatedEmployee);
    }

    private Response buildValidationFailedRepresentation(final UriInfo uriInfo,Map<String, String> notValidatedFields) {
      	
        final Representation halResource = rest.newRepresentation()
                .withProperty("status", "failed")
                .withProperty("fields", notValidatedFields);


        return Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
                .status(HttpStatus.BAD_REQUEST_400)
                .build();
    }

    private void checkPermissionsForUpdate(final Identity identity, final String email) throws NoPermissionException {
        if (!email.equals(identity.getName())) {
            throw new NoPermissionException("You don't have permissions to update this profile");
        }
    }

    private void validateEmails(final String pathEmail, final String payloadEmail) throws OperationNotSupportedException {
        if (!pathEmail.equals(payloadEmail)) {
            throw new OperationNotSupportedException("Unable to change email address - operation is not supported");
        }
    }

    private void validateRestrictedFields(final Organization organization, final EmployeeCommandPayload payload) throws NoPermissionException {
        Employee currentEmployee = organization.getEmployeeByEmail(payload.getEmail());

        if (currentEmployee.isAdmin() != payload.isAdmin() ||
                currentEmployee.getType() != payload.getType()) {
            throw new NoPermissionException("You do not have permissions to update all the fields you provided");
        }
    }

    private Response buildRepresentation(final UriInfo uriInfo, final Employee employee) {
        final URI employeeLink = resourceLink(uriInfo, EmployeeQuery.class, employee.getEmail());
        final Representation halResource = rest.newRepresentation()
                .withProperty("status", "updated")
                .withProperty("email", employee.getEmail())
                .withLink("employee", employeeLink);

        return Response.ok(halResource.toString(RepresentationFactory.HAL_JSON))
                .status(HttpStatus.OK_200)
                .header(HttpHeader.LOCATION.asString(), employeeLink.toString())
                .build();
    }
}
