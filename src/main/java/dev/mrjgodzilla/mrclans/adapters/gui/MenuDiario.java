package dev.mrjgodzilla.mrclans.adapters.gui;

import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class MenuDiario {
    public static final String TITULO_PREFIXO = "MRClans › Diário ";

    public static void abrir(Player jogador, Clan clan) {
        List<EventoCla> diario = clan.getDiario();
        int tamanho = Math.max(9, Math.min(54, ((Math.max(diario.size(), 1) - 1) / 9 + 1) * 9));
        Inventory inv = Bukkit.createInventory(null, tamanho, Mensagens.cor("&8" + TITULO_PREFIXO + clan.getTag()));

        if (diario.isEmpty()) {
            inv.setItem(4, MenuPrincipal.item(Material.PAPER, "&7Sem eventos", List.of("&7O diário está vazio.")));
        } else {
            int slot = 0;
            // Mostra do mais recente para o mais antigo
            for (int i = diario.size() - 1; i >= 0 && slot < tamanho; i--) {
                EventoCla e = diario.get(i);
                inv.setItem(slot++, MenuPrincipal.item(Material.PAPER,
                        "&e" + e.getTipo(),
                        List.of("&7Jogador: &f" + e.getJogador(), "&7" + e.getDescricao())));
            }
        }
        jogador.openInventory(inv);
    }
}
