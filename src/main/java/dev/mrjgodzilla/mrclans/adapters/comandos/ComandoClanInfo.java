package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public final class ComandoClanInfo {
    private ComandoClanInfo() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        Clan c;
        if (args.length < 2) {
            c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
            if (c == null) {
                Mensagens.enviar(jogador, "&eUso: /clan info <jogador|tag>");
                return;
            }
        } else {
            c = plugin.getGerenciador().getClaPorTag(args[1]);
            if (c == null) {
                OfflinePlayer off = Bukkit.getOfflinePlayer(args[1]);
                c = plugin.getGerenciador().getClaDoJogador(off.getUniqueId());
            }
            if (c == null) {
                Mensagens.enviar(jogador, "&cNenhum clã encontrado para &e" + args[1]);
                return;
            }
        }

        String liderNome = "?";
        Membro lider = c.getMembro(c.getLider());
        if (lider != null) liderNome = lider.getNome();

        Mensagens.enviar(jogador, "&6=== &b" + c.getTag() + " &7— &f" + c.getNome() + " &6===");
        if (!c.getMotto().isEmpty()) {
            jogador.sendMessage(Mensagens.cor("&7\"" + c.getMotto() + "\""));
        }
        jogador.sendMessage(Mensagens.cor("&7Líder: &f" + liderNome));
        jogador.sendMessage(Mensagens.cor("&7Membros: &f" + c.getMembros().size()
                + " &8| &7Kills: &f" + c.getEstatisticas().getKills()
                + " &8| &7KDR: &f" + String.format("%.2f", c.getEstatisticas().getKdr())));
        jogador.sendMessage(Mensagens.cor("&7PvP interno: "
                + (c.isPvpAtivo() ? "&aON" : "&cOFF")
                + " &8| &7Home: " + (c.temHome() ? "&asim" : "&cnão")));
        if (!c.getRelacoes().isEmpty()) {
            jogador.sendMessage(Mensagens.cor("&7Relações: &f" + c.getRelacoes().size() + " registrada(s)"));
        }
    }
}
