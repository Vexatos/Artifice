package shukaro.artifice.compat.ee3;

import java.util.List;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import shukaro.artifice.ArtificeBlocks;
import shukaro.artifice.ArtificeConfig;
import shukaro.artifice.ArtificeCore;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "ArtificeCompat|EE3", name = "Artifice Compat: EE3", version = ArtificeCore.modVersion, dependencies = "after:Artifice;after:EE3")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class EE3
{
    @Init
    public static void load(FMLInitializationEvent e0)
    {
        if (!Loader.isModLoaded("EE3"))
        {
            ArtificeCore.logger.warning("EE3 missing, not loading compat");
            return;
        }
        try
        {
            Class<?> recipeClass = Class.forName("com.pahimar.ee3.recipe.RecipesTransmutationStone");
            List<ItemStack> stoneList = (List<ItemStack>) recipeClass.getField("transmutationStones").get(null);
            
            if (ArtificeConfig.enableWorldGen.getBoolean(true))
            {
                for (ItemStack stone : stoneList)
                {
                    GameRegistry.addShapelessRecipe(new ItemStack(ArtificeBlocks.blockBasalt.blockID, 2, 1), stone, new ItemStack(Block.cobblestone.blockID, 1, 0), new ItemStack(Block.cobblestone.blockID, 1, 0));
                    GameRegistry.addShapelessRecipe(new ItemStack(ArtificeBlocks.blockBasalt.blockID, 1, 1), stone, new ItemStack(ArtificeBlocks.blockMarble.blockID, 1, 1));
                    GameRegistry.addShapelessRecipe(new ItemStack(ArtificeBlocks.blockMarble.blockID, 1, 1), stone, new ItemStack(ArtificeBlocks.blockBasalt.blockID, 1, 1));
                }
            }
            
            ArtificeCore.logger.log(Level.INFO, "EE3 Compat Initialized");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
