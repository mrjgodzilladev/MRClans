package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Cargo;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ComandoClanConvidar {
    private ComandoClanConvidar() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        if (args.length < 2) {
            Mensagens.enviar(jogador, "&eUso: /clan convidar <jogador>");
            return;
        }
        Clan c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (c == null) {
            Mensagens.enviar(jogador, "&cVocê não está em um clã.");
            return;
        }
        Membro m = c.getMembro(jogador.getUniqueId());
        if (m == null || m.getCargo() == Cargo.MEMBRO) {
            Mensagens.enviar(jogador, "&cApenas líder/moderador pode convidar.");
            return;
        }
        Player alvo = Bukkit.getPlayerExact(args[1]);
        if (alvo == null) {
            Mensagens.enviar(jogador, "&cJogador não encontrado online.");
            return;
        }
        if (alvo.getUniqueId().equals(jogador.getUniqueId())) {
            Mensagens.enviar(jogador, "&cVocê não pode se convidar.");
            return;
        }
        boolean ok = plugin.getGerenciador().convidar(c, alvo.getUniqueId());
        if (ok) {
            c.registrarEvento(new EventoCla("CONVITE", jogador.getName(), "convidou " + alvo.getName()),
                    plugin.getConfigMr().getDiarioMaxEventos());
            Mensagens.enviar(jogador, "&aConvite enviado para &b" + alvo.getName()
                    + " &7(expira em " + plugin.getConfigMr().getConviteExpiraSegundos() + "s).");
            Mensagens.enviarConvite(alvo, c.getTag(), jogador.getName());
        } else {
            Mensagens.enviar(jogador, "&cNão deu (clã cheio, já é membro ou já tem clã).");
        }
    }
}
