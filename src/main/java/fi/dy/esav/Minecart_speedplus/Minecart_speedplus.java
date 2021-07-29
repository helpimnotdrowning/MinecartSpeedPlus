package fi.dy.esav.Minecart_speedplus;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Minecart_speedplus extends JavaPlugin {
  Logger log = Logger.getLogger("Minecraft");
  
  private final Minecart_speedplusVehicleListener VehicleListener = new Minecart_speedplusVehicleListener(this);
  
  private final Minecart_speedplusSignListener SignListener = new Minecart_speedplusSignListener(this);
  
  static double speedmultiplier = 1.25D;
  
  boolean result;
  
  double multiplier;
  
  public static double getSpeedMultiplier() {
    return speedmultiplier;
  }
  
  public boolean setSpeedMultiplier(double multiplier) {
    if ((multiplier > 0.0D) & (multiplier <= 8.0D)) {
      speedmultiplier = multiplier;
      return true;
    } 
    return false;
  }
  
  public void onEnable() {
    this.log.info(getDescription().getName() + " version " + getDescription().getVersion() + " started.");
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(this.VehicleListener, (Plugin)this);
    pm.registerEvents(this.SignListener, (Plugin)this);
  }
  
  public void onDisable() {
    this.log.info(getDescription().getName() + " version " + getDescription().getVersion() + " stopped.");
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (cmd.getName().equalsIgnoreCase("msp")) {
      if (sender instanceof Player) {
        Player player = (Player)sender;
        if (!player.hasPermission("msp.cmd")) {
          player.sendMessage("You don't have permission to do that");
          return true;
        } 
      } 
      try {
        this.multiplier = Double.parseDouble(args[0]);
      } catch (Exception e) {
        sender.sendMessage(ChatColor.YELLOW + "Multipler should be any number between 0 and 4");
        return false;
      } 
      this.result = setSpeedMultiplier(this.multiplier);
      if (this.result) {
        sender.sendMessage(ChatColor.YELLOW + "Multiplier for new Minecarts set to " + this.multiplier);
        return true;
      } 
      sender.sendMessage(ChatColor.YELLOW + "Multipler should be any number between 0 and 4");
      return true;
    } 
    return false;
  }
}
