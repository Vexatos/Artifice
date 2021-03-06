package shukaro.artifice.block.frame;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import shukaro.artifice.ArtificeBlocks;
import shukaro.artifice.ArtificeConfig;
import shukaro.artifice.ArtificeCore;
import shukaro.artifice.render.IconHandler;
import shukaro.artifice.render.connectedtexture.ConnectedTexture;
import shukaro.artifice.render.connectedtexture.ConnectedTextureBase;
import shukaro.artifice.render.connectedtexture.IConnectedTexture;
import shukaro.artifice.render.connectedtexture.ILayeredRender;
import shukaro.artifice.render.connectedtexture.SolidConnectedTexture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockFrameBlastWall extends BlockFrame implements IConnectedTexture, ILayeredRender
{
    private Icon[] icons = new Icon[ArtificeCore.tiers.length];
    private ConnectedTextureBase basic = new SolidConnectedTexture(ConnectedTexture.BasicFrame);
    private ConnectedTextureBase reinforced = new SolidConnectedTexture(ConnectedTexture.ReinforcedFrame);
    private ConnectedTextureBase industrial = new SolidConnectedTexture(ConnectedTexture.IndustrialFrame);
    private ConnectedTextureBase advanced = new SolidConnectedTexture(ConnectedTexture.AdvancedFrame);
    
    public BlockFrameBlastWall(int id)
    {
        super(id);
        setUnlocalizedName("artifice.reinforced");
    }
    
    @Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
        int meta = world.getBlockMetadata(x, y, z);
        return this.getResistance(meta);
    }
    
    public float getResistance(int meta)
    {
        switch (meta)
        {
        case 0:
            return 20.0F;
        case 1:
            return 30.0F;
        case 2:
            return 50.0F;
        case 3:
            return 80.0F;
        default:
            return 10.0F;
        }
    }
    
    @Override
    public float getBlockHardness(World world, int x, int y, int z)
    {
        return super.getBlockHardness(world, x, y, z) + 5;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return null;
    }

    @Override
    public Icon getRenderIcon(int side, int meta)
    {
        return icons[meta];
    }

    @Override
    public ConnectedTexture getTextureType(int side, int meta)
    {
        switch (meta)
        {
        case 0:
            return ConnectedTexture.BasicFrame;
        case 1:
            return ConnectedTexture.ReinforcedFrame;
        case 2:
            return ConnectedTexture.IndustrialFrame;
        case 3:
            return ConnectedTexture.AdvancedFrame;
        default:
            return null;
        }
    }

    @Override
    public ConnectedTextureBase getTextureRenderer(int side, int meta)
    {
        switch (meta)
        {
        case 0:
            return basic;
        case 1:
            return reinforced;
        case 2:
            return industrial;
        case 3:
            return advanced;
        default:
            return null;
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg)
    {
    	ArtificeConfig.registerConnectedTextures(reg);
        for (int i=0; i<ArtificeCore.tiers.length; i++)
            icons[i] = IconHandler.registerSingle(reg, ArtificeCore.tiers[i].toLowerCase(), "blastwall");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        return this.getTextureType(side, meta).textureList[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess block, int x, int y, int z, int side)
    {
        return this.getTextureType(side, block.getBlockMetadata(x, y, z)).textureList[this.getTextureRenderer(side, block.getBlockMetadata(x, y, z)).getTextureIndex(block, x, y, z, side)];
    }

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}
}
