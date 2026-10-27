package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public final class ComandoClanExpulsar {
    private ComandoClanExpulsar() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        if (args.length < 2) {
            jogador.sendMessage(Mensagens.comPrefixo("&eUso: /clan expulsar <jogador>"));
            return;
        }
        Clan c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (c == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cVocê não está em um clã."));
            return;
        }
        OfflinePlayer alvo = Bukkit.getOfflinePlayer(args[1]);
        if (!c.temMembro(alvo.getUniqueId())) {
            jogador.sendMessage(Mensagens.comPrefixo("&cEsse jogador não está no seu clã."));
            return;
        }
        String nomeAlvo = alvo.getName() != null ? alvo.getName() : args[1];
        boolean ok = plugin.getGerenciador().expulsar(c, jogador.getUniqueId(), alvo.getUniqueId());
        if (ok) {
            c.registrarEvento(new EventoCla("EXPULSO", jogador.getName(), "expulsou " + nomeAlvo),
                    plugin.getConfigMr().getDiarioMaxEventos());
            jogador.sendMessage(Mensagens.comPrefixo("&a" + nomeAlvo + " foi expulso do clã."));
            if (alvo.isOnline() && alvo.getPlayer() != null) {
                alvo.getPlayer().sendMessage(Mensagens.comPrefixo("&cVocê foi expulso do clã &b" + c.getTag()));
            }
        } else {
            jogador.sendMessage(Mensagens.comPrefixo("&cSem permissão (ou alvo é líder/mesmo cargo)."));
        }
    }
}
