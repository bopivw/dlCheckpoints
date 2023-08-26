package com.halestudios.data.especial.guns;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandPermission("data.admin")
@CommandAlias("guns")
public class GunsCommands extends BaseCommand {

    @Subcommand("give")
    public void give(Player player){
        Pistol.getItemStack(player);
    }
}
