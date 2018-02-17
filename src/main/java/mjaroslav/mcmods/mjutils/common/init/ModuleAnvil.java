package mjaroslav.mcmods.mjutils.common.init;

import cpw.mods.fml.common.event.*;
import mjaroslav.mcmods.mjutils.common.anvil.*;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.mjutils.lib.ConfigInfo;
import mjaroslav.mcmods.mjutils.lib.ModInfo;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 * MJUtils anvil module.
 *
 * @author MJaroslav
 */
@ModInitModule(modid = ModInfo.MODID)
public class ModuleAnvil implements IModModule {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new AnvilEventHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        if (ConfigInfo.chainToIron) {
            UtilsAnvil.addRecipe(new ItemStack(Items.iron_helmet, 1),
                    new AnvilRecipe(new ItemStack(Items.chainmail_helmet, 1, 0), new ItemStack(Items.iron_ingot, 1)));
            UtilsAnvil.addRecipe(new ItemStack(Items.iron_chestplate, 1), new AnvilRecipe(
                    new ItemStack(Items.chainmail_chestplate, 1, 0), new ItemStack(Items.iron_ingot, 1)));
            UtilsAnvil.addRecipe(new ItemStack(Items.iron_leggings, 1),
                    new AnvilRecipe(new ItemStack(Items.chainmail_leggings, 1, 0), new ItemStack(Items.iron_ingot, 1)));
            UtilsAnvil.addRecipe(new ItemStack(Items.iron_boots, 1),
                    new AnvilRecipe(new ItemStack(Items.chainmail_boots, 1, 0), new ItemStack(Items.iron_ingot, 1)));
        }
        if (ConfigInfo.leatherToChain) {
            UtilsAnvil.addRecipe(new ItemStack(Items.chainmail_helmet, 1),
                    new AnvilRecipe(new ItemStack(Items.leather_helmet, 1, 0), new ItemStack(Blocks.iron_bars, 2)));
            UtilsAnvil.addRecipe(new ItemStack(Items.chainmail_chestplate, 1),
                    new AnvilRecipe(new ItemStack(Items.leather_chestplate, 1, 0), new ItemStack(Blocks.iron_bars, 2)));
            UtilsAnvil.addRecipe(new ItemStack(Items.chainmail_leggings, 1),
                    new AnvilRecipe(new ItemStack(Items.leather_leggings, 1, 0), new ItemStack(Blocks.iron_bars, 2)));
            UtilsAnvil.addRecipe(new ItemStack(Items.chainmail_boots, 1),
                    new AnvilRecipe(new ItemStack(Items.leather_boots, 1, 0), new ItemStack(Blocks.iron_bars, 2)));
        }
    }

    @Override
    public String getModuleName() {
        return "Anvil";
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String[] modDependencies() {
        return null;
    }

    @Override
    public boolean canLoad() {
        return true;
    }
}