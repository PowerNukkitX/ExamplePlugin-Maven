package cn.powernukkitx.exampleplugin.customentity;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.custom.CustomEntity;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class MyPig extends Entity implements CustomEntity {
    public MyPig(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "powernukkitx:boar";
    }

    @Override
    public String getOriginalName() {
        return "boar";
    }
}
