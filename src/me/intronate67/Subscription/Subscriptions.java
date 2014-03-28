package me.intronate67.Subscription;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class Subscriptions extends JavaPlugin{
	private static Subscriptions instance;
	Logger logger = this.getLogger();
	public long firstJoin = System.currentTimeMillis() + 2628000000L;
	public long firstMonth = System.currentTimeMillis() + 2628000000L;
	public long sixMonths = System.currentTimeMillis() + 15768000000L;
	public long twelveMonths = System.currentTimeMillis() + 31536000000L;
	public double version = 1.0;
	public String enabled = "Subscriptions v" + version + " has been enabled!";
	public String disabled = "Subscriptions v" + version + " has been disabled!";
	
	public void onEnable(){
		if(!new File(this.getDataFolder(), "config.yml").exists()){
			this.saveDefaultConfig();
		}
		this.getLogger().info(enabled);
	}
	public void onDisable(){
		this.getLogger().info(disabled);
		this.saveConfig();
	}
	public static Subscriptions get(){
		return instance;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		String p = event.getPlayer().getName();
		Player pu = event.getPlayer();
		if(pu.hasPlayedBefore()){
			if(!getConfig().contains("players." + p)){
				this.getConfig().set("players." + p, firstJoin);
				pu.sendMessage("test1");
			}else{
				if(System.currentTimeMillis() > getConfig().getLong("players." + p)){
                    			pu.setWhitelisted(false);
                			pu.kickPlayer("");
                    			pu.sendMessage("test2");
                		}
			}
		}else{
			this.getConfig().set("players." + p, firstJoin);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String Lable, String[] args){
		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("wl")){
			if(args.length < 1){
				player.sendMessage("Not enough arguments! Correct Usage: /wl <player> <amount>");
			}
			if(args.length == 2){
				if(args[1].equalsIgnoreCase("firstMonth")){
					//Setting value for config.yml
					this.getConfig().set("players." + args[0], firstMonth);
					player.sendMessage(ChatColor.BLUE + "[Subscriptions] " + ChatColor.GRAY + "You have whitelisted player: " + ChatColor.RED + args[0] + ChatColor.GRAY + " for: 1 Month.");
				}
				if(args[1].equalsIgnoreCase("sixMonths")){
					//Setting value for config.yml
					this.getConfig().set("players." + args[1] + ".", sixMonths);
					player.sendMessage(ChatColor.BLUE + "[Subscriptions] " + ChatColor.GRAY + "You have whitelisted player: " + ChatColor.RED + args[0] + ChatColor.GRAY + " for: 6 Months.");
				}
				if(args[1].equalsIgnoreCase("twelveMonths")){
					//Setting value for config.yml
					this.getConfig().set("players." + args[1] + ".", twelveMonths);
					player.sendMessage(ChatColor.BLUE + "[Subscriptions] " + ChatColor.GRAY + "You have whitelisted player: " + ChatColor.RED + args[0] + ChatColor.GRAY + " for: 12 Months.");
				}
				if(args[1].equalsIgnoreCase("lifetime")){
					
					player.sendMessage(ChatColor.BLUE + "[Subscriptions] " + ChatColor.GRAY + "You have whitelisted player: " + ChatColor.RED + args[0] + ChatColor.GRAY + " for: Eternity.");
				}
				if(args.length > 1){
					player.sendMessage("To many arugments! Correct usage: /wl <player> <amount>");
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("unsub")){
			if(args.length < 0){
				player.sendMessage(ChatColor.RED + "Please specify a player!");
			}
			if(args.length == 0){
				Bukkit.getOfflinePlayer(args[0]).setWhitelisted(false);
				player.sendMessage(ChatColor.BLUE + "[Subscriptions] " + ChatColor.GRAY + "You have sucessfully unsubscribed player: " + args[0]);
			}
		}
		return true;
	}
}
