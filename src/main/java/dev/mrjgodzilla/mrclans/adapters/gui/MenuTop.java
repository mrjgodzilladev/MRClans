package dev.mrjgodzilla.mrclans.adapters.gui;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class MenuTop {
    public static final String TITULO = "MRClans › Ranking";

    public static void abrir(Player jogador, MRClans plugin) {
        String metrica = plugin.getConfigMr().getRankingMetrica();
        List<Clan> ranking = plugin.getGerenciador().ranking(metrica);
        Inventory inv = Bukkit.createInventory(null, 27, Mensagens.cor("&8" + TITULO));

        if (ranking.isEmpty()) {
            inv.setItem(13, MenuPrincipal.item(Material.BARRIER, "&cNenhum clã", List.of("&7Ainda não há clãs.")));
        } else {
            int slot = 0;
            for (int i = 0; i < Math.min(18, ranking.size()); i++) {
                Clan c = ranking.get(i);
                inv.setItem(slot++, MenuPrincipal.item(Material.GOLD_INGOT,
                        "&e#" + (i + 1) + " &b" + c.getTag(),
                        List.of(
                                "&7Nome: &f" + c.getNome(),
                                "&7Kills: &f" + c.getEstatisticas().getKills(),
                                "&7KDR: &f" + String.format("%.2f", c.getEstatisticas().getKdr()),
                                "&7Membros: &f" + c.getMembros().size()
                        )));
            }
        }
        inv.setItem(22, MenuPrincipal.item(Material.ARROW, "&7Fechar", List.of("&7Clique para fechar")));
        jogador.openInventory(inv);
    }
}
