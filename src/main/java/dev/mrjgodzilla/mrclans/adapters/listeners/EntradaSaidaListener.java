package dev.mrjgodzilla.mrclans.adapters.listeners;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/** Atualiza nick e registra entrada/saída no diário. */
public class EntradaSaidaListener implements Listener {
    private final MRClans plugin;

    public EntradaSaidaListener(MRClans plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Clan c = plugin.getGerenciador().getClaDoJogador(event.getPlayer().getUniqueId());
        if (c == null) return;
        Membro m = c.getMembros().get(event.getPlayer().getUniqueId());
        if (m != null) m.setNome(event.getPlayer().getName());
        c.registrarEvento(new EventoCla("ENTROU", event.getPlayer().getName(), "entrou no servidor"),
                plugin.getConfigMr().getDiarioMaxEventos());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Clan c = plugin.getGerenciador().getClaDoJogador(event.getPlayer().getUniqueId());
        if (c == null) return;
        c.registrarEvento(new EventoCla("SAIU", event.getPlayer().getName(), "saiu do servidor"),
                plugin.getConfigMr().getDiarioMaxEventos());
    }
}
