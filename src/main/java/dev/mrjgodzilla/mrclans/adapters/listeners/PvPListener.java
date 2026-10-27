package dev.mrjgodzilla.mrclans.adapters.listeners;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.core.Clan;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/** Bloqueia friendly fire se o PvP do clã estiver off. */
public class PvPListener implements Listener {
    private final MRClans plugin;

    public PvPListener(MRClans plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player atacante)) return;
        if (!(event.getEntity() instanceof Player vitima)) return;

        Clan cA = plugin.getGerenciador().getClaDoJogador(atacante.getUniqueId());
        Clan cV = plugin.getGerenciador().getClaDoJogador(vitima.getUniqueId());
        if (cA == null || cV == null || cA != cV) return;

        if (!cA.isPvpAtivo()) {
            event.setCancelled(true);
        }
    }
}
