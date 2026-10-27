package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Cargo;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.entity.Player;

public final class ComandoClanPvp {
    private ComandoClanPvp() { }

    public static void executar(MRClans plugin, Player jogador) {
        Clan c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (c == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cVocê não está em um clã."));
            return;
        }
        Membro m = c.getMembros().get(jogador.getUniqueId());
        if (m == null || m.getCargo() != Cargo.LIDER) {
            jogador.sendMessage(Mensagens.comPrefixo("&cApenas o líder pode alterar o PvP do clã."));
            return;
        }
        c.setPvpAtivo(!c.isPvpAtivo());
        c.registrarEvento(new EventoCla("PVP", jogador.getName(),
                c.isPvpAtivo() ? "ativou PvP entre membros" : "desativou PvP entre membros"),
                plugin.getConfigMr().getDiarioMaxEventos());
        jogador.sendMessage(Mensagens.comPrefixo("&ePvP entre membros: "
                + (c.isPvpAtivo() ? "&aativado" : "&cdesativado")));
    }
}
