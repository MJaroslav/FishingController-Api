package mjaroslav.mcmods.mjutils.common.init;

import cpw.mods.fml.common.event.*;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.mjutils.common.tooltip.TooltipEventHandler;
import mjaroslav.mcmods.mjutils.lib.ModInfo;
import net.minecraftforge.common.MinecraftForge;

/**
 * MJUtils tooltip module.
 *
 * @author MJaroslav
 */
@ModInitModule(modid = ModInfo.MODID)
public class ModuleTooltip implements IModModule {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new TooltipEventHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public String getModuleName() {
        return "Tooltip";
    }

    @Override
    public int getPriority() {
        return 2;
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