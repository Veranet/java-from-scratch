package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.repository.UserRepositoryImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepositoryImpl userRepository;

    public UserService(UserRepositoryImpl userRepository) {
            this.userRepository = userRepository;
    }

    /**
     * Add a new user along with their role.
     * User can have role 'ADMIN' or 'CLIENT'.
     *
     * @param user the user to be added.
     * @throws RuntimeException if an SQL error occurs during the saving process.
     */
    public void add(BaseUser user) {
        if(user == null) {
            throw new IllegalArgumentException("User must not be null.");
        }
        try {
            userRepository.save(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BaseUser getUserById(int userId) {
        checkUserId(userId);
        Optional<BaseUser> user;
        try {
            user = userRepository.findById(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user.orElseThrow(
                () -> new IllegalArgumentException(String.format("User with ID = %d does not exist.", userId)));
    }

    public List<BaseUser> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete a user by their ID along with their associated tickets and role.
     *  @param userId the ID of the user to be deleted.
     *  @throws IllegalArgumentException if the user with the specified ID was not deleted.
     *  @throws RuntimeException if an SQL error occurs during the deletion process.
     */
    public void deleteUserById(int userId) {
        checkUserId(userId);
        try {
            if(!userRepository.deleteById(userId)) {
                throw new IllegalArgumentException(String.format("User with ID = %d was not deleted.", userId));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkUserId(int userId) {
        if(userId <= 0) {
            throw new IllegalArgumentException("User ID must not be negative or equal to 0.");
        }
    }
}
