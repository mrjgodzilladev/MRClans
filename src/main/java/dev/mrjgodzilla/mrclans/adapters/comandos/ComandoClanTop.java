package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import org.bukkit.entity.Player;

import java.util.List;

public final class ComandoClanTop {
    private ComandoClanTop() { }

    public static void executar(MRClans plugin, Player jogador) {
        String metrica = plugin.getConfigMr().getRankingMetrica();
        List<Clan> ranking = plugin.getGerenciador().ranking(metrica);
        jogador.sendMessage(Mensagens.comPrefixo("&6Top clãs (" + metrica + "):"));
        if (ranking.isEmpty()) {
            jogador.sendMessage(Mensagens.cor("&7Nenhum clã registrado."));
            return;
        }
        for (int i = 0; i < Math.min(10, ranking.size()); i++) {
            Clan c = ranking.get(i);
            jogador.sendMessage(Mensagens.cor("&e" + (i + 1) + ". &b" + c.getTag()
                    + " &7- &f" + c.getEstatisticas().getKills() + " kills &7(KDR "
                    + String.format("%.2f", c.getEstatisticas().getKdr()) + ")"));
        }
    }
}
