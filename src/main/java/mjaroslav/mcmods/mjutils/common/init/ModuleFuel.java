package mjaroslav.mcmods.mjutils.common.init;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import mjaroslav.mcmods.mjutils.common.fuel.FuelHandler;
import mjaroslav.mcmods.mjutils.common.objects.IModModule;
import mjaroslav.mcmods.mjutils.common.objects.ModInitModule;
import mjaroslav.mcmods.mjutils.lib.ModInfo;

/**
 * MJUtils fuel module.
 *
 * @author MJaroslav
 */
@ModInitModule(modid = ModInfo.MODID)
public class ModuleFuel implements IModModule {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        GameRegistry.registerFuelHandler(new FuelHandler());
    }

    @Override
    public String getModuleName() {
        return "Fuel";
    }

    @Override
    public int getPriority() {
        return 1;
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