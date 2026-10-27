package dev.mrjgodzilla.mrclans.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.mrjgodzilla.mrclans.core.Clan;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/** Salva e carrega plugins/MRClans/clans.json. */
public class RepositorioJson {
    private static final int SCHEMA_VERSION = 1;
    private final JavaPlugin plugin;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File arquivo;

    public RepositorioJson(JavaPlugin plugin) {
        this.plugin = plugin;
        this.arquivo = new File(plugin.getDataFolder(), "clans.json");
    }

    private static class Dados {
        int schemaVersion = SCHEMA_VERSION;
        Collection<Clan> clans;
    }

    public void salvar(Collection<Clan> clans) {
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            Dados d = new Dados();
            d.clans = clans;
            try (Writer w = new OutputStreamWriter(new FileOutputStream(arquivo), StandardCharsets.UTF_8)) {
                gson.toJson(d, w);
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Falha ao salvar clans.json: " + e.getMessage());
        }
    }

    public Collection<Clan> carregar() {
        if (!arquivo.exists()) return null;
        try (Reader r = new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8)) {
            Dados d = gson.fromJson(r, Dados.class);
            if (d == null || d.clans == null) return null;
            return d.clans;
        } catch (Exception e) {
            plugin.getLogger().severe("Falha ao carregar clans.json: " + e.getMessage());
            return null;
        }
    }
}
