package dev.mrjgodzilla.mrclans.adapters.gui;

import dev.mrjgodzilla.mrclans.adapters.comandos.ComandoClanChat;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MenuPrincipal {
    public static final String TITULO_PREFIXO = "MRClans › ";

    public static void abrir(Player jogador, Clan clan) {
        Inventory inv = Bukkit.createInventory(null, 45, Mensagens.cor("&8" + TITULO_PREFIXO + clan.getTag()));

        String mottoLine = clan.getMotto().isEmpty() ? "&8sem motto" : "&7\"" + clan.getMotto() + "\"";
        inv.setItem(10, item(Material.BOOK, "&eInformações", List.of(
                "&7Nome: &f" + clan.getNome(),
                "&7Tag: &b" + clan.getTag(),
                mottoLine,
                "&7Membros: &f" + clan.getMembros().size(),
                "&7Kills: &f" + clan.getEstatisticas().getKills()
                        + " &7| KDR: &f" + String.format("%.2f", clan.getEstatisticas().getKdr()),
                "&7PvP: " + (clan.isPvpAtivo() ? "&aON" : "&cOFF")
        )));
        inv.setItem(11, item(Material.PLAYER_HEAD, "&eMembros", List.of("&7Lista com cargo e stats")));
        inv.setItem(12, item(Material.GOLDEN_HELMET, "&eCargos", List.of("&7O que cada cargo pode fazer")));
        inv.setItem(13, item(Material.WRITABLE_BOOK, "&eDiário", List.of("&7Últimos eventos")));
        inv.setItem(14, item(Material.GOLD_INGOT, "&eRanking", List.of("&7Top clãs")));
        inv.setItem(15, item(Material.COMPASS, "&eOnline", List.of("&7Quem do clã está online", "&7Atalho: &b/clan online")));

        boolean chatOn = ComandoClanChat.estaNoModoChat(jogador.getUniqueId());
        inv.setItem(19, item(Material.PAPER, chatOn ? "&aChat do clã: ON" : "&cChat do clã: OFF",
                List.of("&7Clique para alternar")));
        inv.setItem(20, item(Material.IRON_SWORD,
                clan.isPvpAtivo() ? "&aPvP interno: ON" : "&cPvP interno: OFF",
                List.of("&7Só líder", "&7Friendly fire")));
        inv.setItem(21, item(Material.RED_BED, "&eIr para home", List.of("&7Delay + cooldown por cargo")));
        inv.setItem(22, item(Material.LODESTONE, "&eDefinir home", List.of("&7Só líder", "&7Local atual")));
        inv.setItem(23, item(Material.EMERALD, "&aConvidar", List.of(
                "&7No chat: &b/clan convidar <nick>"
        )));
        inv.setItem(24, item(Material.NAME_TAG, "&ePromover / rebaixar", List.of(
                "&b/clan promover <nick>",
                "&b/clan rebaixar <nick>"
        )));

        inv.setItem(28, item(Material.OAK_DOOR, "&cSair do clã", List.of("&7Líder: desfaz o clã")));
        inv.setItem(29, item(Material.BARRIER, "&4Desfazer clã", List.of(
                "&7Só líder",
                "&b/clan desfazer confirm"
        )));
        inv.setItem(30, item(Material.SHIELD, "&eAliança / Rival", List.of(
                "&b/clan aliar <tag>",
                "&b/clan rival <tag>",
                "&b/clan neutro <tag>"
        )));

        inv.setItem(40, item(Material.ARROW, "&7Fechar", List.of()));

        jogador.openInventory(inv);
    }

    public static ItemStack item(Material mat, String nome, List<String> lore) {
        ItemStack it = new ItemStack(mat);
        ItemMeta meta = it.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(Mensagens.cor(nome));
            meta.setLore(lore.stream().map(Mensagens::cor).toList());
            it.setItemMeta(meta);
        }
        return it;
    }
}
