package cn.powernukkitx.exampleplugin.customitem;

import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomArmor;
import cn.nukkit.item.customitem.data.CreativeCategory;

public class MyArmor extends ItemCustomArmor {
    public MyArmor() {
        super("powernukkitx:pnx_armor");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .armorBuilder(this)
                .name("测试盔甲")
                .creativeCategory(CreativeCategory.EQUIPMENT)
                .texture("pnx_armor")
                .allowOffHand(true)
                .handEquipped(true)
                .build();
    }

    @Override
    public boolean isChestplate() {
        return true;
    }

    @Override
    public int getTier() {
        return ItemArmor.TIER_DIAMOND;
    }

    @Override
    public int getMaxDurability() {
        return 666;
    }

    @Override
    public int getEnchantAbility() {
        return 10;
    }

    @Override
    public int getArmorPoints() {
        return 100;
    }
}
