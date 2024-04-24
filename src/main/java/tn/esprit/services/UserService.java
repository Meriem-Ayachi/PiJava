package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.User;

import java.util.List;

public class UserService implements IService<User> {
    @Override
    public void add(User o) {
        // Implementation of adding user without returning an ID
    }

    @Override
    public void update(User o) {
        // Implementation of updating user
    }

    @Override
    public void delete(User o) {
        // Implementation of deleting user
    }

    @Override
    public List<User> getAll() {
        // Implementation of getting all users
        return null;
    }

    @Override
    public User getOne(int id) {
        // Implementation of getting one user by ID
        return null;
    }
}