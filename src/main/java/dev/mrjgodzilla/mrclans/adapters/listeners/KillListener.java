package dev.mrjgodzilla.mrclans.adapters.listeners;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.core.Clan;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/** Conta kill/death no clã. */
public class KillListener implements Listener {
    private final MRClans plugin;

    public KillListener(MRClans plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent event) {
        Player vitima = event.getEntity();
        Player assassino = vitima.getKiller();

        Clan claVitima = plugin.getGerenciador().getClaDoJogador(vitima.getUniqueId());
        Clan claAssassino = assassino != null
                ? plugin.getGerenciador().getClaDoJogador(assassino.getUniqueId())
                : null;

        if (claAssassino == null && claVitima == null) return;

        // Friendly fire com PvP off não conta
        if (claAssassino != null && claAssassino == claVitima && !claAssassino.isPvpAtivo()) {
            return;
        }

        plugin.getGerenciador().registrarKill(claAssassino, claVitima);
    }
}
