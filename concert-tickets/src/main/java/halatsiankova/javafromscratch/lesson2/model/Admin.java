package halatsiankova.javafromscratch.lesson2.model;

import halatsiankova.javafromscratch.lesson2.enumerated.Role;

public class Admin extends BaseUser {
    public Admin() {
        super(Role.ADMIN);
    }
}
