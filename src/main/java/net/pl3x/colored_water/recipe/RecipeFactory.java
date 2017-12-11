package net.pl3x.colored_water.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;

public class RecipeFactory implements IRecipeFactory {
    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        String group = JsonUtils.getString(json, "group", "");

        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients")) {
            ingredients.add(CraftingHelper.getIngredient(ele, context));
        }

        if (ingredients.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
        }
        if (ingredients.size() > 9) {
            throw new JsonParseException("Too many ingredients for shapeless recipe");
        }

        ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
        return new WaterRecipe(group, result, ingredients);
    }

    public static class WaterRecipe extends ShapelessRecipes {
        public WaterRecipe(String group, ItemStack result, NonNullList<Ingredient> ingredients) {
            super(group, result, ingredients);
        }

        @Override
        @Nonnull
        public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
            return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        }
    }
}
