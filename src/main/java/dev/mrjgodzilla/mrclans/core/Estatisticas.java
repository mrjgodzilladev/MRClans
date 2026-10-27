package dev.mrjgodzilla.mrclans.core;

/** Estatísticas de kills/deaths de um clã ou membro. */
public class Estatisticas {
    private int kills;
    private int deaths;

    public Estatisticas() {
    }

    public Estatisticas(int kills, int deaths) {
        this.kills = kills;
        this.deaths = deaths;
    }

    public int getKills() { return kills; }
    public int getDeaths() { return deaths; }

    public void adicionarKill() { this.kills++; }
    public void adicionarDeath() { this.deaths++; }

    /** KDR = kills / deaths. Evita divisão por zero. */
    public double getKdr() {
        return deaths == 0 ? kills : (double) kills / deaths;
    }
}
