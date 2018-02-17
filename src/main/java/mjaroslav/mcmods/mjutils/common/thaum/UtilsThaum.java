package mjaroslav.mcmods.mjutils.common.thaum;

import static thaumcraft.common.lib.events.EventHandlerRunic.*;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketSyncResearch;

/**
 * ThaumCraft utils.
 *
 * @author MJaroslav
 */
public class UtilsThaum {
    private static boolean thaumExist = false;

    /**
     * Thaumcraft is loaded.
     */
    public static void checkMod() {
        thaumExist = true;
    }

    public static boolean exist() {
        return thaumExist;
    }

    /**
     * Get total (all types) warp count from player.
     *
     * @param player
     *            - target.
     * @return Warp count, all types in one.
     */
    public static int getWarp(EntityPlayer player) {
        if (!thaumExist)
            return 0;
        int warp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTotal(player.getCommandSenderName());
        warp += getFinalWarp(player.getCurrentEquippedItem(), player);
        for (int a = 0; a < 4; ++a) {
            warp += getFinalWarp(player.inventory.armorItemInSlot(a), player);
        }
        IInventory baubles = BaublesApi.getBaubles(player);
        for (int a = 0; a < 4; ++a) {
            warp += getFinalWarp(baubles.getStackInSlot(a), player);
        }
        return warp;
    }

    public static void complete(EntityPlayer player, String research) {
        if (!thaumExist)
            return;
        if (isComplete(player, research))
            return;
        Thaumcraft.proxy.getResearchManager().completeResearch(player, research);
        if (player instanceof EntityPlayerMP)
            PacketHandler.INSTANCE.sendTo(new PacketSyncResearch(player), (EntityPlayerMP) player);
    }

    public static boolean isComplete(EntityPlayer player, String research) {
        if (!thaumExist)
            return false;
        return ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), research);
    }
}