package dev.mrjgodzilla.mrclans.adapters.comandos;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.config.Mensagens;
import dev.mrjgodzilla.mrclans.core.Clan;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class ComandoClanChat {
    private static final Set<UUID> MODO_CHAT = new HashSet<>();
    private static final Set<UUID> SPY = new HashSet<>();

    private ComandoClanChat() { }

    public static boolean estaNoModoChat(UUID uuid) {
        return MODO_CHAT.contains(uuid);
    }

    public static boolean estaSpy(UUID uuid) {
        return SPY.contains(uuid);
    }

    public static boolean toggle(UUID uuid) {
        if (MODO_CHAT.contains(uuid)) {
            MODO_CHAT.remove(uuid);
            return false;
        }
        MODO_CHAT.add(uuid);
        return true;
    }

    public static boolean toggleSpy(UUID uuid) {
        if (SPY.contains(uuid)) {
            SPY.remove(uuid);
            return false;
        }
        SPY.add(uuid);
        return true;
    }

    public static void executar(MRClans plugin, Player jogador) {
        Clan c = plugin.getGerenciador().getClaDoJogador(jogador.getUniqueId());
        if (c == null) {
            Mensagens.enviar(jogador, "&cVocê não está em um clã.");
            return;
        }
        boolean ativo = toggle(jogador.getUniqueId());
        Mensagens.enviar(jogador, ativo
                ? "&eChat do clã &aativado&e. Fale no chat normal; só o clã vê."
                : "&eChat do clã &cdesativado&e.");
    }
}
