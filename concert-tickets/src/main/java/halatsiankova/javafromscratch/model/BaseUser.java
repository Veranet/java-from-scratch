package halatsiankova.javafromscratch.model;

import halatsiankova.javafromscratch.enumerated.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.util.List;
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
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;

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

    public BaseUser(Integer id, String name, LocalDateTime createDate, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.tickets = tickets;
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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUser baseUser = (BaseUser) o;
        return Objects.equals(id, baseUser.id)
                && role == baseUser.role
                && Objects.equals(name, baseUser.name)
                && Objects.equals(createDate, baseUser.createDate)
                && Objects.equals(tickets, baseUser.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, name, createDate, tickets);
    }

    @Override
    public String toString() {
        return "BaseUser{" +
                "id=" + id +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", tickets=" + tickets +
                '}';
    }
}
