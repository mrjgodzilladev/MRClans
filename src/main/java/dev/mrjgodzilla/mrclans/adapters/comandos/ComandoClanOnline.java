package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ComandoClanOnline {
    private ComandoClanOnline() { }

    public static void executar(MRClans plugin, Player jogador) {
        Clan c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (c == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cVocê não está em um clã."));
            return;
        }
        StringBuilder sb = new StringBuilder();
        int online = 0;
        for (Membro m : c.getMembros().values()) {
            Player p = Bukkit.getPlayer(m.getUuid());
            if (p != null && p.isOnline()) {
                if (online > 0) sb.append("&7, ");
                sb.append("&a").append(p.getName());
                online++;
            }
        }
        jogador.sendMessage(Mensagens.comPrefixo("&eOnline no clã &b" + c.getTag()
                + " &7(" + online + "/" + c.getMembros().size() + "):"));
        if (online == 0) {
            jogador.sendMessage(Mensagens.cor("&7Ninguém online além de você (ou você é o único)."));
        } else {
            jogador.sendMessage(Mensagens.cor(sb.toString()));
        }
    }
}
