package dev.mrjgodzilla.mrclans.adapters.gui;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.adapters.comandos.ComandoClanChat;
import dev.mrjgodzilla.mrclans.adapters.comandos.ComandoClanDesfazer;
import dev.mrjgodzilla.mrclans.adapters.comandos.ComandoClanHome;
import dev.mrjgodzilla.mrclans.adapters.comandos.ComandoClanOnline;
import dev.mrjgodzilla.mrclans.adapters.comandos.ComandoClanPvp;
import dev.mrjgodzilla.mrclans.adapters.comandos.ComandoClanSair;
import dev.mrjgodzilla.mrclans.adapters.comandos.ComandoClanSetHome;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {
    private final MRClans plugin;

    public MenuListener(MRClans plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String titulo = event.getView().getTitle();
        boolean principal = titulo.contains(MenuPrincipal.TITULO_PREFIXO);
        boolean semCla = titulo.contains(MenuSemCla.TITULO);
        boolean membros = titulo.contains(MenuMembros.TITULO_PREFIXO);
        boolean cargos = titulo.contains(MenuCargos.TITULO_PREFIXO);
        boolean diario = titulo.contains(MenuDiario.TITULO_PREFIXO);
        boolean top = titulo.contains(MenuTop.TITULO);

        if (!principal && !semCla && !membros && !cargos && !diario && !top) return;

        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player jogador)) return;
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

        int slot = event.getSlot();

        if (semCla) {
            switch (slot) {
                case 11 -> jogador.sendMessage(Mensagens.comPrefixo(
                        "&eDigite: &b/clan criar <nome> <tag> &7(ex: &f/clan criar Dragons DRG&7)"));
                case 13 -> MenuTop.abrir(jogador, plugin);
                case 15 -> jogador.sendMessage(Mensagens.comPrefixo(
                        "&eCriar, entrar, ranking: use a GUI ou &b/clan ajuda"));
                case 22 -> jogador.closeInventory();
                default -> { }
            }
            return;
        }

        if (top) {
            if (slot == 22) jogador.closeInventory();
            return;
        }

        Clan clan = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (clan == null) {
            jogador.closeInventory();
            return;
        }

        if (membros || diario) return;
        if (cargos) {
            if (slot == 22) MenuPrincipal.abrir(jogador, clan);
            return;
        }

        if (principal) {
            switch (slot) {
                case 11 -> MenuMembros.abrir(jogador, clan);
                case 12 -> MenuCargos.abrir(jogador, clan);
                case 13 -> MenuDiario.abrir(jogador, clan);
                case 14 -> MenuTop.abrir(jogador, plugin);
                case 15 -> {
                    jogador.closeInventory();
                    ComandoClanOnline.executar(plugin, jogador);
                }
                case 19 -> {
                    jogador.closeInventory();
                    ComandoClanChat.executar(plugin, jogador);
                }
                case 20 -> {
                    jogador.closeInventory();
                    ComandoClanPvp.executar(plugin, jogador);
                }
                case 21 -> {
                    jogador.closeInventory();
                    ComandoClanHome.executar(plugin, jogador);
                }
                case 22 -> {
                    jogador.closeInventory();
                    ComandoClanSetHome.executar(plugin, jogador);
                }
                case 23 -> jogador.sendMessage(Mensagens.comPrefixo("&e/clan convidar <nick>"));
                case 24 -> jogador.sendMessage(Mensagens.comPrefixo(
                        "&e/clan promover <nick> &7| &b/clan rebaixar <nick>"));
                case 28 -> {
                    jogador.closeInventory();
                    ComandoClanSair.executar(plugin, jogador);
                }
                case 29 -> {
                    jogador.closeInventory();
                    jogador.sendMessage(Mensagens.comPrefixo("&cPara desfazer: &b/clan desfazer confirm"));
                }
                case 30 -> jogador.sendMessage(Mensagens.comPrefixo(
                        "&e/clan aliar <tag> &7| &b/clan rival <tag> &7| &b/clan neutro <tag>"));
                case 40 -> jogador.closeInventory();
                default -> { }
            }
        }
    }
}
