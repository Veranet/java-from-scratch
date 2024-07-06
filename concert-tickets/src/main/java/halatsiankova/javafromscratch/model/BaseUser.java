package halatsiankova.javafromscratch.model;

import halatsiankova.javafromscratch.enumerated.Role;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class BaseUser implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Transient
    private Role role;
    @Column(name = "user_name")
    private String name;
    @Column(name = "creation_date")
    private LocalDateTime createDate;

    public BaseUser(Integer id, Role role) {
        this.id = id;
        this.role = role;
    }

    public BaseUser(Role role) {
        this.role = role;
    }

    public BaseUser(Integer id, Role role, String name, LocalDateTime createDate) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.createDate = createDate;
    }

    public BaseUser(Integer id, String name, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
    }

    public BaseUser() {}

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
