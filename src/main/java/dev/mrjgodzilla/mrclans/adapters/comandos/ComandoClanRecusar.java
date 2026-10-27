package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import org.bukkit.entity.Player;

public final class ComandoClanRecusar {
    private ComandoClanRecusar() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        String tag;
        if (args.length >= 2) {
            tag = args[1];
        } else {
            tag = plugin.getGerenciador().tagDoConvitePendente(jogador.getUniqueId());
            if (tag == null) {
                jogador.sendMessage(Mensagens.comPrefixo("&cNenhum convite pendente para recusar."));
                return;
            }
        }
        boolean ok = plugin.getGerenciador().recusarConvite(jogador.getUniqueId(), tag);
        jogador.sendMessage(Mensagens.comPrefixo(ok
                ? "&eConvite do clã &b" + tag + " &erecusado."
                : "&cNão havia convite válido dessa tag."));
    }
}
