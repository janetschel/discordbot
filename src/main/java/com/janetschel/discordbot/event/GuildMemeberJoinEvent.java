package com.janetschel.discordbot.event;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class GuildMemeberJoinEvent extends ListenerAdapter {
    public static final String GROUP_ID = "763056736055590983";

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Member newMemeber = event.getMember();
        Role roleToAdd = Objects.requireNonNull(event.getGuild().getRoleById(GROUP_ID));

        event.getGuild().addRoleToMember(newMemeber, roleToAdd).complete();
    }
}
