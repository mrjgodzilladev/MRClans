package dev.mrjgodzilla.mrclans.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/** Lê o config.yml. Valor inválido vira default e aviso no log. */
public class Config {
    private final int maxMembros;
    private final int conviteExpiraSegundos;
    private final int autoSaveSegundos;
    private final boolean tagColorida;
    private final String formatoChat;
    private final int homeDelay;
    private final int cooldownLider;
    private final int cooldownModerador;
    private final int cooldownMembro;
    private final int diarioMaxEventos;
    private final String rankingMetrica;
    private final int nomeMin;
    private final int nomeMax;
    private final int tagMin;
    private final int tagMax;

    public Config(JavaPlugin plugin) {
        FileConfiguration c = plugin.getConfig();

        this.maxMembros = Math.max(2, c.getInt("cla.max-membros", 20));
        this.conviteExpiraSegundos = Math.max(10, c.getInt("cla.convite-expira-segundos", 300));
        this.autoSaveSegundos = Math.max(0, c.getInt("persistencia.auto-save-segundos", 300));
        this.tagColorida = c.getBoolean("chat.tag-colorida", true);
        this.formatoChat = c.getString("chat.formato", "&7[{tag}&7] &f{jogador}&7: &f{mensagem}");
        this.homeDelay = Math.max(0, c.getInt("home.delay-segundos", 3));
        this.cooldownLider = Math.max(0, c.getInt("home.cooldown.lider", 60));
        this.cooldownModerador = Math.max(0, c.getInt("home.cooldown.moderador", 120));
        this.cooldownMembro = Math.max(0, c.getInt("home.cooldown.membro", 300));
        this.diarioMaxEventos = Math.max(10, c.getInt("diario.max-eventos", 50));
        this.rankingMetrica = c.getString("ranking.metrica", "kills");
        this.nomeMin = Math.max(1, c.getInt("cla.nome-min", 3));
        this.nomeMax = Math.max(nomeMin, c.getInt("cla.nome-max", 16));
        this.tagMin = Math.max(1, c.getInt("cla.tag-min", 2));
        this.tagMax = Math.max(tagMin, c.getInt("cla.tag-max", 5));

        if (c.getInt("cla.max-membros", 20) < 2) {
            plugin.getLogger().warning("cla.max-membros < 2 é inválido. Usando " + maxMembros + ".");
        }
    }

    public int getMaxMembros() { return maxMembros; }
    public int getConviteExpiraSegundos() { return conviteExpiraSegundos; }
    public int getAutoSaveSegundos() { return autoSaveSegundos; }
    public boolean isTagColorida() { return tagColorida; }
    public String getFormatoChat() { return formatoChat; }
    public int getHomeDelay() { return homeDelay; }
    public int getCooldownLider() { return cooldownLider; }
    public int getCooldownModerador() { return cooldownModerador; }
    public int getCooldownMembro() { return cooldownMembro; }
    public int getDiarioMaxEventos() { return diarioMaxEventos; }
    public String getRankingMetrica() { return rankingMetrica; }
    public int getNomeMin() { return nomeMin; }
    public int getNomeMax() { return nomeMax; }
    public int getTagMin() { return tagMin; }
    public int getTagMax() { return tagMax; }
}
