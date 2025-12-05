package valorless.packstatus;

import valorless.valorlessutils.ValorlessUtils.Log;
import valorless.valorlessutils.config.Config;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	public static JavaPlugin plugin;
	public static Config config;
	public static int delay = 20;
	
	protected static PlaceholderAPI papi;
    
    public String[] commands = {
    		"packstatus", 
    		"packstatus reload"
    };
	
	public void onLoad() {
		plugin = this;
		config = new Config(this, "config.yml");
	}
	
	@Override
    public void onEnable() {
		Log.Debug(plugin, "Debugging Enabled!");
		try {
			getServer().getPluginManager().registerEvents(new PackListener(), this);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		papi = new PlaceholderAPI();
		papi.register();
		
		delay = config.GetInt("delay");
		
		RegisterCommands();
    }
    
    public void RegisterCommands() {
    	for (int i = 0; i < commands.length; i++) {
    		getCommand(commands[i]).setExecutor(new CommandListener());
    	}
    }
 
}
