package dev.mrjgodzilla.mrclans.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** Dados do clã. Home vira mundo+coords pra salvar limpo no JSON. */
public class Clan {
    private final String nome;
    private final String tag;
    private final UUID lider;
    private final long criadoEm;
    private String motto = "";
    private final Map<UUID, Membro> membros = new HashMap<>();
    private final Map<String, Relacao> relacoes = new HashMap<>();
    private final List<EventoCla> diario = new ArrayList<>();
    private final Estatisticas estatisticas = new Estatisticas();
    private boolean pvpAtivo = false;

    private String homeMundo;
    private double homeX, homeY, homeZ;
    private float homeYaw, homePitch;
    private boolean temHome = false;

    public Clan(String nome, String tag, UUID lider) {
        this.nome = nome;
        this.tag = tag;
        this.lider = lider;
        this.criadoEm = System.currentTimeMillis();
    }

    public String getNome() { return nome; }
    public String getTag() { return tag; }
    public UUID getLider() { return lider; }
    public long getCriadoEm() { return criadoEm; }
    public String getMotto() { return motto == null ? "" : motto; }
    public void setMotto(String motto) { this.motto = motto == null ? "" : motto; }

    public Map<UUID, Membro> getMembros() { return membros; }
    public Estatisticas getEstatisticas() { return estatisticas; }
    public boolean isPvpAtivo() { return pvpAtivo; }
    public void setPvpAtivo(boolean pvpAtivo) { this.pvpAtivo = pvpAtivo; }

    public boolean temHome() { return temHome; }
    public String getHomeMundo() { return homeMundo; }
    public double getHomeX() { return homeX; }
    public double getHomeY() { return homeY; }
    public double getHomeZ() { return homeZ; }
    public float getHomeYaw() { return homeYaw; }
    public float getHomePitch() { return homePitch; }

    public void setHome(String mundo, double x, double y, double z, float yaw, float pitch) {
        this.homeMundo = mundo;
        this.homeX = x;
        this.homeY = y;
        this.homeZ = z;
        this.homeYaw = yaw;
        this.homePitch = pitch;
        this.temHome = true;
    }

    public Membro getMembro(UUID uuid) {
        return membros.get(uuid);
    }

    public void adicionarMembro(Membro m) { membros.put(m.getUuid(), m); }
    public void removerMembro(UUID uuid) { membros.remove(uuid); }
    public boolean temMembro(UUID uuid) { return membros.containsKey(uuid); }

    public void adicionarRelacao(String tagOutro, Relacao relacao) {
        relacoes.put(tagOutro.toLowerCase(), relacao);
    }
    public void removerRelacao(String tagOutro) { relacoes.remove(tagOutro.toLowerCase()); }
    public Map<String, Relacao> getRelacoes() { return relacoes; }
    public Relacao getRelacao(String tagOutro) {
        return relacoes.getOrDefault(tagOutro.toLowerCase(), Relacao.NEUTRO);
    }

    public void registrarEvento(EventoCla evento, int maxEventos) {
        diario.add(evento);
        while (diario.size() > maxEventos) diario.remove(0);
    }
    public List<EventoCla> getDiario() { return diario; }

    public int membrosOnline(java.util.function.Predicate<UUID> isOnline) {
        int n = 0;
        for (UUID id : membros.keySet()) {
            if (isOnline.test(id)) n++;
        }
        return n;
    }
}
