package core;

import users.Employee;

public interface IMessageSender {
    void sendMessage(Employee receiver, String content);
}
