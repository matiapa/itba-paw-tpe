package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.Permission;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UserService us;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<User> userOpt = us.findByEmail(email);

        if (!userOpt.isPresent()) {
            LOGGER.debug("verification, no user with email {} could be found",email);
            throw new UsernameNotFoundException("Email not found");
        }

        final User user = userOpt.get();

        final Collection<? extends GrantedAuthority> authorities = user.getPermissions().stream()
            .map(Permission::toString)
            .map(p -> "ROLE_" + p)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new UserPrincipal(user, authorities);
    }

}