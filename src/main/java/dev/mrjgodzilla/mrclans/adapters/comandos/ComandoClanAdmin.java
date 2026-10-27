package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.config.Perms;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/** Comandos de staff (/clan admin). */
public final class ComandoClanAdmin {
    private ComandoClanAdmin() { }

    public static void executar(MRClans plugin, Player staff, String[] args) {
        if (!Perms.isAdmin(staff)) {
            Mensagens.enviar(staff, "&cSem permissão (mrclans.admin).");
            return;
        }
        if (args.length < 2) {
            enviarAjuda(staff);
            return;
        }
        String sub = args[1].toLowerCase();
        switch (sub) {
            case "reload" -> {
                if (!staff.hasPermission(Perms.ADMIN_RELOAD) && !Perms.isAdmin(staff)) {
                    Mensagens.enviar(staff, "&cSem permissão.");
                    return;
                }
                plugin.reloadConfigMr();
                Mensagens.enviar(staff, "&aConfig recarregada.");
            }
            case "desfazer", "disband" -> {
                if (args.length < 3) {
                    Mensagens.enviar(staff, "&eUso: /clan admin desfazer <tag>");
                    return;
                }
                Clan c = plugin.getGerenciador().getClaPorTag(args[2]);
                if (c == null) {
                    Mensagens.enviar(staff, "&cClã não encontrado.");
                    return;
                }
                String tag = c.getTag();
                c.registrarEvento(new EventoCla("ADMIN_DESFEITO", staff.getName(), "staff desfez o clã"),
                        plugin.getConfigMr().getDiarioMaxEventos());
                plugin.getGerenciador().desfazer(c);
                plugin.salvarAgora();
                Mensagens.enviar(staff, "&aClã &b" + tag + " &adesfeito por staff.");
            }
            case "forcarentrar", "forcejoin" -> {
                if (args.length < 4) {
                    Mensagens.enviar(staff, "&eUso: /clan admin forcarentrar <jogador> <tag>");
                    return;
                }
                Player alvo = Bukkit.getPlayerExact(args[2]);
                if (alvo == null) {
                    Mensagens.enviar(staff, "&cJogador offline.");
                    return;
                }
                Clan c = plugin.getGerenciador().getClaPorTag(args[3]);
                if (c == null) {
                    Mensagens.enviar(staff, "&cClã não encontrado.");
                    return;
                }
                if (plugin.getGerenciador().getClaDoJogador(alvo.getUniqueId()) != null) {
                    Mensagens.enviar(staff, "&cJogador já está em um clã. Tire-o antes.");
                    return;
                }
                // força via convite + aceitar interno
                plugin.getGerenciador().convidar(c, alvo.getUniqueId());
                boolean ok = plugin.getGerenciador().aceitarConvite(c, alvo.getUniqueId(), alvo.getName());
                if (ok) {
                    c.registrarEvento(new EventoCla("ADMIN_JOIN", staff.getName(),
                            "colocou " + alvo.getName() + " no clã"), plugin.getConfigMr().getDiarioMaxEventos());
                    Mensagens.enviar(staff, "&a" + alvo.getName() + " entrou em &b" + c.getTag());
                    Mensagens.enviar(alvo, "&eStaff colocou você no clã &b" + c.getTag());
                } else {
                    Mensagens.enviar(staff, "&cFalha (clã cheio?).");
                }
            }
            case "spy" -> {
                if (!staff.hasPermission(Perms.ADMIN_SPY) && !Perms.isAdmin(staff)) {
                    Mensagens.enviar(staff, "&cSem permissão.");
                    return;
                }
                boolean on = ComandoClanChat.toggleSpy(staff.getUniqueId());
                Mensagens.enviar(staff, on ? "&aSpy do chat de clãs &lON" : "&eSpy do chat de clãs &lOFF");
            }
            case "salvar", "save" -> {
                plugin.salvarAgora();
                Mensagens.enviar(staff, "&aClans salvos em disco.");
            }
            case "info" -> {
                Mensagens.enviar(staff, "&eClãs carregados: &f" + plugin.getGerenciador().getTodos().size());
                Mensagens.enviar(staff, "&eMax membros: &f" + plugin.getGerenciador().getMaxMembros());
            }
            default -> enviarAjuda(staff);
        }
    }

    private static void enviarAjuda(Player staff) {
        Mensagens.enviar(staff, "&6Staff — /clan admin");
        staff.sendMessage(Mensagens.cor("&7reload &8| &7desfazer <tag> &8| &7forcarentrar <player> <tag>"));
        staff.sendMessage(Mensagens.cor("&7spy &8| &7salvar &8| &7info"));
    }
}
