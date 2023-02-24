package dev.javarush.todolist.middleware;

import dev.javarush.todolist.command.Command;

public abstract class Middleware {
    private Middleware next;

    public static Middleware link(Middleware first, Middleware... chain) {
        Middleware head = first;
        for (Middleware nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean check(Command command);

    protected boolean checkNext(Command command) {
        if (next == null) {
            return true;
        }
        return next.check(command);
    }
}