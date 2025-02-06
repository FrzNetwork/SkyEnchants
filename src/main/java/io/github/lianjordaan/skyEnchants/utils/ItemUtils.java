package io.github.lianjordaan.skyEnchants.utils;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    /**
     * Normalizes a list of ItemStacks by ensuring that no stack exceeds its max stack size.
     * If an ItemStack has more than its max stack size, the excess is split into new stacks.
     *
     * @param items The list of ItemStacks to normalize.
     * @return A new list of ItemStacks where all stacks are within their max stack size.
     */
    public static List<ItemStack> normalizeItemStacks(List<ItemStack> items) {
        List<ItemStack> normalizedItems = new ArrayList<>();

        for (ItemStack item : items) {
            if (item == null) continue; // Skip null items

            int maxStackSize = item.getMaxStackSize();
            int amount = item.getAmount();

            while (amount > maxStackSize) {
                // Create a new stack with the max amount
                ItemStack splitStack = item.clone();
                splitStack.setAmount(maxStackSize);
                normalizedItems.add(splitStack);

                amount -= maxStackSize;
            }

            // Add the remaining items (less than max stack size)
            if (amount > 0) {
                ItemStack remainingStack = item.clone();
                remainingStack.setAmount(amount);
                normalizedItems.add(remainingStack);
            }
        }

        return normalizedItems;
    }
}
