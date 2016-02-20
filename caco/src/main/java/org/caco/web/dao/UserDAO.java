package org.caco.web.dao;

import org.caco.web.domain.User;

public interface UserDAO {
	public void saveUser(final User user);
	public User getUser(final long id);
}
