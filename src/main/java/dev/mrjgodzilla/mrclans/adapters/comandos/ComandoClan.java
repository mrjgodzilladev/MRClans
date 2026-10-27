package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.adapters.gui.MenuPrincipal;
import dev.mrjgodzilla.mrclans.adapters.gui.MenuSemCla;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.config.Perms;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.GerenciadorClas;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ComandoClan implements CommandExecutor, TabCompleter {

    private static final List<String> SUBS = List.of(
            "criar", "create", "convidar", "invite",
            "entrar", "join", "aceitar", "accept", "recusar", "deny",
            "sair", "leave", "expulsar", "kick", "desfazer", "disband",
            "promover", "promote", "rebaixar", "demote",
            "listar", "list", "online", "info", "top",
            "chat", "pvp", "home", "sethome", "motto",
            "aliar", "ally", "rival", "neutro",
            "admin", "ajuda", "help"
    );

    private final MRClans plugin;
    private final GerenciadorClas gerenciador;

    public ComandoClan(MRClans plugin, GerenciadorClas gerenciador) {
        this.plugin = plugin;
        this.gerenciador = gerenciador;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player jogador)) {
            sender.sendMessage("Só in-game.");
            return true;
        }
        if (!Perms.podeUsar(jogador)) {
            Mensagens.enviar(jogador, "&cSem permissão (mrclans.usar).");
            return true;
        }

        if (args.length == 0) {
            Clan c = gerenciador.getClaDoJogador(jogador.getUniqueId());
            if (c == null) MenuSemCla.abrir(jogador);
            else MenuPrincipal.abrir(jogador, c);
            return true;
        }

        String sub = args[0].toLowerCase(Locale.ROOT);
        switch (sub) {
            case "criar", "create" -> ComandoClanCriar.executar(plugin, jogador, args);
            case "convidar", "invite" -> ComandoClanConvidar.executar(plugin, jogador, args);
            case "entrar", "join" -> ComandoClanEntrar.executar(plugin, jogador, args);
            case "aceitar", "accept" -> ComandoClanAceitar.executar(plugin, jogador, args);
            case "recusar", "deny" -> ComandoClanRecusar.executar(plugin, jogador, args);
            case "sair", "leave" -> ComandoClanSair.executar(plugin, jogador);
            case "expulsar", "kick" -> ComandoClanExpulsar.executar(plugin, jogador, args);
            case "desfazer", "disband" -> ComandoClanDesfazer.executar(plugin, jogador, args);
            case "promover", "promote" -> ComandoClanPromover.executar(plugin, jogador, args);
            case "rebaixar", "demote" -> ComandoClanRebaixar.executar(plugin, jogador, args);
            case "listar", "list" -> ComandoClanListar.executar(plugin, jogador);
            case "online" -> ComandoClanOnline.executar(plugin, jogador);
            case "info" -> ComandoClanInfo.executar(plugin, jogador, args);
            case "top" -> ComandoClanTop.executar(plugin, jogador);
            case "chat" -> ComandoClanChat.executar(plugin, jogador);
            case "pvp" -> ComandoClanPvp.executar(plugin, jogador);
            case "home" -> ComandoClanHome.executar(plugin, jogador);
            case "sethome" -> ComandoClanSetHome.executar(plugin, jogador);
            case "motto" -> ComandoClanMotto.executar(plugin, jogador, args);
            case "aliar", "ally", "rival", "neutro" -> ComandoClanAliar.executar(plugin, jogador, args);
            case "admin" -> ComandoClanAdmin.executar(plugin, jogador, args);
            case "ajuda", "help", "?" -> enviarAjuda(jogador);
            default -> {
                Mensagens.enviar(jogador, "&cSubcomando desconhecido: &e" + sub);
                enviarAjuda(jogador);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String p = args[0].toLowerCase(Locale.ROOT);
            return SUBS.stream().filter(s -> s.startsWith(p)).collect(Collectors.toList());
        }
        if (args.length == 2) {
            String sub = args[0].toLowerCase(Locale.ROOT);
            String p = args[1].toLowerCase(Locale.ROOT);
            if (List.of("convidar", "invite", "expulsar", "kick", "info", "promover", "promote", "rebaixar", "demote").contains(sub)) {
                List<String> nomes = new ArrayList<>();
                for (Player pl : plugin.getServer().getOnlinePlayers()) {
                    if (pl.getName().toLowerCase(Locale.ROOT).startsWith(p)) nomes.add(pl.getName());
                }
                return nomes;
            }
            if (List.of("entrar", "join", "aceitar", "accept", "recusar", "deny", "aliar", "ally", "rival", "neutro").contains(sub)) {
                return gerenciador.getTodos().stream().map(Clan::getTag)
                        .filter(t -> t.toLowerCase(Locale.ROOT).startsWith(p)).collect(Collectors.toList());
            }
            if (sub.equals("desfazer") || sub.equals("disband")) {
                if ("confirm".startsWith(p)) return List.of("confirm");
            }
            if (sub.equals("admin") && sender.hasPermission(Perms.ADMIN)) {
                return List.of("reload", "desfazer", "forcarentrar", "spy", "salvar", "info").stream()
                        .filter(s -> s.startsWith(p)).collect(Collectors.toList());
            }
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("admin")) {
            String a = args[1].toLowerCase(Locale.ROOT);
            String p = args[2].toLowerCase(Locale.ROOT);
            if (a.equals("desfazer") || a.equals("disband")) {
                return gerenciador.getTodos().stream().map(Clan::getTag)
                        .filter(t -> t.toLowerCase(Locale.ROOT).startsWith(p)).collect(Collectors.toList());
            }
            if (a.equals("forcarentrar") || a.equals("forcejoin")) {
                List<String> nomes = new ArrayList<>();
                for (Player pl : plugin.getServer().getOnlinePlayers()) {
                    if (pl.getName().toLowerCase(Locale.ROOT).startsWith(p)) nomes.add(pl.getName());
                }
                return nomes;
            }
        }
        if (args.length == 4 && args[0].equalsIgnoreCase("admin")) {
            String a = args[1].toLowerCase(Locale.ROOT);
            if (a.equals("forcarentrar") || a.equals("forcejoin")) {
                String p = args[3].toLowerCase(Locale.ROOT);
                return gerenciador.getTodos().stream().map(Clan::getTag)
                        .filter(t -> t.toLowerCase(Locale.ROOT).startsWith(p)).collect(Collectors.toList());
            }
        }
        return List.of();
    }

    private static void enviarAjuda(Player j) {
        Mensagens.enviar(j, "&e/clan &7→ GUI");
        j.sendMessage(Mensagens.cor("&7criar · convidar · aceitar · recusar · sair · expulsar"));
        j.sendMessage(Mensagens.cor("&7promover · rebaixar · listar · online · top · chat · pvp · home"));
        j.sendMessage(Mensagens.cor("&7motto · aliar · rival · neutro · desfazer confirm"));
        if (Perms.isAdmin(j)) {
            j.sendMessage(Mensagens.cor("&6Staff: &f/clan admin &7reload|desfazer|forcarentrar|spy|salvar"));
        }
    }
}
