package me.ezeh.nirct;

import org.jetbrains.annotations.NotNull;


public class LetterCounter extends Listener {
    int count;
    @Override
    public void onChat(@NotNull ChatEvent event){
        if (!total(event))count(event);
    }
    private void count(ChatEvent e){
        int c = Strings.INSTANCE.countChar(e.getMessage().toLowerCase(), 'e');
        count += Strings.INSTANCE.countChar(e.getMessage(), 'e');
        System.out.println(e.getSender()+" said \""+e.getMessage()+"\" which used the letter 'e' "+c+" times");
    }
    private boolean total(ChatEvent e) {
        if (e.getReceiver().equalsIgnoreCase(e.getClient().getNick())){
            e.getClient().sendMessage("So far the letter 'e' has been said " + count + " " + (count == 1 ? "time" : "times") + " since I have joined this network/e", e.getSender());
            return true;
        }
        return false;
    }
}

