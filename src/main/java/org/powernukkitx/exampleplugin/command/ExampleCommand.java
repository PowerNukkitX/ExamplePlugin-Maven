package org.powernukkitx.exampleplugin.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.tree.ParamList;
import cn.nukkit.command.tree.node.PlayersNode;
import cn.nukkit.command.utils.CommandLogger;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.ai.EntityAI;
import cn.nukkit.form.element.ElementDivider;
import cn.nukkit.form.element.ElementHeader;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.custom.ElementInput;
import cn.nukkit.form.element.custom.ElementSlider;
import cn.nukkit.form.element.simple.ButtonImage;
import cn.nukkit.form.element.simple.ElementButton;
import cn.nukkit.form.window.CustomForm;
import cn.nukkit.form.window.ModalForm;
import cn.nukkit.form.window.SimpleForm;
import cn.nukkit.inventory.fake.FakeInventory;
import cn.nukkit.inventory.fake.FakeInventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import org.apache.commons.io.output.TeeWriter;
import org.powernukkitx.exampleplugin.ExamplePlugin;
import org.powernukkitx.exampleplugin.custom.entity.MyHuman;
import org.powernukkitx.exampleplugin.custom.entity.MyPig;
import org.powernukkitx.exampleplugin.event.CustomEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExampleCommand extends PluginCommand<ExamplePlugin> {

    public ExampleCommand() {
        /*
        1.the name of the command must be lowercase
        2.Here the description is set in with the key in the language file,Look at en_US.lang or zh_CN.lang.
        This can send different command description to players of different language.
        You must extends PluginCommand to have this feature.
        */
        super("examplecommand", "exampleplugin.examplecommand.description", ExamplePlugin.INSTANCE);

        //Set the alias for this command
        this.setAliases(new String[]{"test"});

        this.setPermission("exampleplugin.command.examplecommand.say1;exampleplugin.command.examplecommand.say2");

        /*
         * The following begins to set the command parameters, first need to clean,
         * because NK will fill in several parameters by default, we do not need.
         * */
        this.getCommandParameters().clear();

        /*
         * 1.getCommandParameters return a Map<String,cn.nukkit.command.data.Com mandParameter[]>,
         * in which each entry can be regarded as a subcommand or a command pattern.
         * 2.Each subcommand cannot be repeated.
         * 3.Optional arguments must be used at the end of the subcommand or consecutively.
         */
        this.getCommandParameters().put("pattern1", new CommandParameter[]{
                CommandParameter.newEnum("enum2", false, new CommandEnum("method", "say1")),
        });
        this.getCommandParameters().put("pattern2", new CommandParameter[]{
                CommandParameter.newEnum("enum2", false, new CommandEnum("method", "say2")),
                CommandParameter.newType("player", false, CommandParamType.TARGET, new PlayersNode()),
                CommandParameter.newType("message", true, CommandParamType.STRING)
        });
        this.getCommandParameters().put("spawn", new CommandParameter[]{
                CommandParameter.newEnum("enum2", false, new CommandEnum("spawn", "spawn"))
        });
        this.getCommandParameters().put("exampleevent", new CommandParameter[]{
                CommandParameter.newEnum("enum2", false, new CommandEnum("exampleevent"))
        });
        this.commandParameters.put("event", new CommandParameter[]{
                CommandParameter.newEnum("event", new String[]{"event"}),
        });
        this.commandParameters.put("form", new CommandParameter[]{
                CommandParameter.newEnum("form", new String[]{"form"}),
                CommandParameter.newEnum("enum3", new String[]{"simple", "modal", "custom"})
        });

        this.getCommandParameters().put("inventory", new CommandParameter[]{
                CommandParameter.newEnum("inventory",  new String[]{"inventory"})
        });
        /*
         * You'll find two `execute()` methods,
         * where `boolean execute()` is the old NK method,
         * and if you want to use the new `int execute()`,
         * you must add `enableParamTree` at the end of the constructor.
         *
         * Note that you can only choose one of these two execute methods
         */
        this.enableParamTree();
    }

    /**
     * This method is executed only if the command syntax is correct, which means you don't need to verify the parameters yourself.
     * In addition, before executing the command, will check whether the executor has the permission for the command.
     * If these conditions are not met, an error message is automatically displayed.
     *
     * @param sender       The sender of the command
     * @param commandLabel Command label. For example, if `/test 123` is used, the value is `test`
     * @param result       The parsed matching subcommand pattern
     * @param log          The command output tool, which is used to output info, can be controlled by the world's sendCommandFeedback rule
     */
    @Override
    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        var list = result.getValue();
        switch (result.getKey()) {
            case "spawn" -> {
                ExamplePlugin.INSTANCE.getLogger().info("spawn custom entity");
                if (sender.isPlayer()) {
                    Player player = sender.asPlayer();
                    new MyPig(player.getChunk(), Entity.getDefaultNBT(player)).spawnToAll();
                    new MyHuman(player.getChunk(), Entity.getDefaultNBT(player)).spawnToAll();
                }
            }
            case "pattern1" -> {
                System.out.println("execute say1");
            }
            case "pattern2" -> {
                System.out.println("execute say2");
                /*
                 * You need to use the corresponding parameter index to get the parsing value,
                 * Each different parameter corresponds to a different return value type,
                 * There will be a document for you to see
                 */
                List<Player> players = list.getResult(1);

                //For optional parameters, you must judge if they exist before you get the result
                if (list.hasResult(2)) {
                    String message = list.getResult(2);
                    /*
                     * 1.log.addMessage output red color by default.
                     *
                     * 2.Using the TextFormat to add color characters, note that language key needs to be separated by %
                     * All text that is not related to the language key must be separated by %
                     *
                     * 3.addMessage will automatically translate the language key,
                     * and addSuccess will output white by default,
                     * and addError will output red by default,
                     * None of the text is translated except for the client-language key.
                     */
                    log.addMessage(TextFormat.WHITE + "%exampleplugin.helloworld% this is message:" + message, players.stream().map(Player::getName).toList().toString()).output();
                } else {
                    log.addMessage(TextFormat.WHITE + "%exampleplugin.helloworld", players.stream().map(Player::getName).toList().toString()).output();
                }
            }
            case "event" -> {
                int tick = sender.getLocation().getLevel().getTick();
                /*
                 * We call our custom event here
                 */
                Server.getInstance().getPluginManager().callEvent(new CustomEvent(tick));
            }

            /*
             *
             * FORMS
             *
             */

            case "form" -> {
                if (sender.isPlayer()) {
                    Player player = sender.asPlayer();
                    switch (list.getResult(1).toString()) {
                        case "simple" -> {
                            SimpleForm simpleForm = new SimpleForm();
                            simpleForm.title("SimpleForm");
                            simpleForm.content("Hello. This is the forms content.");
                            simpleForm.addButton("Button1", (r) -> {
                                player.sendMessage("You pressed the first button");
                            });
                            simpleForm.addElement(new ElementDivider());
                            simpleForm.addElement(new ElementHeader("Header!!!"));
                            simpleForm.addButton(new ElementButton("Button2", new ButtonImage(ButtonImage.Type.PATH, "textures/items/apple")), (r) -> {
                               player.sendMessage("You pressed the second button!");
                            });
                            simpleForm.addElement(new ElementLabel("Use a label to add simple text to your forms"));
                            simpleForm.send(player);
                            /*
                             * Updating a form. In this example we will add a new element after three seconds.
                             */
                            Server.getInstance().getScheduler().scheduleDelayedTask(() -> {
                                if(simpleForm.isViewer(player)) {
                                    simpleForm.addElement(new ElementHeader(TextFormat.RED + "You also can update forms!"));
                                    simpleForm.sendUpdate(player);
                                }
                            }, 60);
                        }
                        case "modal" -> {
                            ModalForm modalForm = new ModalForm();
                            modalForm.title("ModalForm");
                            modalForm.content("This is a modal form!");
                            modalForm.yes(TextFormat.GREEN + "YES", (r) -> {
                                r.sendMessage("You pressed YES");
                            });
                            modalForm.no(TextFormat.RED + "NO", (r) -> {
                                r.sendMessage("You pressed NO!");
                            });
                            modalForm.send(player);
                        }
                        case "custom" -> {
                            CustomForm customForm = new CustomForm();
                            customForm.title("CustomForm");
                            customForm.addElement(new ElementSlider("Slider", 0, 10));
                            customForm.addElement(new ElementDivider());
                            customForm.addElement(new ElementInput("Input", "This text will show when box is empty.", "DefaultText"));
                            customForm.addElement(new ElementHeader("Header"));
                            customForm.onSubmit((p, response) -> {
                                p.sendMessage("Slider: " +response.getSliderResponse(0));
                                p.sendMessage("Input: " + response.getInputResponse(2));
                            });
                            customForm.send(player);
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + list.getResult(0));
                    }
                }
            }

            /*
             *
             * FAKE INVENTORIES
             *
             */

            case "inventory" -> {
                if (sender.isPlayer()) {
                    Player player = sender.asPlayer();
                    FakeInventory inv = new FakeInventory(FakeInventoryType.DOUBLE_CHEST, "Fake Inventory Title");
                    inv.setItem(0, Item.get(Item.APPLE)); //First slot
                    inv.setItem(1, Item.get(Item.DIAMOND)); //Second slot
                    inv.setItem(9, Item.get(Block.BARRIER)); //Second row, first slot (8 slots of first row + 1)

                    //If you prefer events to lambda, use PlayerTransferItemEvent
                    inv.setItemHandler(0, (fakeInventory, slot, oldItem, newItem, itemStackRequestActionEvent) -> {
                        itemStackRequestActionEvent.getPlayer().sendMessage("You clicked the first item!!");
                    });

                    inv.setDefaultItemHandler((fakeInventory, slot, oldItem, newItem, itemStackRequestActionEvent) -> {
                        switch (slot) {
                            case 1 -> {
                                fakeInventory.setItem(slot, Item.get(Item.EMERALD));
                            }
                            default -> {
                                fakeInventory.close(itemStackRequestActionEvent.getPlayer());
                            }
                        }
                        //Prevent your players from removing the items
                        itemStackRequestActionEvent.setCancelled();
                    });

                    //You cannot open two UI elements at the same time. By the time the command is executed, the command screen is still open, so you have to delay the opening of the inventory here.
                    Server.getInstance().getScheduler().scheduleDelayedTask(() -> player.addWindow(inv), 10);
                }
            }
            default -> {
                return 0;
            }
        }
        //A return of 0 means failure, and a return of 1 means success.
        //This value is applied to the comparator next to the commandblock.
        return 1;
    }
}
