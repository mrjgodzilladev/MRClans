package dev.mrjgodzilla.mrclans.adapters.gui;

import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class MenuCargos {
    public static final String TITULO_PREFIXO = "MRClans › Cargos ";

    public static void abrir(Player jogador, Clan clan) {
        Inventory inv = Bukkit.createInventory(null, 27, Mensagens.cor("&8" + TITULO_PREFIXO + clan.getTag()));

        inv.setItem(11, MenuPrincipal.item(Material.GOLDEN_HELMET, "&6Líder", List.of(
                "&7Convidar: &a✔",
                "&7Expulsar: &a✔",
                "&7Desfazer: &a✔",
                "&7SetHome: &a✔",
                "&7PvP: &a✔"
        )));
        inv.setItem(13, MenuPrincipal.item(Material.IRON_HELMET, "&7Moderador", List.of(
                "&7Convidar: &a✔",
                "&7Expulsar: &a✔",
                "&7Desfazer: &c✘",
                "&7SetHome: &c✘",
                "&7PvP: &c✘"
        )));
        inv.setItem(15, MenuPrincipal.item(Material.LEATHER_HELMET, "&fMembro", List.of(
                "&7Convidar: &c✘",
                "&7Expulsar: &c✘",
                "&7Desfazer: &c✘",
                "&7Chat / Home / Sair: &a✔"
        )));
        inv.setItem(22, MenuPrincipal.item(Material.ARROW, "&7Voltar", List.of("&7Voltar ao menu principal")));

        jogador.openInventory(inv);
    }
}
