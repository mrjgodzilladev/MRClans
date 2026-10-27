package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Cargo;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.entity.Player;

public final class ComandoClanMotto {
    private ComandoClanMotto() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        Clan c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (c == null) {
            Mensagens.enviar(jogador, "&cVocê não está em um clã.");
            return;
        }
        if (args.length < 2) {
            String m = c.getMotto();
            Mensagens.enviar(jogador, m.isEmpty()
                    ? "&7Seu clã ainda não tem motto. &e/clan motto <texto>"
                    : "&eMotto: &f" + m);
            return;
        }
        Membro mem = c.getMembro(jogador.getUniqueId());
        if (mem == null || mem.getCargo() != Cargo.LIDER) {
            Mensagens.enviar(jogador, "&cSó o líder define o motto.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i > 1) sb.append(' ');
            sb.append(args[i]);
        }
        String motto = sb.toString();
        if (motto.length() > 40) {
            Mensagens.enviar(jogador, "&cMotto no máximo 40 caracteres.");
            return;
        }
        c.setMotto(motto);
        Mensagens.enviar(jogador, "&aMotto atualizado: &f" + motto);
    }
}
