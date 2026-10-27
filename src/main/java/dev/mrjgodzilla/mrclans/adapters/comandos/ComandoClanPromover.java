package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public final class ComandoClanPromover {
    private ComandoClanPromover() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        if (args.length < 2) {
            jogador.sendMessage(Mensagens.comPrefixo("&eUso: /clan promover <jogador>"));
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
        boolean ok = plugin.getGerenciador().promover(c, jogador.getUniqueId(), alvo.getUniqueId());
        if (ok) {
            String nome = alvo.getName() != null ? alvo.getName() : args[1];
            c.registrarEvento(new EventoCla("PROMOVER", jogador.getName(), "promoveu " + nome + " a moderador"),
                    plugin.getConfigMr().getDiarioMaxEventos());
            jogador.sendMessage(Mensagens.comPrefixo("&a" + nome + " agora é &eModerador&a."));
            if (alvo.isOnline() && alvo.getPlayer() != null) {
                alvo.getPlayer().sendMessage(Mensagens.comPrefixo("&aVocê foi promovido a Moderador no clã &b" + c.getTag()));
            }
        } else {
            jogador.sendMessage(Mensagens.comPrefixo("&cSó o líder pode promover membros (não o próprio líder)."));
        }
    }
}
