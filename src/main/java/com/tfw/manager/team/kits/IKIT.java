package com.tfw.manager.team.kits;

import com.tfw.configuration.ConfigFile;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.kits.items.CustomItem;

public interface IKIT {

    void loadKits(ConfigFile teamConfig, String path);

    void giveKitAll();
    void giveKit(PlayerData playerData);

    CustomItem[] getArmors();
    CustomItem[] getContents();
}
