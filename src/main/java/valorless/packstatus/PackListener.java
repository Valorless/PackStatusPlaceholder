package valorless.packstatus;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import valorless.valorlessutils.ValorlessUtils.Log;

public class PackListener implements Listener {
	
	public static HashMap<Player, Boolean> isLoaded = new HashMap<Player, Boolean>();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerUpdatesPackStatus(PlayerResourcePackStatusEvent event) {
        PlayerResourcePackStatusEvent.Status status = event.getStatus();
        switch (status) {
        	case ACCEPTED :
            	if(Main.config.HasKey("log") && Main.config.GetBool("log")){
            		Log.Info(Main.plugin, String.format("%s Pack Accepted", event.getPlayer().getName()));
            	}
            	for(String acmd : Main.config.GetStringList("commands.accepted")) {
                	acmd = acmd.replace("%username%", event.getPlayer().getName());
                	acmd = acmd.replace("%uuid%", event.getPlayer().getUniqueId().toString());
                	SendCommand(acmd, event.getPlayer());
            	}
            	isLoaded.put(event.getPlayer(), false);
            	break;
            case DECLINED :
            	if(Main.config.HasKey("log") && Main.config.GetBool("log")){
            		Log.Info(Main.plugin, String.format("%s Pack Declined", event.getPlayer().getName()));
            	}
            	for(String acmd : Main.config.GetStringList("commands.declined")) {
                	acmd = acmd.replace("%username%", event.getPlayer().getName());
                	acmd = acmd.replace("%uuid%", event.getPlayer().getUniqueId().toString());
                	SendCommand(acmd, event.getPlayer());
            	}
            	isLoaded.put(event.getPlayer(), false);
            	break;
            case FAILED_DOWNLOAD :
            	if(Main.config.HasKey("log") && Main.config.GetBool("log")){
            		Log.Info(Main.plugin, String.format("%s Pack Failed Download", event.getPlayer().getName()));
            	}
            	for(String acmd : Main.config.GetStringList("commands.failed")) {
                	acmd = acmd.replace("%username%", event.getPlayer().getName());
                	acmd = acmd.replace("%uuid%", event.getPlayer().getUniqueId().toString());
                	SendCommand(acmd, event.getPlayer());
            	}
            	isLoaded.put(event.getPlayer(), false);
            	break;
            case SUCCESSFULLY_LOADED :
            	if(Main.config.HasKey("log") && Main.config.GetBool("log")){
            		Log.Info(Main.plugin, String.format("%s Pack Loaded", event.getPlayer().getName()));
            	}
            	for(String cmd : Main.config.GetStringList("commands.loaded")) {
                	cmd = cmd.replace("%username%", event.getPlayer().getName());
                	cmd = cmd.replace("%uuid%", event.getPlayer().getUniqueId().toString());
                	SendCommand(cmd, event.getPlayer());
            	}
            	isLoaded.put(event.getPlayer(), true);
            	break;

            // 1.20.3+ only. switch statement should never reach this however on lower versions
            case DOWNLOADED :
            	if(Main.config.HasKey("log") && Main.config.GetBool("log")){
            		Log.Info(Main.plugin, String.format("%s Pack Downloaded", event.getPlayer().getName()));
            	}
            	for(String acmd : Main.config.GetStringList("commands.downloaded")) {
                	acmd = acmd.replace("%username%", event.getPlayer().getName());
                	acmd = acmd.replace("%uuid%", event.getPlayer().getUniqueId().toString());
                	SendCommand(acmd, event.getPlayer());
            	}
            	isLoaded.put(event.getPlayer(), false);
            	break;
            case INVALID_URL :
            	if(Main.config.HasKey("log") && Main.config.GetBool("log")){
            		Log.Info(Main.plugin, "Pack Invalid URL");
            	}
            	for(String acmd : Main.config.GetStringList("commands.invalid-url")) {
                	acmd = acmd.replace("%username%", event.getPlayer().getName());
                	acmd = acmd.replace("%uuid%", event.getPlayer().getUniqueId().toString());
                	SendCommand(acmd, event.getPlayer());
            	}
            	isLoaded.put(event.getPlayer(), false);
            	break;
            case FAILED_RELOAD :
            	if(Main.config.HasKey("log") && Main.config.GetBool("log")){
            		Log.Info(Main.plugin, String.format("%s Pack Failed Reload", event.getPlayer().getName()));
            	}
            	for(String acmd : Main.config.GetStringList("commands.reload-failed")) {
                	acmd = acmd.replace("%username%", event.getPlayer().getName());
                	acmd = acmd.replace("%uuid%", event.getPlayer().getUniqueId().toString());
                	SendCommand(acmd, event.getPlayer());
            	}
            	isLoaded.put(event.getPlayer(), false);
            	break;
            case DISCARDED : 
            	if(Main.config.HasKey("log") && Main.config.GetBool("log")){
            		Log.Info(Main.plugin, String.format("%s Pack Discarded", event.getPlayer().getName()));
            	}
            	for(String acmd : Main.config.GetStringList("commands.discarded")) {
                	acmd = acmd.replace("%username%", event.getPlayer().getName());
                	acmd = acmd.replace("%uuid%", event.getPlayer().getUniqueId().toString());
                	SendCommand(acmd, event.getPlayer());
            	}
            	isLoaded.put(event.getPlayer(), false);
            	break;
        };
    }
    
    void SendCommand(String cmd, Player player) {

    	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run(){
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(cmd, player.getName()));
            }
        }, Main.delay);
    }
    
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
    	for(String cmd : Main.config.GetStringList("commands.join")) {
        	cmd = cmd.replace("%username%", event.getPlayer().getName());
        	cmd = cmd.replace("%uuid%", event.getPlayer().getUniqueId().toString());
        	SendCommand(cmd, event.getPlayer());
    	}
    	
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
    	for(String cmd : Main.config.GetStringList("commands.quit")) {
        	cmd = cmd.replace("%username%", event.getPlayer().getName());
        	cmd = cmd.replace("%uuid%", event.getPlayer().getUniqueId().toString());
        	SendCommand(cmd, event.getPlayer());
    	}
    }
}
