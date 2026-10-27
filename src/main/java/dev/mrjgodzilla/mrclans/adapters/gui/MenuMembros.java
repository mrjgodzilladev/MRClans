package dev.mrjgodzilla.mrclans.adapters.gui;

import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class MenuMembros {
    public static final String TITULO_PREFIXO = "MRClans › Membros ";

    public static void abrir(Player jogador, Clan clan) {
        int tamanho = ((clan.getMembros().size() - 1) / 9 + 1) * 9;
        tamanho = Math.max(9, Math.min(54, tamanho));
        Inventory inv = Bukkit.createInventory(null, tamanho, Mensagens.cor("&8" + TITULO_PREFIXO + clan.getTag()));

        int slot = 0;
        for (Membro m : clan.getMembros().values()) {
            if (slot >= tamanho) break;
            inv.setItem(slot++, MenuPrincipal.item(Material.PLAYER_HEAD,
                    "&e" + m.getNome(),
                    List.of(
                            "&7Cargo: &f" + m.getCargo().name(),
                            "&7Kills: &f" + m.getEstatisticas().getKills(),
                            "&7Mortes: &f" + m.getEstatisticas().getDeaths()
                    )));
        }
        jogador.openInventory(inv);
    }
}
