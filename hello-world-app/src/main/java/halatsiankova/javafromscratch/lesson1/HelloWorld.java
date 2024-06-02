package halatsiankova.javafromscratch.lesson1;

public class HelloWorld {

    /**
     * Print the welcome phrase in the console with the username taken from the parameters
     * @param args - username
     */
    public static void main(String[] args) {
        var helloWorld = new HelloWorld();
        var name = helloWorld.isParamPresent(args) ? args[0] : "World";
        var welcomePhrase = helloWorld.sayHello(name);
        System.out.println(welcomePhrase);
    }

    private String sayHello(String name) {
        return String.format("Hello %s!", name);
    }

    private boolean isParamPresent(String[] args) {
        return args != null && args.length != 0;
    }
}
