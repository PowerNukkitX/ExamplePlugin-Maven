package cn.powernukkitx.exampleplugin.customblock;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockProperties;
import cn.nukkit.block.BlockState;
import cn.nukkit.block.BlockTransparent;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.block.customblock.data.*;
import cn.nukkit.block.property.type.BooleanPropertyType;
import cn.nukkit.item.Item;
import cn.nukkit.math.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MySlab extends BlockTransparent implements CustomBlock {
    public static final BooleanPropertyType BRIDGE_TOP_SLOT_BIT = BooleanPropertyType.of("bridge:top_slot_bit", false);
    public static final BooleanPropertyType BRIDGE_IS_FULL_BIT = BooleanPropertyType.of("bridge:is_full_bit", false);
    public static final BlockProperties PROPERTIES = new BlockProperties("powernukkitx:blue_mahoe_slab", BRIDGE_TOP_SLOT_BIT, BRIDGE_IS_FULL_BIT);

    public MySlab() {
        super(PROPERTIES.getDefaultState());
    }

    public MySlab(@Nullable BlockState blockState) {
        super(blockState);
    }

    @NotNull
    @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public CustomBlockDefinition getDefinition() {
        return CustomBlockDefinition
                .builder(this)
                .texture("blue_mahoe_planks")
                .geometry(new Geometry("geometry.custom_slab")
                        .boneVisibility("lower", true)
                        .boneVisibility("upper", false))
                .permutations(
                        new Permutation(Component.builder()
                                .collisionBox(new CollisionBox(-8, 0, -8, 16, 8, 16))
                                .selectionBox(new SelectionBox(-8, 0, -8, 16, 8, 16))
                                .geometry(new Geometry("geometry.custom_slab")
                                        .boneVisibility("lower", true)
                                        .boneVisibility("upper", false))
                                .build(),
                                "q.block_state('bridge:top_slot_bit') == false && q.block_state('bridge:is_full_bit') == false"),
                        new Permutation(Component.builder()
                                .collisionBox(new CollisionBox(-8, 8, -8, 16, 16, 16))
                                .selectionBox(new SelectionBox(-8, 8, -8, 16, 16, 16))
                                .geometry(new Geometry("geometry.custom_slab")
                                        .boneVisibility("lower", false)
                                        .boneVisibility("upper", true))
                                .build(),
                                "q.block_state('bridge:top_slot_bit') == true && q.block_state('bridge:is_full_bit') == false"),
                        new Permutation(Component.builder()
                                .collisionBox(new CollisionBox(-8, 0, -8, 16, 16, 16))
                                .selectionBox(new SelectionBox(-8, 0, -8, 16, 16, 16))
                                .geometry(new Geometry("geometry.custom_slab")
                                        .boneVisibility("lower", true)
                                        .boneVisibility("upper", true))
                                .build(),
                                "q.block_state('bridge:is_full_bit') == true")
                )
                .build();
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public double getFrictionFactor() {
        return 0.8;
    }

    @Override
    public double getResistance() {
        return 5;
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

    public boolean isOnTop() {
        return getPropertyValue(BRIDGE_TOP_SLOT_BIT);
    }

    public boolean isFull() {
        return getPropertyValue(BRIDGE_IS_FULL_BIT);
    }

    @Override
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, @Nullable Player player) {
        setPropertyValue(BRIDGE_IS_FULL_BIT, false);
        if (face == BlockFace.DOWN) {
            if (target instanceof MySlab) {
                if (target.getPropertyValue(BRIDGE_TOP_SLOT_BIT) && !target.getPropertyValue(BRIDGE_IS_FULL_BIT)) {//如果往上层半砖下方放置半砖，且不完整,则设为完整
                    setPropertyValue(BRIDGE_TOP_SLOT_BIT, false);
                    setPropertyValue(BRIDGE_IS_FULL_BIT, true);
                    this.getLevel().setBlock(target, this, true);
                    return false;
                } else if (!target.getPropertyValue(BRIDGE_TOP_SLOT_BIT) || target.getPropertyValue(BRIDGE_IS_FULL_BIT)) {//如果往下层半砖或者完整半砖下方放置半砖，则正常放置上层半砖
                    setPropertyValue(BRIDGE_TOP_SLOT_BIT, true);
                    this.getLevel().setBlock(this, this, true);
                    return true;
                } else return false;
            } else {
                setPropertyValue(BRIDGE_TOP_SLOT_BIT, true);
                this.getLevel().setBlock(this, this, true);
                return true;
            }
        } else if (face == BlockFace.UP) {
            if (target instanceof MySlab) {
                if (!target.getPropertyValue(BRIDGE_TOP_SLOT_BIT) && !target.getPropertyValue(BRIDGE_IS_FULL_BIT)) {//如果往下层半砖上方放置半砖，且不完整,则设为完整
                    setPropertyValue(BRIDGE_TOP_SLOT_BIT, false);
                    setPropertyValue(BRIDGE_IS_FULL_BIT, true);
                    this.getLevel().setBlock(target, this, true);
                    return false;
                } else if (target.getPropertyValue(BRIDGE_TOP_SLOT_BIT) || target.getPropertyValue(BRIDGE_IS_FULL_BIT)) {
                    setPropertyValue(BRIDGE_TOP_SLOT_BIT, false);
                    this.getLevel().setBlock(this, this, true);
                    return true;
                } else return false;
            } else {
                setPropertyValue(BRIDGE_TOP_SLOT_BIT, false);
                this.getLevel().setBlock(this, this, true);
            }
        } else {
            setPropertyValue(BRIDGE_TOP_SLOT_BIT, fy > 0.5);
            this.getLevel().setBlock(this, this, true);
            return true;
        }
        return true;
    }
}
