package me.ezeh.nirct.examples;

import me.ezeh.nirct.Listener;
import me.ezeh.nirct.event.ChatEvent;
import me.ezeh.nirct.tools.Strings;
import org.jetbrains.annotations.NotNull;


public class LetterCounter extends Listener {
    private int count;
    private char letter = 'e';

    public LetterCounter(char letter) {
        this.letter = letter;
    }
    public LetterCounter() {

    }

    @Override
    public void onChat(@NotNull ChatEvent event) {
        if (!total(event)) count(event);
    }

    private void count(ChatEvent e) {
        int c = Strings.tools.countChar(e.getMessage().toLowerCase(), letter);
        count += Strings.tools.countChar(e.getMessage(), letter);
        System.out.println(e.getSender() + " said \"" + e.getMessage() + "\" which used the letter '" + letter + "' " + c + " times");
    }

    private boolean total(ChatEvent e) {
        if (e.getReceiver().equalsIgnoreCase(e.getClient().getNick())) {
            e.getClient().sendMessage("So far the letter '" + letter + "' has been said " + count + " " + (count == 1 ? "time" : "times") + " since I have joined this network/e", e.getSender());
            return true;
        }
        return false;
    }
}

