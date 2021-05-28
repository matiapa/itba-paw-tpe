package ar.edu.itba.paw.webapp.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import ar.edu.itba.paw.models.User;

public class UserPrincipal extends User implements UserDetails, CredentialsContainer {
	private Set<GrantedAuthority> authorities;

    public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(),
        user.getCareer());

        this.authorities = (authorities != null)
				? Collections.unmodifiableSet(new LinkedHashSet<>(authorities))
				: Collections.unmodifiableSet(new LinkedHashSet<>(AuthorityUtils.NO_AUTHORITIES));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
        
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
	public boolean equals(Object rhs) {
		if (rhs instanceof User) {
			return getEmail().equals(((User) rhs).getEmail());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getEmail().hashCode();
	}
}