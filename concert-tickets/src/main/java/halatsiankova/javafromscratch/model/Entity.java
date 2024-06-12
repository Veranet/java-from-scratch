package halatsiankova.javafromscratch.model;

public interface Entity<ID extends Number> {
    ID getId();
    void setId(ID id);
}
