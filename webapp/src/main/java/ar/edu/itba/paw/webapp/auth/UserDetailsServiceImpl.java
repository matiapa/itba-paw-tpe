package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UserService us;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<User> userOpt = us.findByEmail(email);

        if (! userOpt.isPresent())
            throw new UsernameNotFoundException("Email not found");

        final User user = userOpt.get();

        final Collection<? extends GrantedAuthority> authorities = Arrays.asList(
            new SimpleGrantedAuthority("ROLE_USER"),
            new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        return new org.springframework.security.core.userdetails.User(
            user.getName(), user.getPassword(), authorities
        );
    }
}