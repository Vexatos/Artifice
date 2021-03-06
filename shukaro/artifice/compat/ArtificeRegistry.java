package shukaro.artifice.compat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import shukaro.artifice.ArtificeConfig;
import shukaro.artifice.ArtificeCore;
import shukaro.artifice.util.IdMetaPair;

public abstract class ArtificeRegistry
{
    private static List<String> floraBlacklist = new ArrayList<String>();
    private static List<Integer> dimensionBlacklist = new ArrayList<Integer>();
    private static Set<Integer> stoneTypes = new HashSet<Integer>();
    private static List<String> worldTypeBlacklist = new ArrayList<String>();
    private static List<String> lotusWhitelist = new ArrayList<String>();
    private static Map<IdMetaPair, ArrayList<ItemStack>> sledgeBlocks = new HashMap<IdMetaPair, ArrayList<ItemStack>>();
    private static Map<Integer, ArrayList<ItemStack>> wildSledgeBlocks = new HashMap<Integer, ArrayList<ItemStack>>();
    private static Map<IdMetaPair, List<String>> tooltipMap = new HashMap<IdMetaPair, List<String>>();
    private static List<ItemStack> marbleTypes = new ArrayList<ItemStack>();
    private static List<ItemStack> basaltTypes = new ArrayList<ItemStack>();
    
    public static void registerMarbleType(int id, int meta)
    {
        ItemStack stack = new ItemStack(id, 1, meta);
        if (marbleTypes.isEmpty())
            marbleTypes.add(stack);
        else if (!marbleTypes.contains(stack))
        {
            marbleTypes.add(stack);
            if (marbleTypes.size() < 3)
            {
                IRecipe inner = new ShapelessOreRecipe(marbleTypes.get(1), marbleTypes.get(0));
                IRecipe wrap = new ShapelessOreRecipe(marbleTypes.get(0), marbleTypes.get(1));
                CraftingManager.getInstance().getRecipeList().add(inner);
                CraftingManager.getInstance().getRecipeList().add(wrap);
            }
            else
            {
                IRecipe old = new ShapelessOreRecipe(marbleTypes.get(0), marbleTypes.get(marbleTypes.size() - 2));
                IRecipe inner = new ShapelessOreRecipe(marbleTypes.get(marbleTypes.size()), marbleTypes.get(marbleTypes.size() - 1));
                IRecipe wrap = new ShapelessOreRecipe(marbleTypes.get(0), marbleTypes.get(marbleTypes.size() - 1));
                CraftingManager.getInstance().getRecipeList().remove(old);
                CraftingManager.getInstance().getRecipeList().add(inner);
                CraftingManager.getInstance().getRecipeList().add(wrap);
            }
        }
    }
    
    public static List<ItemStack> getMarbleTypes()
    {
        return marbleTypes;
    }
    
    public static void registerBasaltType(int id, int meta)
    {
        ItemStack stack = new ItemStack(id, 1, meta);
        if (basaltTypes.isEmpty())
            basaltTypes.add(stack);
        else if (!basaltTypes.contains(stack))
        {
            basaltTypes.add(stack);
            if (!ArtificeConfig.stoneCycleRecipes.getBoolean(true))
                return;
            if (basaltTypes.size() < 3)
            {
                IRecipe inner = new ShapelessOreRecipe(basaltTypes.get(1), basaltTypes.get(0));
                IRecipe wrap = new ShapelessOreRecipe(basaltTypes.get(0), basaltTypes.get(1));
                CraftingManager.getInstance().getRecipeList().add(inner);
                CraftingManager.getInstance().getRecipeList().add(wrap);
            }
            else
            {
                IRecipe old = new ShapelessOreRecipe(basaltTypes.get(0), basaltTypes.get(basaltTypes.size() - 2));
                IRecipe inner = new ShapelessOreRecipe(basaltTypes.get(basaltTypes.size()), basaltTypes.get(basaltTypes.size() - 1));
                IRecipe wrap = new ShapelessOreRecipe(basaltTypes.get(0), basaltTypes.get(basaltTypes.size() - 1));
                CraftingManager.getInstance().getRecipeList().remove(old);
                CraftingManager.getInstance().getRecipeList().add(inner);
                CraftingManager.getInstance().getRecipeList().add(wrap);
            }
        }
    }
    
    public static List<ItemStack> getBasaltTypes()
    {
        return basaltTypes;
    }
    
    public static void registerTooltip(int id, int meta, String line)
    {
        IdMetaPair pair = new IdMetaPair(id, meta);
        if (tooltipMap.get(pair) == null)
        {
            List<String> temp = new ArrayList<String>();
            temp.add(line);
            tooltipMap.put(pair, temp);
        }
        else
        {
            tooltipMap.get(pair).add(line);
        }
    }
    
    public static Map<IdMetaPair, List<String>> getTooltipMap()
    {
        return tooltipMap;
    }
    
    public static void registerSledgeBlock(int id, int meta, ArrayList<ItemStack> drops)
    {
        IdMetaPair pair = new IdMetaPair(id, meta);
        if (!pair.isValidBlock())
            ArtificeCore.logger.log(Level.WARNING, "Tried to register non-block id-meta pair in the sledgeBlock map: " + pair.toString());
        else if (!(sledgeBlocks.get(pair) != null))
            sledgeBlocks.put(pair, drops);
    }
    
    public static void registerWildSledgeBlock(int id, ArrayList<ItemStack> drops)
    {
        if (wildSledgeBlocks.get(id) == null)
            wildSledgeBlocks.put(id, drops);
    }
    
    public static Map<Integer, ArrayList<ItemStack>> getWildSledgeBlocks()
    {
        return wildSledgeBlocks;
    }
    
    public static Map<IdMetaPair, ArrayList<ItemStack>> getSledgeBlocks()
    {
        return sledgeBlocks;
    }
    
    public static void registerFloraBlacklist(String biome)
    {
        if (!floraBlacklist.contains(biome))
            floraBlacklist.add(biome);
    }
    
    public static List<String> getFloraBlacklist()
    {
        return floraBlacklist;
    }
    
    public static void registerDimensionBlacklist(int dimID)
    {
        if (!dimensionBlacklist.contains(dimID))
            dimensionBlacklist.add(dimID);
    }
    
    public static List<Integer> getDimensionBlacklist()
    {
        return dimensionBlacklist;
    }
    
    public static void registerStoneType(int stoneID)
    {
        if (!stoneTypes.contains(stoneID))
            stoneTypes.add(stoneID);
    }
    
    public static Set<Integer> getStoneTypes()
    {
        return stoneTypes;
    }
    
    public static void registerWorldTypeBlacklist(String type)
    {
        if (!worldTypeBlacklist.contains(type))
            worldTypeBlacklist.add(type);
    }
    
    public static List<String> getWorldTypeBlacklist()
    {
        return worldTypeBlacklist;
    }
    
    public static void registerLotusWhitelist(String biome)
    {
        if (!lotusWhitelist.contains(biome))
            lotusWhitelist.add(biome);
    }
    
    public static List<String> getLotusWhitelist()
    {
        return lotusWhitelist;
    }
}
