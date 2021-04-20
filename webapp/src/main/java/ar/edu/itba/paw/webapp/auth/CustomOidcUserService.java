package ar.edu.itba.paw.webapp.auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;

public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    
    private OidcUserService delegate;
    private UserService userService;

    public CustomOidcUserService(UserService userService) {
        this.userService = userService;
        this.delegate = new OidcUserService();
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = delegate.loadUser(userRequest);
        Optional<User> user = userService.findByEmail(oidcUser.getEmail());
        if(user.isPresent())
        {
            Collection<GrantedAuthority> mappedAuthorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            return new UserPrincipal(user.get(), mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        }
        else
        {
            Collection<GrantedAuthority> mappedAuthorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_UNREGISTERED"));
            return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        }
    }
    
}
