package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import org.bukkit.entity.Player;

public final class ComandoClanEntrar {
    private ComandoClanEntrar() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        if (args.length < 2) {
            jogador.sendMessage(Mensagens.comPrefixo("&eUso: /clan entrar <tag>"));
            return;
        }
        Clan c = plugin.getGerenciador().getClaPorTag(args[1]);
        if (c == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cClã não encontrado."));
            return;
        }
        boolean ok = plugin.getGerenciador().aceitarConvite(c, jogador.getUniqueId(), jogador.getName());
        if (ok) {
            c.registrarEvento(new EventoCla("ENTROU_CLA", jogador.getName(),
                    "entrou no clã"), plugin.getConfigMr().getDiarioMaxEventos());
            jogador.sendMessage(Mensagens.comPrefixo("&aVocê entrou no clã &b" + c.getTag() + "&a!"));
        } else {
            jogador.sendMessage(Mensagens.comPrefixo("&cConvite inválido, expirado ou você já está em um clã."));
        }
    }
}
