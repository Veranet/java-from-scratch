package halatsiankova.javafromscratch.lesson2.repository;

import halatsiankova.javafromscratch.lesson2.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserRepository implements Repository<User, Integer> {

    private final Map<Integer, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void saveAll(Collection<User> users) {
        this.users.putAll(
                users.stream()
                        .collect(Collectors.toMap(User::getId, Function.identity())));
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }
}
