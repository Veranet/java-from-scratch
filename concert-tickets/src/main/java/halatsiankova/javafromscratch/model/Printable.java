package halatsiankova.javafromscratch.model;

public interface Printable {
    default String print() {
        return "print content in console";
    }
}
