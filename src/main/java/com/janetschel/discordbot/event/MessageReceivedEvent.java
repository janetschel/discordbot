package com.janetschel.discordbot.event;

import com.janetschel.discordbot.enums.Commands;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class MessageReceivedEvent extends ListenerAdapter {
    private static final String CHANNEL_ID = "763660519940685864";
    private static final String GROUP_ID = "763669126379077642";
    private static final String BOT_ID = "763304085671247882";

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String channelIdOfChannel = String.valueOf(event.getChannel().getIdLong());
        String idOfMember = String.valueOf(Objects.requireNonNull(event.getMember()).getIdLong());

        // we only listen on one specific channel (#bot-commands) and only for messages not sent by the bot
        if (!CHANNEL_ID.equals(channelIdOfChannel) || BOT_ID.equals(idOfMember)) {
            return;
        }

        Guild guild = event.getGuild();

        // message is in form of /[(mute all)|(unmute all)]
        final String command = event.getMessage().getContentRaw();
        Role mutedRole = Objects.requireNonNull(guild.getRoleById(GROUP_ID));
        List<Member> members = guild.getMembers();

        if (Commands.MUTE.getCommandName().equals(command)) {
            addMutedRoleToAllMembers(idOfMember, guild, mutedRole, members);
        } else if (Commands.UNMUTE.getCommandName().equals(command)) {
            members.forEach(member -> guild.removeRoleFromMember(member, mutedRole).complete());
        } else {
            String errorMessage = String.format("Unkown command: %s", command);
            event.getChannel().sendMessage(errorMessage).complete();
        }
    }

    private void addMutedRoleToAllMembers(String idOfMember, Guild guild, Role mutedRole, List<Member> members) {
        members.forEach(member -> {
            if (!String.valueOf(member.getIdLong()).equals(idOfMember)) {
                guild.addRoleToMember(member, mutedRole).complete();
            }
        });
    }
}
