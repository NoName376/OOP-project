package core;

public interface IAuthenticatable {
    boolean login(String credentials);
    void logout();
}
