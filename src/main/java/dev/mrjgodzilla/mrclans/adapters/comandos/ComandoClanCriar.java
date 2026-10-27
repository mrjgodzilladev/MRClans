package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.adapters.gui.MenuPrincipal;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.config.Perms;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.EventoCla;
import org.bukkit.entity.Player;

public final class ComandoClanCriar {
    private ComandoClanCriar() { }

    public static void executar(MRClans plugin, Player jogador, String[] args) {
        if (!Perms.podeCriar(jogador)) {
            Mensagens.enviar(jogador, "&cSem permissão para criar clã (mrclans.criar).");
            return;
        }
        if (args.length < 3) {
            Mensagens.enviar(jogador, "&eUso: &b/clan criar <nome> <tag>");
            jogador.sendMessage(Mensagens.cor("&7Ex: &f/clan criar Dragons DRG"));
            return;
        }

        String nome = args[1];
        String tag = args[2];
        int nMin = plugin.getConfigMr().getNomeMin();
        int nMax = plugin.getConfigMr().getNomeMax();
        int tMin = plugin.getConfigMr().getTagMin();
        int tMax = plugin.getConfigMr().getTagMax();

        if (nome.length() < nMin || nome.length() > nMax) {
            Mensagens.enviar(jogador, "&cNome: entre " + nMin + " e " + nMax + " caracteres.");
            return;
        }
        if (!nome.matches("[A-Za-z0-9_\\-]+")) {
            Mensagens.enviar(jogador, "&cNome: só letras, números, _ e -.");
            return;
        }
        if (tag.length() < tMin || tag.length() > tMax) {
            Mensagens.enviar(jogador, "&cTag: entre " + tMin + " e " + tMax + " caracteres.");
            return;
        }
        if (!tag.matches("[A-Za-z0-9]+")) {
            Mensagens.enviar(jogador, "&cTag: só letras e números.");
            return;
        }
        if (plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId()) != null) {
            Mensagens.enviar(jogador, "&cVocê já está em um clã.");
            return;
        }
        if (plugin.getGerenciador().getClaPorTag(tag) != null) {
            Mensagens.enviar(jogador, "&cTag &b" + tag + " &cjá em uso.");
            return;
        }

        Clan c = plugin.getGerenciador().criar(nome, tag, jogador.getUniqueId(), jogador.getName());
        if (c == null) {
            Mensagens.enviar(jogador, "&cFalha ao criar. Tente outra tag.");
            return;
        }
        c.registrarEvento(new EventoCla("CRIADO", jogador.getName(), "criou o clã"),
                plugin.getConfigMr().getDiarioMaxEventos());
        plugin.salvarAgora();
        Mensagens.enviar(jogador, "&aClã &b" + tag + " &a(&f" + nome + "&a) criado!");
        MenuPrincipal.abrir(jogador, c);
    }
}
