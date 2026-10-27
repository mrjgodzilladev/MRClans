package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import org.bukkit.entity.Player;

import java.util.List;

public final class ComandoClanListar {
    private ComandoClanListar() { }

    public static void executar(MRClans plugin, Player jogador) {
        List<Clan> lista = plugin.getGerenciador().ranking("kills");
        if (lista.isEmpty()) {
            jogador.sendMessage(Mensagens.comPrefixo("&7Nenhum clã no servidor ainda."));
            return;
        }
        jogador.sendMessage(Mensagens.comPrefixo("&6Clãs do servidor &7(" + lista.size() + "):"));
        int i = 1;
        for (Clan c : lista) {
            if (i > 20) {
                jogador.sendMessage(Mensagens.cor("&7... e mais. Use &b/clan top &7ou a GUI."));
                break;
            }
            jogador.sendMessage(Mensagens.cor("&e" + i + ". &b[" + c.getTag() + "] &f" + c.getNome()
                    + " &7- " + c.getMembros().size() + " membros, "
                    + c.getEstatisticas().getKills() + " kills"));
            i++;
        }
    }
}
