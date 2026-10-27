package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Cargo;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import dev.mrjgodzilla.mrclans.core.Membro;
import org.bukkit.entity.Player;

public final class ComandoClanAliar {
    private ComandoClanAliar() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        if (args.length < 2) {
            jogador.sendMessage(Mensagens.comPrefixo("&eUso: /clan aliar <tag>  &7ou  /clan rival <tag>  &7ou  /clan neutro <tag>"));
            return;
        }
        // args[0] = aliar | rival | neutro
        String acao = args[0].toLowerCase();
        Clan meu = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (meu == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cVocê não está em um clã."));
            return;
        }
        Membro m = meu.getMembros().get(jogador.getUniqueId());
        if (m == null || m.getCargo() != Cargo.LIDER) {
            jogador.sendMessage(Mensagens.comPrefixo("&cSó o líder define alianças/rivalidades."));
            return;
        }
        Clan outro = plugin.getGerenciador().getClaPorTag(args[1]);
        if (outro == null) {
            jogador.sendMessage(Mensagens.comPrefixo("&cClã não encontrado."));
            return;
        }
        if (outro == meu) {
            jogador.sendMessage(Mensagens.comPrefixo("&cNão dá para se aliar ao próprio clã."));
            return;
        }

        boolean ok;
        String msg;
        if (acao.equals("aliar") || acao.equals("ally")) {
            ok = plugin.getGerenciador().aliado(meu, outro);
            msg = ok ? "&aAliança formada com &b" + outro.getTag() : "&cFalha ao aliar.";
            if (ok) meu.registrarEvento(new EventoCla("ALIADO", jogador.getName(), "aliou-se a " + outro.getTag()),
                    plugin.getConfigMr().getDiarioMaxEventos());
        } else if (acao.equals("rival")) {
            ok = plugin.getGerenciador().rival(meu, outro);
            msg = ok ? "&cRivalidade declarada com &b" + outro.getTag() : "&cFalha ao rivalizar.";
            if (ok) meu.registrarEvento(new EventoCla("RIVAL", jogador.getName(), "declarou rival " + outro.getTag()),
                    plugin.getConfigMr().getDiarioMaxEventos());
        } else {
            ok = plugin.getGerenciador().removerRelacao(meu, outro);
            msg = ok ? "&eRelação com &b" + outro.getTag() + " &eremovida (neutro)." : "&cFalha.";
        }
        jogador.sendMessage(Mensagens.comPrefixo(msg));
    }
}
