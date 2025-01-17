package Util;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;

import java.util.List;
import java.util.TimerTask;

public class Util {
    private static String embedTitle;
    private static String url;



    public static boolean hasRole(Member member, String role) {
        for (Role r : member.getRoles()) {
            if (r.getName().equals(role)){
                return true;
            }
        }
        return false;
    }


    public static void deleteMessage(TextChannel channel, String message){
        List<Message> mes = new MessageHistory(channel).retrievePast(10).complete();
        for (Message m : mes){
            if (m.getContentRaw().contains(message)){
                m.delete().queue();
            }
        }
    }


    public static MessageEmbed createMessage(String title, String description){
        return new MessageEmbed(null, title, description, null, null, 0xF40C0C, null,null,null,null,null,null,null);
    }

    public static MessageEmbed sendEmbed(String description){
        var embed = new MessageEmbed(null, embedTitle, description, null, null, 0xF40C0C, null,null,null,null,null, setImageInfo(url),null);
        embedTitle = "";
        url = "";
        return embed;
    }

    public static void setTitle(String title){
        embedTitle = title;
    }

    public static MessageEmbed.ImageInfo setImageInfo(String imageUrl) {
        url = imageUrl;
        return new MessageEmbed.ImageInfo(url, "", 100, 100);
    }

    public static void setChannelName(Guild event, String channelID){

        var channel = event.getGuildChannelById(channelID);
        var whiteListByLevel = event.getMembersWithRoles(event.getRoleById(925558862478717058L)).size();
        var whiteListByInvite = event.getMembersWithRoles(event.getRoleById(934762373129072661L)).size();
        var waiters = event.getMembersWithRoles(event.getRoleById(937477857591054397L)).size();
        var size = whiteListByInvite + whiteListByLevel;

        if (channelID.equals("933518711262953542")){
            channel.getManager().setName("⚪・WL " + size + "/400").queue();
        }else if (channelID.equals("937484977438871552")){
            channel.getManager().setName("⚪・Waiters: " + waiters).queue();
        }
    }

    public static void setAmountOnline(Guild event){
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                var counter = 0;
                var channel = event.getGuildChannelById(935215569358164028L);

                for (var memb : event.getMembers()) {
                    if (memb.getOnlineStatus() != OnlineStatus.OFFLINE) {
                        counter++;
                    }
                }
                channel.getManager().setName("\uD83D\uDC65・Online: " + counter).queue();
            }
        }, 10 * 1000 * 60);
        System.out.println("Online counter has been set!");
    }

}