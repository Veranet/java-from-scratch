package halatsiankova.javafromscratch.lesson2.model;

public interface Entity<ID extends Number> {
    ID getId();
    void setId(ID id);
}
