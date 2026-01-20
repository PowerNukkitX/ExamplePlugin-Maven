package org.powernukkitx.exampleplugin;

import cn.nukkit.Server;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.lang.PluginI18n;
import cn.nukkit.lang.PluginI18nManager;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.registry.RegisterException;
import cn.nukkit.registry.Registries;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import org.powernukkitx.exampleplugin.command.ExampleCommand;
import org.powernukkitx.exampleplugin.custom.block.MyBlock;
import org.powernukkitx.exampleplugin.custom.block.MySlab;
import org.powernukkitx.exampleplugin.custom.enchantment.MyEnchantment1;
import org.powernukkitx.exampleplugin.custom.enchantment.MyEnchantment2;
import org.powernukkitx.exampleplugin.custom.entity.MyHuman;
import org.powernukkitx.exampleplugin.custom.entity.MyPig;
import org.powernukkitx.exampleplugin.custom.item.MyArmor;
import org.powernukkitx.exampleplugin.custom.item.MyPickaxe;
import org.powernukkitx.exampleplugin.custom.item.MySword;
import org.powernukkitx.exampleplugin.custom.recipe.CustomRecipe;
import org.powernukkitx.exampleplugin.event.EventListener;

import java.io.File;
import java.util.LinkedHashMap;

public class ExamplePlugin extends PluginBase {
    public static ExamplePlugin INSTANCE;
    public static PluginI18n I18N;

    /*
     * We have to register items, blocks, enchantments etc on plugin load because we need them before the worlds are loaded.
     * You cannot register new items or blocks once all plugins are loaded.
     */
    @Override
    public void onLoad() {
        //save Plugin Instance
        INSTANCE = this;
        //register the plugin i18n
        I18N = PluginI18nManager.register(this);
        //register the command of plugin
        this.getServer().getCommandMap().register("exampleplugin", new ExampleCommand());

        this.getLogger().info(TextFormat.WHITE + "The example plugin has been loaded!");

        INSTANCE = this;
        try {
            Registries.ITEM.registerCustomItem(this, MyArmor.class, MySword.class, MyPickaxe.class);
            Registries.BLOCK.registerCustomBlock(this, MyBlock.class, MySlab.class);
            Registries.ENTITY.registerCustomEntity(this, MyPig.class);
            Registries.ENTITY.registerCustomEntity(this, MyHuman.class);
            Enchantment.register(new MyEnchantment1(), new MyEnchantment2());
            CustomRecipe.registerAllRecipes();

        } catch (RegisterException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        this.getLogger().info(TextFormat.DARK_GREEN + "The example plugin has been successfully launched!");
        this.getLogger().info(String.valueOf(this.getDataFolder().mkdirs()));

        //Use the plugin's i18n output
        this.getLogger().info(I18N.tr(Server.getInstance().getLanguageCode(), "exampleplugin.helloworld", "世界"));

        //Register the EventListener
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

        //PluginTask
        this.getServer().getScheduler().scheduleDelayedRepeatingTask(new BroadcastPluginTask(this), 500, 200);

        //Save resources
        this.saveResource("string.txt");

        //Config reading and writing
        Config config = new Config(
                new File(this.getDataFolder(), "config.yml"),
                Config.YAML,
                //Default values (not necessary)
                new ConfigSection(new LinkedHashMap<>() {
                    {
                        put("this-is-a-key", "Hello! Config!");
                        put("another-key", true); //you can also put other standard objects!
                    }
                }));

        //Now try to get the value, the default value will be given if the key isn't exist!
        this.getLogger().info(String.valueOf(config.get("this-is-a-key", "this-is-default-value")));

        //Don't forget to save it!
        config.save();
    }

    @Override
    public void onDisable() {
        this.getLogger().info(TextFormat.DARK_RED + "The example plugin has been disabled!");
    }
}
