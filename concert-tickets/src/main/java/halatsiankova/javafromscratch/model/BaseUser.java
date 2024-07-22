package halatsiankova.javafromscratch.model;

import halatsiankova.javafromscratch.enumerated.Role;
import halatsiankova.javafromscratch.enumerated.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @OneToMany(mappedBy = "userId", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<Ticket> tickets;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "status")
    private Status status;

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

    public BaseUser(Integer id, String name, LocalDateTime createDate, Status status, Set<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.status = status;
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

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        if (tickets == null) {
            return;
        }
        if (this.tickets != null) {
            this.tickets.addAll(tickets);
        } else {
            this.tickets = new HashSet<>(tickets);
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
                && Objects.equals(tickets, baseUser.tickets)
                && status == baseUser.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, name, createDate, tickets, status);
    }

    @Override
    public String toString() {
        return "BaseUser{" +
                "id=" + id +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", tickets=" + tickets +
                ", status=" + status +
                '}';
    }
}
