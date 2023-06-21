/**
 * 
 */
package com.mytangibleproperty.rems.bo;

/**
 * @author Satbir Kaur
 *
 */
public class MyUserDetails // implements UserDetails
{
	private String username;

	public MyUserDetails()
	{
	}

	public MyUserDetails(String username)
	{
		this.username = username;
	}

	/*
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	 * return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")); }
	 * 
	 * @Override public String getPassword() { return "123"; }
	 * 
	 * @Override public String getUsername() { return username; }
	 * 
	 * @Override public boolean isAccountNonExpired() { // TODO Auto-generated
	 * method stub return false; }
	 * 
	 * @Override public boolean isAccountNonLocked() { // TODO Auto-generated method
	 * stub return false; }
	 * 
	 * @Override public boolean isCredentialsNonExpired() { // TODO Auto-generated
	 * method stub return false; }
	 * 
	 * @Override public boolean isEnabled() { // TODO Auto-generated method stub
	 * return false; }
	 */

}
