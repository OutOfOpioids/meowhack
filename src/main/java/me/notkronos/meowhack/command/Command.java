package me.notkronos.meowhack.command;

import me.notkronos.meowhack.util.Wrapper;
import me.notkronos.meowhack.util.chat.MessageType;

import java.io.IOException;

public abstract class Command implements Wrapper {
    String name;
    String description;
    String[] alias;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Command(String name, String description, String[] alias) {
        this.name = name;
        this.description = name;
        this.alias = alias;
    }

    public abstract void onExecute(String[] args) throws IOException;

    public abstract String getUseCase();

    public abstract int getArgSize();

    public abstract String getName();
}
