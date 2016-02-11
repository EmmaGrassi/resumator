package io.sytac.resumator.user;

import io.sytac.resumator.command.AbstractCommand;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.enums.Nationality;
import io.sytac.resumator.utils.DateUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Retrieves and stores profiles in memory
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class InMemoryProfileRepository implements ProfileRepository {

    private final Map<String, Profile> profiles = new ConcurrentHashMap<>();

    private final Map<String, String> profileIdToEmail = new ConcurrentHashMap<>();

    private final EventPublisher events;

    @Inject
    public InMemoryProfileRepository(final EventPublisher events) {
        this.events = events;
    }


    public Optional<Profile> get(final String id) {
        return profiles.values()
                .stream()
                .filter(account -> id.equals(account.getId()))
                .findFirst();
    }

    @Override
    public Profile getProfileByEmail(final String email) {
        return profiles.get(email);
    }

    @Override
    public Profile register(final AbstractCommand command) {
        final Profile profile = add(command);

        events.publish(command);

        return profile;
    }

    private Profile createFromCommand(final String accountId, final AbstractCommand command) {
        final ProfileCommandPayload payload = (ProfileCommandPayload)command.getPayload();

        return new Profile(accountId,
                payload.getTitle(),
                payload.getName(),
                payload.getSurname(),
                DateUtils.convert(payload.getDateOfBirth()),
                payload.getEmail(),
                payload.getPhonenumber(),
                Nationality.valueOf(payload.getNationality()),
                payload.getCityOfResidence(),
                payload.getCountryOfResidence(),
                payload.getGithub(),
                payload.getLinkedin(),
                payload.getAboutMe(),
                false
        );
    }

    @Override
    public Profile add(final AbstractCommand command) {
        final String profileId = Optional.ofNullable(command.getHeader().getId()).orElse(UUID.randomUUID().toString());
        final Profile profile = createFromCommand(profileId, command);
        final Profile previous = profiles.putIfAbsent(profile.getEmail(), profile);

        if (previous != null) {
            throw new IllegalArgumentException("Duplicate profile: " + previous);
        }

        profileIdToEmail.put(profile.getId(), profile.getEmail());

        return profile;
    }

    @Override
    public Profile update(AbstractCommand command) {
        final String profileId = Optional.ofNullable(command.getHeader().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot update profile because profileId is null or empty"));

        final String profileEmail = profileIdToEmail.get(profileId);
        if (!profiles.containsKey(profileEmail)) {
            throw new IllegalArgumentException(String.format("Profile with email '%s' not found in the repository", profileEmail));
        }

        final Profile profile = createFromCommand(profileId, command);
        profiles.put(profileEmail, profile);

        events.publish(command);

        return profile;
    }

    @Override
    public void remove(AbstractCommand command) {
        // NOP
    }

    @Override
    public List<Profile> getAll() {
        return null;
    }


}
