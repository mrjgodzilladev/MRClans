package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Cargo;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ComandoClanHome {
    private static final Map<UUID, Long> COOLDOWN = new HashMap<>();

    private ComandoClanHome() { }

    public static void executar(MRClans plugin, Player jogador) {
        Clan c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (c == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cVocê não está em um clã."));
            return;
        }
        if (!c.temHome()) {
            jogador.sendMessage(Mensagens.comPrefixo("&cO clã ainda não tem home. Use &b/clan sethome &cou a GUI."));
            return;
        }

        World mundo = Bukkit.getWorld(c.getHomeMundo());
        if (mundo == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cO mundo da home não está carregado."));
            return;
        }
        Location home = new Location(mundo, c.getHomeX(), c.getHomeY(), c.getHomeZ(),
                c.getHomeYaw(), c.getHomePitch());

        Membro m = c.getMembros().get(jogador.getUniqueId());
        Cargo cargo = m != null ? m.getCargo() : Cargo.MEMBRO;
        int cooldownSeg = cooldownDoCargo(plugin, cargo);
        long agora = System.currentTimeMillis();
        Long ultimo = COOLDOWN.get(jogador.getUniqueId());
        if (ultimo != null && agora - ultimo < cooldownSeg * 1000L) {
            long restante = (cooldownSeg * 1000L - (agora - ultimo)) / 1000L;
            jogador.sendMessage(Mensagens.comPrefixo("&cAguarde &e" + restante + "s &cpara usar a home."));
            return;
        }

        int delay = plugin.getConfigMr().getHomeDelay();
        if (delay <= 0) {
            jogador.teleport(home);
            COOLDOWN.put(jogador.getUniqueId(), System.currentTimeMillis());
            jogador.sendMessage(Mensagens.comPrefixo("&aBem-vindo à home do clã!"));
            return;
        }

        jogador.sendMessage(Mensagens.comPrefixo("&eTeleportando em &b" + delay + "s&e..."));
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (jogador.isOnline()) {
                jogador.teleport(home);
                COOLDOWN.put(jogador.getUniqueId(), System.currentTimeMillis());
                jogador.sendMessage(Mensagens.comPrefixo("&aBem-vindo à home do clã!"));
            }
        }, delay * 20L);
    }

    private static int cooldownDoCargo(MRClans plugin, Cargo cargo) {
        return switch (cargo) {
            case LIDER -> plugin.getConfigMr().getCooldownLider();
            case MODERADOR -> plugin.getConfigMr().getCooldownModerador();
            case MEMBRO -> plugin.getConfigMr().getCooldownMembro();
        };
    }
}
