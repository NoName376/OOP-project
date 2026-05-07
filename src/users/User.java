package users;

import core.IAuthenticatable;
import core.IIdentifiable;
import core.ILoggable;
import java.io.Serializable;

public abstract class User implements IIdentifiable, ILoggable, IAuthenticatable, Serializable {
	public User(String id, String username, String passwordHash, String firstName, String lastName, String email) {
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean login(String credentials) {
		return passwordHash.equals(credentials);
	}

	@Override
	public void logout() {
	}

	@Override
	public String getLogDetails() {
		return "User: " + getFullName() + " (ID: " + id + ")";
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	protected String id;
	protected String username;
	protected String passwordHash;
	protected String firstName;
	protected String lastName;
	protected String email;
}
