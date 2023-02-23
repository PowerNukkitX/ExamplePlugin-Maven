package cn.powernukkitx.exampleplugin;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.tree.ParamList;
import cn.nukkit.command.tree.node.PlayersNode;
import cn.nukkit.command.utils.CommandLogger;
import cn.nukkit.utils.TextFormat;

import java.util.List;
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
            default -> {
                return 0;
            }
        }
        //A return of 0 means failure, and a return of 1 means success.
        //This value is applied to the comparator next to the commandblock.
        return 1;
    }
}
