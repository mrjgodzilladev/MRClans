package dev.mrjgodzilla.mrclans.config;

import org.bukkit.entity.Player;

/** Nodes de permissão usados no plugin. */
public final class Perms {
    private Perms() { }

    public static final String USAR = "mrclans.usar";
    public static final String CRIAR = "mrclans.criar";
    public static final String ADMIN = "mrclans.admin";
    public static final String ADMIN_RELOAD = "mrclans.admin.reload";
    public static final String ADMIN_DESFAZER = "mrclans.admin.desfazer";
    public static final String ADMIN_SPY = "mrclans.admin.spy";

    public static boolean podeUsar(Player p) {
        return p.hasPermission(USAR) || p.hasPermission(ADMIN);
    }

    public static boolean podeCriar(Player p) {
        return p.hasPermission(CRIAR) || p.hasPermission(ADMIN);
    }

    public static boolean isAdmin(Player p) {
        return p.hasPermission(ADMIN);
    }
}
