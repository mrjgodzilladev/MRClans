package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Cargo;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class ComandoClanSetHome {
    private ComandoClanSetHome() { }

    public static void executar(MRClans plugin, Player jogador) {
        Clan c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (c == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cVocê não está em um clã."));
            return;
        }
        Membro m = c.getMembros().get(jogador.getUniqueId());
        if (m == null || m.getCargo() != Cargo.LIDER) {
            jogador.sendMessage(Mensagens.comPrefixo("&cApenas o líder pode definir a home do clã."));
            return;
        }
        Location loc = jogador.getLocation();
        if (loc.getWorld() == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cNão foi possível obter o mundo atual."));
            return;
        }
        c.setHome(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        c.registrarEvento(new EventoCla("SETHOME", jogador.getName(), "definiu a home do clã"),
                plugin.getConfigMr().getDiarioMaxEventos());
        jogador.sendMessage(Mensagens.comPrefixo("&aHome do clã definida neste local."));
    }
}
