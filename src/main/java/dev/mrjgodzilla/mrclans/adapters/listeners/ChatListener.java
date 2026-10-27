package dev.mrjgodzilla.mrclans.adapters.listeners;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.adapters.comandos.ComandoClanChat;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {
    private final MRClans plugin;

    public ChatListener(MRClans plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onChat(AsyncChatEvent event) {
        Player jogador = event.getPlayer();
        Clan clan = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (clan == null) return;

        String mensagem = LegacyComponentSerializer.legacySection().serialize(event.message());

        if (ComandoClanChat.estaNoModoChat(jogador.getUniqueId())) {
            event.setCancelled(true);
            Component formatado = LegacyComponentSerializer.legacyAmpersand()
                    .deserialize("&8[&b" + clan.getTag() + "&8] &f" + jogador.getName() + "&7: &f" + mensagem);
            Component spyLine = LegacyComponentSerializer.legacyAmpersand()
                    .deserialize("&8[&cSPY&8][&b" + clan.getTag() + "&8] &f" + jogador.getName() + "&7: &f" + mensagem);

            Bukkit.getScheduler().runTask(plugin, () -> {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (clan.getMembros().containsKey(online.getUniqueId())) {
                        online.sendMessage(formatado);
                    } else if (ComandoClanChat.estaSpy(online.getUniqueId())) {
                        online.sendMessage(spyLine);
                    }
                }
            });
            return;
        }

        if (plugin.getConfigMr().isTagColorida()) {
            String formato = plugin.getConfigMr().getFormatoChat()
                    .replace("{tag}", clan.getTag())
                    .replace("{jogador}", jogador.getName())
                    .replace("{mensagem}", mensagem);
            event.renderer((source, sourceDisplayName, message, viewer) ->
                    LegacyComponentSerializer.legacyAmpersand().deserialize(formato));
        }
    }
}
