package halatsiankova.javafromscratch.model;

import halatsiankova.javafromscratch.enumerated.Role;

import java.time.LocalDateTime;

public class Client extends BaseUser {
    public Client() {
        super(Role.CLIENT);
    }

    public Client(Integer id, Role role, String name, LocalDateTime createDate) {
        super(id, role, name, createDate);
    }
}
