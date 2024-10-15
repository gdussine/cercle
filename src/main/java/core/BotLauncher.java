package core;

public class BotLauncher {



    public static void main(String[] args) {
        var configLoader = new BotConfigurationLoader();
        System.out.println(configLoader.loadDefault());
    }
}
