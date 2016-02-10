package io.sytac.resumator.user;

import java.util.List;

/**
 * Represent a repository of profiles
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public interface ProfileRepository {


    Profile getProfileByEmail(String email);


    Profile register(NewProfileCommand command);


    Profile add(NewProfileCommand command);


    Profile update(UpdateProfileCommand command);


    void remove(RemoveProfileCommand command);


    List<Profile> getAll();
}
