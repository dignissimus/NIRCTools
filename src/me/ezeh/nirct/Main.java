package me.ezeh.nirct;

public class Main {
    public static void main(String[] args) {
        String ip = "127.0.0.1";
        String channel = "#test";
        int port = 6667;
        final IrcClient client = new IrcClient(new IrcConnection(ip, port));
        client.addListener(new LetterCounter());
        client.setLog(true);
        client.start();
        client.setNick("eCountear");
        client.setUser("eCounter 8 * :eCounter@NIRCTools");
        client.addLoginHook(new Runnable() {
            @Override
            public void run() {
                client.joinChannel(channel);

                client.sendMessage("Hi! I am a test IRC bot, created by spammy23 I count the number of times people have used he letter 'E'! But don't worry I don't take logs!", channel);
                client.sendMessage("If you want to take a look at the statistics, just use '/privmsg eCounter'", channel);
                client.me("wants people to use the letter 'E'", channel);
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                client.command("QUIT", "eCounter@SpiderBot Shutting down... IRCUL8r");
                System.out.println("Said bye!");
            }
        }));
    }
}
