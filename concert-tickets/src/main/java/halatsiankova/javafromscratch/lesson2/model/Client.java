package halatsiankova.javafromscratch.lesson2.model;

import halatsiankova.javafromscratch.lesson2.enumerated.Role;

public class Client extends BaseUser {
    public Client() {
        super(Role.CLIENT);
    }
}
