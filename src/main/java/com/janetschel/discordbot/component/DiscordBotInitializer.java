package com.janetschel.discordbot.component;

import com.janetschel.discordbot.event.GuildMemeberJoinEvent;
import com.janetschel.discordbot.event.MessageReceivedEvent;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;

@Component
public class DiscordBotInitializer {

    @Value("${bot.token}")
    private String token;

    @PostConstruct @SneakyThrows(LoginException.class)
    private void initializeDiscordBot() {
        JDA jda = JDABuilder
                .createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .setAutoReconnect(true)
                .build();

        jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        jda.getPresence().setActivity(Activity.listening("euch ganz genau"));

        jda.addEventListener(new GuildMemeberJoinEvent());
        jda.addEventListener(new MessageReceivedEvent());
    }
}
