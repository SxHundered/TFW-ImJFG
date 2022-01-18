package com.tfw.manager.team.kits.items;

import com.tfw.configuration.Style;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@RequiredArgsConstructor@Data
public class CustomItem implements ITem{

    private final String name;
    private final String displayName;
    private final Material material;
    private final int amount;
    private final boolean isGlow;
    private final int slot;
    private final int durability;

    private ItemStack itemStack;


    private Set<String> enchantments = new HashSet<>();
    private List<String> lores = new ArrayList<>();


    @Override
    public ItemStack retrieveItemStack() {
        return itemStack;
    }

    /**
     * Item to be send to the players!
     *
     * @return Return the item when finish
     */
    @Override
    public ItemStack generateItemStack() {
        ItemStack itemStack = new ItemStack(material, amount);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);

        if (isGlow)
            applyGlowEffect(itemMeta);

        if (!lores.isEmpty())
            loresChange(itemMeta);

        if (!enchantments.isEmpty())
            enchantmentAdder(itemMeta);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Applying lores into the item!
     *
     * @param itemMeta ItemMeta of the ItemStack!
     * @return the item meta
     */
    private ItemMeta loresChange(ItemMeta itemMeta){
        //TODO
        itemMeta.setLore(lores);
        return itemMeta;
    }

    /**
     *
     * Quick guider!, here we have 3 components of the list:
     * 1- Enchantment name
     * 2- The power of the enchantment!
     * 3- TOGGLE TO TRUE IF YOU WANT TO BE USED!
     *
     * @param itemMeta ItemMeta of the ItemStack!
     * @return the item meta
     */
    private ItemMeta enchantmentAdder(ItemMeta itemMeta){
        for (String enchantment : getEnchantments()){
            final List<String> list = Arrays.asList(enchantment.split("\\s*;\\s*"));
            if (list.size() == 3) {
                Enchantment enchant = Enchantment.getByName(list.get(0));
                int power = Integer.parseInt(list.get(1));
                boolean enabled = Boolean.parseBoolean(list.get(2));
                if (!itemMeta.hasConflictingEnchant(enchant))
                    itemMeta.addEnchant(enchant, power, enabled);
            }else
                Bukkit.getConsoleSender().sendMessage(Style.RED + "COULD NOT LOAD " + Style.YELLOW + enchantment + Style.RED + " for the item " + getDisplayName());
        }
        return itemMeta;
    }

    /**
     *
     * Applying glow effect into the item!
     *
     * @param itemMeta ItemMeta of the ItemStack!
     * @return the item meta
     */
    private ItemMeta applyGlowEffect(ItemMeta itemMeta){
        //TODO
        return itemMeta;
    }
}
