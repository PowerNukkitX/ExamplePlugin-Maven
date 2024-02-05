package cn.powernukkitx.exampleplugin.customench;

import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.utils.Identifier;

public class MyEnchantment2 extends Enchantment {
    public MyEnchantment2() {
        super(new Identifier("powernukkitx:foo"), "Test2", Rarity.COMMON, EnchantmentType.ALL);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
