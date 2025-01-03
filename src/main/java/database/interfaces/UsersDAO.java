package database.interfaces;

import database.Users;
import java.util.List;

public interface UsersDAO {
//    List<Users> getAllUsers();
//
//    Users findUserById(int userId);
//
//    void saveUser(Users user);
//
//    void updateUser(Users user);
//
//    void deleteUser(Users user);
//
//    List<Users> getUsersByRole(String roleName);
    Users findByEmail(String email);
}
