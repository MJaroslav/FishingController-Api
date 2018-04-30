package mjaroslav.mcmods.mjutils.lib.handler;

import static mjaroslav.mcmods.mjutils.lib.utils.UtilsGame.*;
import static mjaroslav.utils.UtilsJava.*;
import static net.minecraft.util.StringUtils.*;

import net.minecraft.item.ItemStack;

public class AnvilRecipe {
    public ItemStack leftStack;
    public ItemStack rightStack;

    /**
     * Name of left stack in lower case without spaces. If the string is empty,
     * it is ignored.
     */
    public String leftStackName;

    /**
     * Name of left stack in lower case without spaces. If the string is empty,
     * it is ignored.
     */
    public String rightStackName;

    /**
     * Anvil text field. If the string is empty, it is ignored.
     */
    public String textField;

    /**
     * The price of the recipe in the levels. If it is zero, the price will
     * always be equal to one, not counting the quantity.
     */
    public int cost;

    public AnvilRecipe(ItemStack leftStack, ItemStack rightStack) {
        this(leftStack, rightStack, "", "", "", 0);
    }

    public AnvilRecipe(ItemStack leftStack, ItemStack rightStack, int cost) {
        this(leftStack, rightStack, "", "", "", cost);
    }

    public AnvilRecipe(ItemStack leftStack, ItemStack rightStack, String textField) {
        this(leftStack, rightStack, "", "", textField, 0);
    }

    public AnvilRecipe(ItemStack leftStack, ItemStack rightStack, String textField, int cost) {
        this(leftStack, rightStack, "", "", textField, cost);
    }

    public AnvilRecipe(ItemStack leftStack, ItemStack rightStack, String leftStackName, String rightStackName,
            String textField) {
        this(leftStack, rightStack, leftStackName, rightStackName, textField, 0);
    }

    public AnvilRecipe(ItemStack leftStack, ItemStack rightStack, String leftStackName, String rightStackName,
            String textField, int cost) {
        this.leftStack = leftStack;
        this.rightStack = rightStack;
        this.leftStackName = leftStackName;
        this.rightStackName = rightStackName;
        this.textField = textField;
        this.cost = cost;
    }

    public boolean equals(AnvilRecipe recipe) {
        return recipe != null && itemStackNotNullAndEquals(leftStack, recipe.leftStack)
                && itemStackNotNullAndEquals(rightStack, recipe.rightStack)
                && string(leftStackName).equals(string(recipe.leftStackName))
                && string(rightStackName).equals(string(recipe.rightStackName))
                && string(textField).equals(string(recipe.textField)) && cost == recipe.cost;
    }

    public boolean equals(ItemStack leftStack, ItemStack rightStack, String textField, int cost) {
        return itemStackNotNullAndEquals(this.leftStack, leftStack)
                && itemStackNotNullAndEquals(this.rightStack, rightStack)
                && (isNullOrEmpty(leftStackName) || string(nameFormat(leftStack)).equals(string(leftStackName)))
                && (isNullOrEmpty(rightStackName) || string(nameFormat(rightStack)).equals(string(rightStackName)))
                && (isNullOrEmpty(this.textField) || string(this.textField).equals(string(textField)))
                && (cost == -1 || this.cost == cost);
    }

    public AnvilRecipe copy() {
        return new AnvilRecipe(leftStack.copy(), rightStack.copy(), leftStackName, rightStackName, textField, cost);
    }
}
