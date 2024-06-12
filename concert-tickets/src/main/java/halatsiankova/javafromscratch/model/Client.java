package halatsiankova.javafromscratch.model;

import halatsiankova.javafromscratch.enumerated.Role;

public class Client extends BaseUser {
    public Client() {
        super(Role.CLIENT);
    }
}
