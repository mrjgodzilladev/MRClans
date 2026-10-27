package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Cargo;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.entity.Player;

public final class ComandoClanDesfazer {
    private ComandoClanDesfazer() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        Clan c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (c == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cVocê não está em um clã."));
            return;
        }
        Membro m = c.getMembros().get(jogador.getUniqueId());
        if (m == null || m.getCargo() != Cargo.LIDER) {
            jogador.sendMessage(Mensagens.comPrefixo("&cSó o líder pode desfazer o clã."));
            return;
        }
        boolean confirmado = args.length >= 2 && args[1].equalsIgnoreCase("confirm");
        if (!confirmado) {
            jogador.sendMessage(Mensagens.comPrefixo("&cIsso apaga o clã &b" + c.getTag() + "&c para sempre."));
            jogador.sendMessage(Mensagens.cor("&eConfirme com: &b/clan desfazer confirm"));
            return;
        }
        c.registrarEvento(new EventoCla("DESFEITO", jogador.getName(), "desfez o clã"),
                plugin.getConfigMr().getDiarioMaxEventos());
        String tag = c.getTag();
        plugin.getGerenciador().desfazer(c);
        jogador.sendMessage(Mensagens.comPrefixo("&aClã &b" + tag + " &adesfeito."));
    }
}
