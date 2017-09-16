package com.thesopfactory.thesopmaker.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
Author- Nikhil
Date - Jul 29, 2017
*/
@Entity
@Table( name = "users" )
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column( unique=true, nullable=false )
	private String username;
	
	private String firstname;
	
	private String lastname;
	
	private String password;
	
	@Transient
	private String password2;
	
	@Column( name="email_id" )
	private String emailId;
	
	private boolean enabled;
	
	@ElementCollection( fetch=FetchType.EAGER)
	@CollectionTable( name="authorities", joinColumns=@JoinColumn( name="user_id" ) )
	@Column( name = "role" )
	private Set<String> roles;

	public long getId() {	return id;		}

	public void setId(long id) {	this.id = id;	}

	public String getFirstname() {	 return firstname;	  }

	public void setFirstname(String firstname) {	this.firstname = firstname;		}

	public String getLastname() {	 return lastname;  	 }

	public void setLastname(String lastname) {	  this.lastname = lastname;		}

	public String getEmailId() {	return emailId;		}

	public void setEmailId(String emailId) {	this.emailId = emailId;		}

	public String getPassword2() {	  return password2;	   }

	public void setPassword2(String password2) {	this.password2 = password2;	   }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		for ( String role : roles ) 
			authorities.add( new SimpleGrantedAuthority( role ) );
		return authorities;
	}

	@Override
	public String getPassword() {	 return password;	 }

	public void setPassword(String password) {	  this.password = password;	 }
	
	@Override
	public String getUsername() {	 return username;	 }
	
	public void setUsername(String username) {	  this.username = username;	   }

	@Override
	public boolean isEnabled() {	 return enabled;	  }
	
	public void setEnabled(boolean enabled) {	  this.enabled = enabled;	 }

	@Override
	public boolean isAccountNonExpired() {	  return true;	   }

	@Override
	public boolean isAccountNonLocked() {	 return true;	 }

	@Override
	public boolean isCredentialsNonExpired() {	  return true;	  }
	
	public Set<String> getRoles() {    return roles;	}

	public void setRoles(Set<String> roles) {	 this.roles = roles;	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", password=" + password + ", emailId=" + emailId + ", enabled=" + enabled + "]";
	}

}
