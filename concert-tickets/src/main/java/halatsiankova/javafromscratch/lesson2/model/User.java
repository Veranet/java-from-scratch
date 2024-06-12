package halatsiankova.javafromscratch.lesson2.model;

public interface User extends Printable, Entity<Integer> {
    void printRole();
}
