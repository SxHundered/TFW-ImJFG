package com.tfw.manager.team.kits;

import com.tfw.configuration.ConfigFile;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.kits.items.CustomItem;

import java.util.Set;

public interface IKIT {

    void loadKits(ConfigFile teamConfig, String path, String teamName);

    void giveKitAll();
    void giveKit(PlayerData playerData);

    Set<CustomItem> getArmors();
    Set<CustomItem> getContents();
}
