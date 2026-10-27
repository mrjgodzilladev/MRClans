package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.adapters.gui.MenuPrincipal;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import org.bukkit.entity.Player;

public final class ComandoClanAceitar {
    private ComandoClanAceitar() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        String tag;
        if (args.length >= 2) {
            tag = args[1];
        } else {
            tag = plugin.getGerenciador().tagDoConvitePendente(jogador.getUniqueId());
            if (tag == null) {
                jogador.sendMessage(Mensagens.comPrefixo("&cVocê não tem convite pendente. Use &b/clan entrar <tag>"));
                return;
            }
        }
        Clan c = plugin.getGerenciador().getClaPorTag(tag);
        if (c == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cClã não encontrado."));
            return;
        }
        boolean ok = plugin.getGerenciador().aceitarConvite(c, jogador.getUniqueId(), jogador.getName());
        if (ok) {
            c.registrarEvento(new EventoCla("ENTROU_CLA", jogador.getName(), "aceitou o convite"),
                    plugin.getConfigMr().getDiarioMaxEventos());
            jogador.sendMessage(Mensagens.comPrefixo("&aVocê entrou no clã &b" + c.getTag() + "&a!"));
            MenuPrincipal.abrir(jogador, c);
        } else {
            jogador.sendMessage(Mensagens.comPrefixo("&cConvite inválido, expirado ou você já está em um clã."));
        }
    }
}
