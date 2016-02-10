package io.sytac.resumator.user;

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
    public Profile register(final NewProfileCommand command) {
        final Profile profile = this.add(command);
        events.publish(command);
        return profile;
    }

    private Profile createFromPayload(final String accountId, final NewProfileCommand command) {
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
    public Profile add(final NewProfileCommand command) {
        final String profileId = Optional.ofNullable(command.getHeader().getId()).orElse(UUID.randomUUID().toString());
        final Profile profile = createFromPayload(profileId, command);
        final Profile previous = profiles.putIfAbsent(profile.getEmail(), profile);

        if (previous != null) {
            throw new IllegalArgumentException("Duplicate profile: " + previous);
        }

        profileIdToEmail.put(profile.getId(), profile.getEmail());
        return profile;
    }

    @Override
    public Profile update(UpdateProfileCommand command) {
        return null;
    }

    @Override
    public void remove(RemoveProfileCommand command) {
        // NOP
    }

    @Override
    public List<Profile> getAll() {
        return null;
    }


}
