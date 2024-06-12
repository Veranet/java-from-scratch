package halatsiankova.javafromscratch.util;

public class TicketsGeneratorUtil {
    private TicketsGeneratorUtil() {}

    public static String generateTicketId(int order) {
        return Integer.toHexString(order);
    }
}
