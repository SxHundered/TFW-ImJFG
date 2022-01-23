package com.tfw.manager.team.kits;

import com.tfw.configuration.ConfigFile;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.ITeam;
import com.tfw.manager.team.kits.items.CustomItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

@RequiredArgsConstructor@Data
public class Kit implements IKIT {

    private final ITeam team;
    private Set<CustomItem> armors;
    private Set<CustomItem> contents;

    //fighter_equipments
    @Override
    public void loadKits(ConfigFile teamConfig, String path, String teamName) {
        //TODO
        path = path + ".fighter_equipments";
        ConfigurationSection configurationSection = teamConfig.getYaml().getConfigurationSection(path);

        for (String key : configurationSection.getKeys(false)) {
            //armors , contents
            switch (key.toUpperCase(Locale.ROOT)) {
                case "ARMORS":
                    String armorPath = path + "." + key;
                    ConfigurationSection armorSection = teamConfig.getYaml().getConfigurationSection(armorPath);

                    armors = new HashSet<>();

                    for (String armor : armorSection.getKeys(false)) {
                        CustomItem customItem = new CustomItem(armor, teamConfig.getString(armorPath + "." + armor + ".name").replace("%team_name%", teamName)
                                , Material.getMaterial(teamConfig.getString(armorPath + "." + armor + ".type"))
                                ,1
                                , teamConfig.getBoolean(armorPath + "." + armor + ".glow")
                                ,-1
                                ,0);
                        System.out.println(Material.getMaterial(teamConfig.getString(armorPath + "." + armor + ".type")).name().toUpperCase(Locale.ROOT));

                        if (teamConfig.getYaml().getStringList(armorPath + "." + armor + ".enchantments") != null)
                            for (String enchant : teamConfig.getYaml().getStringList(armorPath + "." + armor + ".enchantments"))
                                customItem.getEnchantments().add(enchant);
                        if (teamConfig.getYaml().getStringList(armorPath + "." + armor + ".lore") != null)
                            for (String enchant : teamConfig.getYaml().getStringList(armorPath + "." + armor + ".lore"))
                                customItem.getLores().add(enchant);

                        customItem.setItemStack(customItem.generateItemStack());
                        armors.add(customItem);
                    }

                    break;
                case "CONTENTS":
                    String contentPath = path + "." + key;
                    ConfigurationSection contentSection = teamConfig.getYaml().getConfigurationSection(contentPath);

                    contents = new HashSet<>();

                    for (String content : contentSection.getKeys(false)) {
                        CustomItem customItem = new CustomItem(content, teamConfig.getString(contentPath + "." + content + ".name").replace("%team_name%", teamName)
                                , Material.getMaterial(teamConfig.getString(contentPath + "." + content + ".type"))
                                ,teamConfig.getInt(contentPath + "." + content + ".amount")
                                ,teamConfig.getBoolean(contentPath + "." + content + ".glow")
                                ,teamConfig.getInt(contentPath + "." + content + ".slot")
                                ,teamConfig.getInt(contentPath + "." + content + ".durability"));

                        if (teamConfig.getYaml().getStringList(contentPath + "." + content + ".enchantments") != null)
                            for (String enchant : teamConfig.getYaml().getStringList(contentPath + "." + content + ".enchantments"))
                                customItem.getEnchantments().add(enchant);
                        if (teamConfig.getYaml().getStringList(contentPath + "." + content + ".lore") != null)
                            for (String enchant : teamConfig.getYaml().getStringList(contentPath + "." + content + ".lore"))
                                customItem.getLores().add(enchant);

                        customItem.setItemStack(customItem.generateItemStack());
                        contents.add(customItem);
                    }
                    break;
            }
        }
    }

    @Override
    public void giveKitAll() {
        for (PlayerData playerData : team.alive_members())
            giveKit(playerData);
    }

    @Override
    public void giveKit(PlayerData playerData) {
        for (CustomItem customItem : contents)
            if (customItem != null)
                if (playerData.isOnline())
                    playerData.getPlayer().getInventory().setItem(customItem.getSlot(), customItem.getItemStack());

        for (CustomItem customItem : armors)
            if (customItem != null)
                if (playerData.isOnline())
                    switch (customItem.getName().toLowerCase(Locale.ROOT)) {
                        case "helmet":
                            playerData.getPlayer().getInventory().setHelmet(customItem.getItemStack());
                            break;
                        case "chestplate":
                            playerData.getPlayer().getInventory().setChestplate(customItem.getItemStack());
                            break;
                        case "leggings":
                            playerData.getPlayer().getInventory().setLeggings(customItem.getItemStack());
                            break;
                        case "boots":
                            playerData.getPlayer().getInventory().setBoots(customItem.getItemStack());
                            break;
                    }
        playerData.textPlayer("%prefix% &eYou have received your kit successfully!");
    }


    @Override
    public Set<CustomItem> getArmors() {
        return armors;
    }

    @Override
    public Set<CustomItem> getContents() {
        return contents;
    }
}
