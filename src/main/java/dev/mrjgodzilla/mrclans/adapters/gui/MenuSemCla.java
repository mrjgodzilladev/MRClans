package dev.mrjgodzilla.mrclans.adapters.gui;

import dev.mrjgodzilla.mrclans.config.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

/** Menu quando o jogador ainda não tem clã. */
public class MenuSemCla {
    public static final String TITULO = "MRClans › Sem clã";

    public static void abrir(Player jogador) {
        Inventory inv = Bukkit.createInventory(null, 27, Mensagens.cor("&8" + TITULO));

        inv.setItem(11, MenuPrincipal.item(Material.EMERALD_BLOCK, "&aCriar clã", List.of(
                "&7Digite no chat:",
                "&b/clan criar <nome> <tag>",
                "&7Exemplo: &f/clan criar Dragons DRG",
                "&7Nome: 3–16  |  Tag: 2–5 (A-Z 0-9)"
        )));
        inv.setItem(13, MenuPrincipal.item(Material.GOLD_INGOT, "&eRanking", List.of(
                "&7Top clãs do servidor"
        )));
        inv.setItem(15, MenuPrincipal.item(Material.BOOK, "&eAjuda", List.of(
                "&b/clan criar <nome> <tag>",
                "&b/clan entrar <tag>",
                "&b/clan top",
                "&7Aliases EN: create, join"
        )));
        inv.setItem(22, MenuPrincipal.item(Material.ARROW, "&7Fechar", List.of("&7Clique para fechar")));

        jogador.openInventory(inv);
    }
}
