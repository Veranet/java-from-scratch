package halatsiankova.javafromscratch.util;

public class HexIdGeneratorUtil {
    private HexIdGeneratorUtil() {}

    public static String generateTicketId(int order) {
        return Integer.toHexString(order);
    }
}
