package shukaro.artifice.block.decorative;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import shukaro.artifice.ArtificeBlocks;
import shukaro.artifice.ArtificeCore;
import shukaro.artifice.gui.ArtificeCreativeTab;
import shukaro.artifice.render.IconHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlora extends BlockFlower
{
    public static Icon[] icons = new Icon[ArtificeCore.flora.length];
    
    public BlockFlora(int id)
    {
        super(id, Material.plants);
        this.setTickRandomly(true);
        setCreativeTab(ArtificeCreativeTab.main);
        setHardness(0.0F);
        setStepSound(soundGrassFootstep);
        setUnlocalizedName("artifice.flora");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        if (meta > ArtificeCore.flora.length)
            return icons[0];
        return icons[meta];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg)
    {
        for (int i=0; i<ArtificeCore.flora.length; i++)
            icons[i] = IconHandler.registerSingle(reg, ArtificeCore.flora[i].toLowerCase(), "flora");
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        int meta = world.getBlockMetadata(x, y, z);
        
        if (meta == 3 && (world.getBlockLightValue(x, y, z) < 8))
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        if (meta == 4 && (world.getBlockLightValue(x, y, z) >= 8))
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int i, CreativeTabs tabs, List list)
    {
        for (int j = 0; j < ArtificeCore.flora.length - 1; j++)
        {
            list.add(new ItemStack(i, 1, j));
        }
    }
    
    @Override
    public int damageDropped(int meta)
    {
        return meta == 4 ? 3 : meta;
    }
    
    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) == 4 ? 3 : world.getBlockMetadata(x, y, z);
    }
}
