package cc.wdcy.domain.dto;

import java.io.Serializable;

import cc.wdcy.domain.user.User;

public class UserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
    private String password;

    private String phone;
    private String email;
    
    
    public User createDomain() {
		User user = new User().username(username).password(password)
				.email(email).phone(phone);
    	return user;
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
