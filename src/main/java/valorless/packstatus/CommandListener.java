package valorless.packstatus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import valorless.valorlessutils.ValorlessUtils.*;

public class CommandListener implements CommandExecutor {
	String Name = "§7[§6PackStatus§7]§r";
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	Log.Debug(Main.plugin, "Sender: " + sender.getName());
    	Log.Debug(Main.plugin, "Command: " + command.toString());
    	Log.Debug(Main.plugin, "Label: " + label);
    	for(int i = 0; i < args.length; i++) {
			Log.Debug(Main.plugin, "Arg " + i + ": " + args[i]);
		}
    	
		if (args.length >= 1){
			try {
				if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("packstatus.reload")) {
					Main.config.Reload();
					sender.sendMessage(Name +" §aReloaded.");
					Log.Info(Main.plugin, Name + " §aReloaded!");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

}
