package io.sytac.resumator.user;

import io.sytac.resumator.command.AbstractCommand;

import java.util.List;

/**
 * Represent a repository of profiles
 *
 * @author Dmitry Ryazanov
 * @since 0.1
 */
public interface ProfileRepository {


    Profile getProfileByEmail(String email);


    Profile register(AbstractCommand command);


    Profile add(AbstractCommand command);


    Profile update(AbstractCommand command);


    void remove(AbstractCommand command);


    List<Profile> getAll();
}
