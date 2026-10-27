package dev.mrjgodzilla.mrclans.adapters.placeholders;

import dev.mrjgodzilla.mrclans.MRClans;
import dev.mrjgodzilla.mrclans.core.Clan;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

/** Placeholders do PAPI, se o plugin estiver no server. */
public class MRClansExpansion extends PlaceholderExpansion {
    private final MRClans plugin;

    public MRClansExpansion(MRClans plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "mrclans";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MRJGodzilla";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) return "";
        Clan c = plugin.getGerenciador().getClaDoJogador(player.getUniqueId());
        if (c == null) {
            return switch (params.toLowerCase()) {
                case "tag", "nome" -> "";
                case "kdr", "kills" -> "0";
                default -> null;
            };
        }
        return switch (params.toLowerCase()) {
            case "tag" -> c.getTag();
            case "nome" -> c.getNome();
            case "kdr" -> String.format("%.2f", c.getEstatisticas().getKdr());
            case "kills" -> String.valueOf(c.getEstatisticas().getKills());
            default -> null;
        };
    }
}
