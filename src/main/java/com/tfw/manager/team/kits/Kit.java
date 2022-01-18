package com.tfw.manager.team.kits;

import com.tfw.configuration.ConfigFile;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.ITeam;
import com.tfw.manager.team.kits.items.CustomItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Locale;

@RequiredArgsConstructor@Data
public class Kit implements IKIT{

    private final ITeam team;
    private CustomItem[] armors;
    private CustomItem[] contents;

    //fighter_equipments
    @Override
    public void loadKits(ConfigFile teamConfig, String path) {
        //TODO
        int index = 0;
        ConfigurationSection configurationSection = teamConfig.getYaml().getConfigurationSection(path);
        for (String key : configurationSection.getKeys(false)) {
            //armors , contents
            switch (key.toUpperCase(Locale.ROOT)){
                case "ARMORS":
                    String armorPath = path + "." + key + ".armors";
                    ConfigurationSection armorSection = teamConfig.getYaml().getConfigurationSection(armorPath);
                    index = 0;
                    armors = new CustomItem[armorSection.getKeys(false).size()];

                    for (String armor : armorSection.getKeys(false)) {
                        CustomItem customItem = new CustomItem(armor, teamConfig.getString(armorPath + "." + armor + ".name"), Material.getMaterial(teamConfig.getString(armorPath + "." + armor + ".type")), teamConfig.getInt(armorPath + "." + armor + ".amount"), teamConfig.getBoolean(armorPath + "." + armor + ".glow"), teamConfig.getInt(armorPath + "." + armor + ".slot"), teamConfig.getInt(armorPath + "." + armor + ".durability"));

                        if (teamConfig.getYaml().getStringList(armorPath + "." + armor + ".enchantments") != null)
                            for (String enchant : teamConfig.getYaml().getStringList(armorPath + "." + armor + ".enchantments"))
                                customItem.getEnchantments().add(enchant);
                        if (teamConfig.getYaml().getStringList(armorPath + "." + armor + ".lore") != null)
                            for (String enchant : teamConfig.getYaml().getStringList(armorPath + "." + armor + ".lore"))
                                customItem.getLores().add(enchant);

                        customItem.setItemStack(customItem.generateItemStack());
                        armors[index++] = customItem;
                    }

                    break;
                case "CONTENTS":
                    String contentPath = path + "." + key + ".contents";
                    ConfigurationSection contentSection = teamConfig.getYaml().getConfigurationSection(contentPath);

                    index = 0;
                    contents = new CustomItem[contentSection.getKeys(false).size()];

                    for (String content : contentSection.getKeys(false)) {
                        CustomItem customItem = new CustomItem(content, teamConfig.getString(contentPath + "." + content + ".name"), Material.getMaterial(teamConfig.getString(contentPath + "." + content + ".type")), teamConfig.getInt(contentPath + "." + content + ".amount"), teamConfig.getBoolean(contentPath + "." + content + ".glow"), teamConfig.getInt(contentPath + "." + content + ".slot"), teamConfig.getInt(contentPath + "." + content + ".durability"));

                        if (teamConfig.getYaml().getStringList(contentPath + "." + content + ".enchantments") != null)
                            for (String enchant : teamConfig.getYaml().getStringList(contentPath + "." + content + ".enchantments"))
                                customItem.getEnchantments().add(enchant);
                        if (teamConfig.getYaml().getStringList(contentPath + "." + content + ".lore") != null)
                            for (String enchant : teamConfig.getYaml().getStringList(contentPath + "." + content + ".lore"))
                                customItem.getLores().add(enchant);

                        customItem.setItemStack(customItem.generateItemStack());
                        contents[index++] = customItem;
                    }
                    break;
            }
        }
    }

    @Override
    public void giveKitAll() {
        for (CustomItem customItem : contents)
            for (PlayerData playerData : team.alive_members())
                if (playerData.isOnline())
                    playerData.getPlayer().getInventory().setItem(customItem.getSlot(), customItem.getItemStack());

        for (CustomItem customItem : armors)
            for (PlayerData playerData : team.alive_members())
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
    }

    @Override
    public void giveKit(PlayerData playerData) {
        for (CustomItem customItem : contents)
            if (playerData.isOnline())
                playerData.getPlayer().getInventory().setItem(customItem.getSlot(), customItem.getItemStack());

        for (CustomItem customItem : armors)
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
    }

    @Override
    public CustomItem[] getArmors() {
        return armors;
    }

    @Override
    public CustomItem[] getContents() {
        return contents;
    }
}
