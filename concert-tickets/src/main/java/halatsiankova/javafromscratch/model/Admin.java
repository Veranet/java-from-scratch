package halatsiankova.javafromscratch.model;

import halatsiankova.javafromscratch.enumerated.Role;

public class Admin extends BaseUser {
    public Admin() {
        super(Role.ADMIN);
    }
}
