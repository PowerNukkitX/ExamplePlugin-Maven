package org.powernukkitx.exampleplugin.custom.recipe;

import cn.nukkit.item.Item;
import cn.nukkit.recipe.FurnaceRecipe;
import cn.nukkit.recipe.ShapedRecipe;
import cn.nukkit.recipe.ShapelessRecipe;
import cn.nukkit.registry.Registries;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CustomRecipe {


    public static void registerAllRecipes() {
        registerShapeless();
        registerShaped();
        registerFurnace();
        // Rebuild the packet that's sends to the client so the client knows that your recipes exist.
        Registries.RECIPE.rebuildPacket();
    }

    /**
     * Creating a shapeless recipe
     */
    private static void registerShapeless() {
        Item result = Item.get(Item.DIAMOND);
        Item coal = Item.get(Item.COAL);

        List<Item> input = Arrays.asList(coal, coal, coal); //Three times coal. Always add items with count of one.
        // Create the recipe
        ShapelessRecipe recipe = new ShapelessRecipe(result, input);
        Registries.RECIPE.register(recipe);
    }

    /**
     * Creating a shaped recipe
     */
    private static void registerShaped() {
        // Define which items are used in the recipe
        Map<Character, Item> itemMapping = Map.of(
                'a', Item.get(Item.AMETHYST_SHARD),
                'b', Item.get(Item.STICK)
        );

        // Define the shape of the recipe
        String[] shape = new String[] {
                "aaa", //Three shards (defined above as a)
                " b ", //One stick, defined as b above
                " b " //Slots without items should be spaces.
        };

        Item result = Item.get("powernukkitx:amethyst_pickaxe");
        // Create the recipe
        ShapedRecipe recipe = new ShapedRecipe(result, shape, itemMapping, List.of());
        // Register the recipe
        Registries.RECIPE.register(recipe);
    }

    /**
     * Creating a furnace recipe
     */
    private static void registerFurnace() {
        Item result = Item.get(Item.DIAMOND);
        Item coal = Item.get(Item.COAL);
        FurnaceRecipe recipe = new FurnaceRecipe(result, coal);
        Registries.RECIPE.register(recipe);
    }

    /*
        For other recipe types, it works basically the same.
     */

}
