package halatsiankova.javafromscratch.service;

import halatsiankova.javafromscratch.model.BaseUser;
import halatsiankova.javafromscratch.model.Ticket;
import halatsiankova.javafromscratch.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final boolean updateEnabled;

    public UserService(UserRepository userRepository, @Value("${service.update-enabled:true}") boolean updateEnabled) {
        this.userRepository = userRepository;
        this.updateEnabled = updateEnabled;
    }

    public void add(BaseUser user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null.");
        }
        userRepository.save(user);
    }

    public BaseUser getUserById(int userId) {
        checkUserId(userId);
        Optional<BaseUser> user = userRepository.findById(userId);
        return user.orElseThrow(
                () -> new IllegalArgumentException(String.format("User with ID = %d does not exist.", userId)));
    }

    /**
     * Delete a user by their ID along with their associated tickets and role.
     *
     * @param userId the ID of the user to be deleted.
     * @throws IllegalArgumentException if the user with the specified ID was not deleted.
     * @throws RuntimeException         if an SQL error occurs during the deletion process.
     */
    public void deleteUserById(int userId) {
        checkUserId(userId);
        userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException(String.format("User with ID = %d does not exist.", userId)));
        userRepository.deleteById(userId);
    }

    /**
     * Update the user and create a ticket if the status is ACTIVATE and the update operation is enabled.
     *
     * @param userId user's ID that to be updated.
     * @param ticket ticket to be created.
     * @throws IllegalArgumentException if the user or ticket is null.
     * @throws UnsupportedOperationException if the update operation is disabled.
     */
    @Transactional
    public void updateUserAndSaveTickets(int userId, Ticket ticket) {
        checkUserId(userId);
        if(!updateEnabled) {
            throw new UnsupportedOperationException("Update not supported.");
        }
        if (ticket != null) {
            userRepository.updateBaseUserByIdAndTicket(userId, ticket.getType().name(), ticket.getCreatedDateTime());
        } else {
            throw new IllegalArgumentException("Ticket must not be null.");
        }
    }

    public static void checkUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must not be negative or equal to 0.");
        }
    }
}
