package cn.powernukkitx.exampleplugin.customblock;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockProperties;
import cn.nukkit.block.BlockState;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.block.customblock.data.Transformation;
import cn.nukkit.math.Vector3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyBlock extends Block implements CustomBlock {
    public static final BlockProperties PROPERTIES = new BlockProperties("powernukkitx:redstoneluckyblock");

    public MyBlock() {
        super(PROPERTIES.getDefaultState());
    }

    public MyBlock(@Nullable BlockState blockState) {
        super(blockState);
    }

    @Override
    public @NotNull BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public CustomBlockDefinition getDefinition() {
        return CustomBlockDefinition
                .builder(this)
                .texture("redstoneluckyblock")
                .transformation(new Transformation(new Vector3(0, 0, 0), new Vector3(1, 1, 1), new Vector3(90, 180, 90)))
                .breakTime(3)
                .build();
    }

    @Override
    public double getFrictionFactor() {
        return 0.6;
    }

    @Override
    public double getHardness() {
        return 99999;
    }

    @Override
    public double getResistance() {
        return 500;
    }

    @Override
    public int getLightLevel() {
        return 15;
    }

    @Override
    public int getLightFilter() {
        return 15;
    }

    @Override
    public int getBurnAbility() {
        return 0;
    }

    @Override
    public int getBurnChance() {
        return 0;
    }

    @Override
    public int getItemMaxStackSize() {
        return 64;
    }
}
