package io.sytac.resumator.security;

import javax.security.auth.Subject;
import java.security.Principal;

/**
 * Created by skuro on 04/12/15.
 */
class User implements Principal {

    private final String name;

    User(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
