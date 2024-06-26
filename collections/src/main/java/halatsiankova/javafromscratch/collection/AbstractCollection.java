package halatsiankova.javafromscratch.collection;

public abstract class AbstractCollection<T> implements Collection<T> {
    /**
     * Return a string representation of the object
     *
     * @return a string representation of the object
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        final int size = this.size();
        int index = 0;
        for (final T entry : this) {
            if (entry == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(entry);
            }
            index++;
            if (index != size) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * Return the hash code value for this list.
     *
     * @return the hash code value for this list
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (T obj : this) {
            if (obj != null)
                hash += obj.hashCode();
        }
        return hash;
    }
}
