package com.janetschel.discordbot.enums;


import lombok.Getter;

public enum Commands {
    MUTE("mute all"),
    UNMUTE("unmute all");

    @Getter
    private final String commandName;

    static final String PREFIX = "~";

    Commands(String commandName) {
        this.commandName = PREFIX + commandName;
    }
}
