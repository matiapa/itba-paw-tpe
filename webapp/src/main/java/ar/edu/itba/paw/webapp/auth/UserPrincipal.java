package ar.edu.itba.paw.webapp.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import ar.edu.itba.paw.models.User;

public class UserPrincipal extends User implements OidcUser {
    private OidcIdToken idToken;
    private OidcUserInfo userInfo;
	private Map<String, Object> attributes;
	private Set<GrantedAuthority> authorities;

    public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, OidcUserInfo userInfo) {
        super(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getCareer_id());
        this.idToken = idToken;
        this.userInfo = userInfo;

        this.authorities = (authorities != null)
				? Collections.unmodifiableSet(new LinkedHashSet<>(authorities))
				: Collections.unmodifiableSet(new LinkedHashSet<>(AuthorityUtils.NO_AUTHORITIES));

		Map<String, Object> claims = new HashMap<>();
		if (userInfo != null) {
			claims.putAll(userInfo.getClaims());
		}
		claims.putAll(idToken.getClaims());
        this.attributes = Collections.unmodifiableMap(claims);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getClaims() {
        return getAttributes();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return idToken;
    }

}
