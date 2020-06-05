package xyz.acrylicstyle.tomeito_api.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import util.SneakyThrow;
import xyz.acrylicstyle.tomeito_api.utils.ArrayUtil;

/**
 * Inventory utils provides some useful utilities.
 */
public class InventoryUtils implements Cloneable {
    @NotNull private final Inventory inventory;

    public InventoryUtils(@NotNull Inventory inventory) { this.inventory = inventory; }

    @NotNull
    public Inventory getInventory() { return this.inventory; }

    @NotNull
    public InventoryUtils fillEmptySlots(ItemStack itemStack) {
        ItemStack[] contents = ArrayUtil.expand(this.inventory.getContents(), this.inventory.getSize());
        for (int i = 0; i < contents.length; i++)
            if (contents[i] == null || contents[i].getType() == Material.AIR)
                contents[i] = itemStack;
        this.inventory.setContents(contents);
        return this;
    }

    @NotNull
    public InventoryUtils fill(int start, int end, ItemStack itemStack) {
        ItemStack[] contents = ArrayUtil.expand(this.inventory.getContents(), this.inventory.getSize());
        for (int i = start; i < end; i++) contents[i] = itemStack;
        this.inventory.setContents(contents);
        return this;
    }

    @NotNull
    @Override
    public InventoryUtils clone() {
        try {
            return (InventoryUtils) super.clone();
        } catch (CloneNotSupportedException e) {
            SneakyThrow.sneaky(e);
            return null;
        }
    }
}
