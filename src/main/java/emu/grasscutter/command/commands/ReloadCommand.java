package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;

@Command(label = "reload", usage = "reload",
        description = "Reload server config", permission = "server.reload")
public final class ReloadCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        if(args.size() == 0 || args.contains("config") || args.contains("all")) {
            CommandHandler.sendMessage(sender, "Reloading config.");
            Grasscutter.loadConfig();
        }

        if(args.size() == 0 || args.contains("gacha") || args.contains("all")) {
            CommandHandler.sendMessage(sender, "Reloading gacha.");
            Grasscutter.getGameServer().getGachaManager().load();
        }

        if(args.size() == 0 || args.contains("queries") || args.contains("all")) {
            CommandHandler.sendMessage(sender, "Reloading queries.");
            Grasscutter.getDispatchServer().loadQueries();
        }
        
        CommandHandler.sendMessage(sender, "Reload complete.");
    }
}
