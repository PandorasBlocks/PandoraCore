package me.DawnBudgie.PandoraCore;

import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PandoraCore
  extends JavaPlugin
  implements Listener
{
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
    getLogger().info(" has been enabled.");
    if (!new File(getDataFolder(), "config.yml").exists()) {
      saveResource("config.yml", false);
    }
  }
  
  public void onDisable()
  {
    getLogger().info(" has been disabled.");
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e)
  {
    String joinMsg = getConfig().getString("join");
    joinMsg = joinMsg.replaceAll("%player%", e.getPlayer().getDisplayName());
    e.setJoinMessage(ColourUtils.format(joinMsg));
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e)
  {
    String quitMsg = getConfig().getString("quit");
    quitMsg = quitMsg.replace("%player%", e.getPlayer().getDisplayName());
    e.setQuitMessage(ColourUtils.format(quitMsg));
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("pcr")) {
      if ((sender instanceof Player)) {
        if ((sender.hasPermission("pc.reload")) || (sender.isOp()))
        {
          saveResource("config.yml", false);
          reloadConfig();
          ((Player)sender).sendMessage("&4PandoraCore config reloaded!");
          System.out.print("Reloaded the PandoraCore config.");
        }
      }
    }
    if (cmd.getName().equalsIgnoreCase("pcs")) {
      if ((sender instanceof Player)) {
        if ((sender.hasPermission("pc.save")) || (sender.isOp()))
        {
          saveResource("config.yml", false);
          ((Player)sender).sendMessage("&4PandoraCore config saved!");
          System.out.print("Saved the PandoraCore config.");
        }
      }
    }
    return true;
  }
  
  public static enum ColourUtils
  {
    BLACK("&0", ChatColor.BLACK.toString()),  DARK_BLUE("&1", ChatColor.DARK_BLUE.toString()),  DARK_GREEN("&2", ChatColor.DARK_GREEN.toString()),  DARK_AQUA("&3", ChatColor.DARK_AQUA.toString()),  DARK_RED("&4", ChatColor.DARK_RED.toString()),  DARK_PURPLE("&5", ChatColor.DARK_PURPLE.toString()),  GOLD("&6", ChatColor.GOLD.toString()),  GRAY("&7", ChatColor.GRAY.toString()),  DARK_GRAY("&8", ChatColor.DARK_GRAY.toString()),  BLUE("&9", ChatColor.BLUE.toString()),  GREEN("&a", ChatColor.GREEN.toString()),  AQUA("&b", ChatColor.AQUA.toString()),  RED("&c", ChatColor.RED.toString()),  LIGHT_PURPLE("&d", ChatColor.LIGHT_PURPLE.toString()),  YELLOW("&e", ChatColor.YELLOW.toString()),  WHITE("&f", ChatColor.WHITE.toString()),  MAGIC("&k", ChatColor.MAGIC.toString()),  BOLD("&l", ChatColor.BOLD.toString()),  STRIKETHROUGH("&m", ChatColor.STRIKETHROUGH.toString()),  UNDERLINE("&n", ChatColor.UNDERLINE.toString()),  ITALIC("&o", ChatColor.ITALIC.toString()),  RESET("&r", ChatColor.RESET.toString());
    
    private final String input;
    private final String MinecraftColor;
    
    private ColourUtils(String input, String MinecraftColor)
    {
      this.input = input;
      this.MinecraftColor = MinecraftColor;
    }
    
    public String getMinecraftColor()
    {
      return this.MinecraftColor;
    }
    
    public String getInput()
    {
      return this.input;
    }
    
    public static String format(String message)
    {
      String msg = message;
      for (ColourUtils c : values()) {
        msg = msg.replace(c.getInput(), c.getMinecraftColor());
      }
      return msg;
    }
  }
}
