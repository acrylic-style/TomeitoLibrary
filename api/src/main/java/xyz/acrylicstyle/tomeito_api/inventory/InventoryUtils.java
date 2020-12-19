package xyz.acrylicstyle.tomeito_api.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Collection;
import util.CollectionSet;
import util.ICollection;
import util.ICollectionList;
import util.SneakyThrow;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
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
    public InventoryUtils fillEmptySlots(@Nullable ItemStack itemStack) {
        ItemStack[] contents = ArrayUtil.expand(this.inventory.getContents(), this.inventory.getSize());
        for (int i = 0; i < contents.length; i++)
            if (contents[i] == null || contents[i].getType() == Material.AIR)
                contents[i] = itemStack;
        this.inventory.setContents(contents);
        return this;
    }

    @NotNull
    public InventoryUtils fill(int start, int end, @Nullable ItemStack itemStack) {
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

    @Contract(pure = true)
    @NotNull
    public Collection<Material, Integer> diff(@NotNull Inventory other) {
        Collection<Material, Integer> mapA = new Collection<>();
        Collection<Material, Integer> mapB = new Collection<>();
        getMaterials().forEach(material -> mapA.add(material, getTotalAmount(material)));
        TomeitoAPI.getMaterials(other).forEach(material -> mapB.add(material, TomeitoAPI.getTotalMaterialAmount(other, material)));
        return mapA.mapValues((material, amount) -> mapB.getOrDefault(material, 0) - amount)
                .addAll(mapB.filterKeys(material -> !mapA.containsKey(material)))
                .filter(i -> i != 0)
                .filterKeys(material -> material != Material.AIR);
    }

    @Contract(pure = true)
    public int getTotalAmount(@NotNull Material material) {
        return ICollection.asCollection(inventory.all(material)).valuesList().map(ItemStack::getAmount).reduce(ICollectionList.Reducer.SUM_INTEGER, 0);
    }

    @Contract(pure = true)
    @NotNull
    public CollectionSet<Material> getMaterials() {
        CollectionSet<Material> set = new CollectionSet<>();
        for (ItemStack item : inventory.getContents()) {
            if (item != null) set.add(item.getType());
        }
        return set;
    }
}
