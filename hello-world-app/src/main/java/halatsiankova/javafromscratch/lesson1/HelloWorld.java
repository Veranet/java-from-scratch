package halatsiankova.javafromscratch.lesson1;

public class HelloWorld {

    /**
     * Print the welcome phrase in the console with the username taken from the parameters
     * @param args - username
     */
    public static void main(String[] args) {
        var name = args[0];
        var welcomePhrase = new HelloWorld().sayHello(name);
        System.out.println(welcomePhrase);
    }

    private String sayHello(String name) {
        return String.format("Hello %s!", name);
    }
}
