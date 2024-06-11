package halatsiankova.javafromscratch.lesson2.model;

public interface Printable {
    default void print() {
        System.out.println("print content in console");
    }
}
