package cn.powernukkitx.exampleplugin.customitem;

import cn.nukkit.item.ItemDiamondSword;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.CreativeCategory;

public class MySword extends ItemCustomTool {
    public MySword() {
        super("powernukkitx:test_sward");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this)
                .creativeCategory(CreativeCategory.EQUIPMENT)
                .texture("test_sward")
                .name("测试剑")
                .allowOffHand(true)
                .handEquipped(true)
                .foil(true)
                .build();
    }

    @Override
    public int getMaxDurability() {
        return 1000;
    }

    @Override
    public int getTier() {
        return ItemDiamondSword.TIER_DIAMOND;
    }

    @Override
    public int getAttackDamage() {
        return 30;
    }

    @Override
    public int getEnchantAbility() {
        return 20;
    }

    @Override
    public boolean isSword() {
        return true;
    }
}
