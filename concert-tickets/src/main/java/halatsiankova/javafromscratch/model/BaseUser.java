package halatsiankova.javafromscratch.model;

import halatsiankova.javafromscratch.enumerated.Role;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class BaseUser implements User {
    private Integer id;
    private final Role role;
    private String name;
    private LocalDateTime createDate;

    protected BaseUser(Integer id, Role role) {
        this.id = id;
        this.role = role;
    }

    protected BaseUser(Role role) {
        this.role = role;
    }

    public BaseUser(Integer id, Role role, String name, LocalDateTime createDate) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.createDate = createDate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "BaseUser{" +
                "id=" + id +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUser user = (BaseUser) o;
        return Objects.equals(id, user.id)
                && role == user.role
                && Objects.equals(name, user.name)
                && Objects.equals(createDate, user.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, name, createDate);
    }
}
