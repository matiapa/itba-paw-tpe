package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.Permission;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
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

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<User> userOpt = us.findByEmail(email);

        if (! userOpt.isPresent())
            throw new UsernameNotFoundException("Email not found");

        final User user = userOpt.get();

        final Collection<? extends GrantedAuthority> authorities = user.getPermissions().stream()
            .map(Permission::toString).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        System.out.println(authorities);

        return new UserPrincipal(user, authorities);
    }
}