package dev.mrjgodzilla.mrclans;

import dev.mrjgodzilla.mrclans.adapters.comandos.ComandoClan;
import dev.mrjgodzilla.mrclans.adapters.gui.MenuListener;
import dev.mrjgodzilla.mrclans.adapters.listeners.ChatListener;
import dev.mrjgodzilla.mrclans.adapters.listeners.EntradaSaidaListener;
import dev.mrjgodzilla.mrclans.adapters.listeners.KillListener;
import dev.mrjgodzilla.mrclans.adapters.listeners.PvPListener;
import dev.mrjgodzilla.mrclans.adapters.placeholders.MRClansExpansion;
import dev.mrjgodzilla.mrclans.config.Config;
import dev.mrjgodzilla.mrclans.core.Clan;
import dev.mrjgodzilla.mrclans.core.GerenciadorClas;
import dev.mrjgodzilla.mrclans.persistencia.RepositorioJson;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public class MRClans extends JavaPlugin {
    private Config config;
    private GerenciadorClas gerenciador;
    private RepositorioJson repositorio;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.config = new Config(this);
        this.gerenciador = new GerenciadorClas(
                config.getMaxMembros(),
                config.getConviteExpiraSegundos()
        );
        this.repositorio = new RepositorioJson(this);

        Collection<Clan> carregados = repositorio.carregar();
        if (carregados != null) {
            int n = 0;
            for (Clan c : carregados) {
                if (c != null && c.getTag() != null) {
                    gerenciador.importar(c);
                    n++;
                }
            }
            getLogger().info("JSON: " + n + " clã(s) importado(s).");
        }

        PluginCommand cmd = getCommand("clan");
        if (cmd != null) {
            ComandoClan executor = new ComandoClan(this, gerenciador);
            cmd.setExecutor(executor);
            cmd.setTabCompleter(executor);
        } else {
            getLogger().severe("plugin.yml sem comando 'clan' — plugin inutilizável.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getPluginManager().registerEvents(new MenuListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PvPListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntradaSaidaListener(this), this);
        Bukkit.getPluginManager().registerEvents(new KillListener(this), this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new MRClansExpansion(this).register();
            getLogger().info("PlaceholderAPI ok.");
        }

        if (config.getAutoSaveSegundos() > 0) {
            long ticks = config.getAutoSaveSegundos() * 20L;
            getServer().getScheduler().runTaskTimerAsynchronously(
                    this,
                    () -> {
                        try {
                            repositorio.salvar(gerenciador.getTodos());
                        } catch (Exception e) {
                            getLogger().warning("Auto-save falhou: " + e.getMessage());
                        }
                    },
                    ticks, ticks
            );
        }

        getLogger().info("MRClans 1.0.0 online | clãs=" + gerenciador.getTodos().size()
                + " | /clan = GUI | staff: /clan admin");
    }

    @Override
    public void onDisable() {
        salvarAgora();
        getLogger().info("MRClans offline (save ok).");
    }

    /** Recarrega config.yml em runtime (staff). Não recarrega JSON de clãs. */
    public void reloadConfigMr() {
        reloadConfig();
        this.config = new Config(this);
    }

    public void salvarAgora() {
        if (repositorio != null && gerenciador != null) {
            try {
                repositorio.salvar(gerenciador.getTodos());
            } catch (Exception e) {
                getLogger().severe("Falha ao salvar clans.json: " + e.getMessage());
            }
        }
    }

    public Config getConfigMr() { return config; }
    public GerenciadorClas getGerenciador() { return gerenciador; }
}
