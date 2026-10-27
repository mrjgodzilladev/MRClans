package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import org.bukkit.entity.Player;

public final class ComandoClanSair {
    private ComandoClanSair() { }

    public static void executar(MRClans plugin, Player jogador) {
        Clan c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (c == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cVocê não está em um clã."));
            return;
        }
        boolean lider = c.getLider().equals(jogador.getUniqueId());
        if (lider) {
            jogador.sendMessage(Mensagens.comPrefixo("&cVocê é o líder. Sair desfaz o clã inteiro."));
            jogador.sendMessage(Mensagens.cor("&eSe for isso mesmo: &b/clan desfazer confirm"));
            return;
        }
        c.registrarEvento(new EventoCla("SAIU_CLA", jogador.getName(), "saiu do clã"),
                plugin.getConfigMr().getDiarioMaxEventos());
        plugin.getGerenciador().sair(c, jogador.getUniqueId());
        jogador.sendMessage(Mensagens.comPrefixo("&aVocê saiu do clã."));
    }
}
