package halatsiankova.javafromscratch.model;

import halatsiankova.javafromscratch.enumerated.Role;

public abstract class BaseUser implements User {
    private Integer id;
    private final Role role;

    protected BaseUser(Integer id, Role role) {
        this.id = id;
        this.role = role;
    }

    protected BaseUser(Role role) {
        this.role = role;
    }

    @Override
    public void printRole() {
        System.out.println(role);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
