package halatsiankova.javafromscratch.model;

import halatsiankova.javafromscratch.enumerated.Role;

import java.time.LocalDateTime;

public class Admin extends BaseUser {
    public Admin() {
        super(Role.ADMIN);
    }

    public Admin (Integer id,Role role, String name, LocalDateTime createDate) {
        super(id, Role.ADMIN, name, createDate);
    }
}
