package mjaroslav.mcmods.mjutils.common.thaum;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;

import static mjaroslav.mcmods.mjutils.common.thaum.ThaumUtils.complete;
import static mjaroslav.mcmods.mjutils.common.thaum.ThaumUtils.isComplete;

/**
 * Thaumcraft module event handler
 *
 * @author MJaroslav
 */
public class ThaumEventHandler {
    public static ArrayList<ResearchCopy> researchCopyList = new ArrayList<ResearchCopy>();

    @SubscribeEvent
    public void eventPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (!event.player.worldObj.isRemote) {
            for (ResearchCopy researchCopy : researchCopyList) {
                if (researchCopy.exist() && isComplete(event.player, researchCopy.getOriginalKey())) {
                    complete(event.player, researchCopy.key);
                }
            }
        }
    }

    public static class ResearchCopy {
        private String originalKey;
        private String key;

        public ResearchCopy(String originalKey, String key) {
            this.originalKey = originalKey;
            this.key = key;
        }

        public boolean exist() {
            return !StringUtils.isNullOrEmpty(this.originalKey) && !StringUtils.isNullOrEmpty(this.key);
        }

        public String getKey() {
            return key;
        }

        public String getOriginalKey() {
            return originalKey;
        }
    }
}