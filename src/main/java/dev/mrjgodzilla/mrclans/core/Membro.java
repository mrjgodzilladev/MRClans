package dev.mrjgodzilla.mrclans.core;

import java.util.UUID;

/** Membro de um clã. */
public class Membro {
    private final UUID uuid;
    private String nome;
    private Cargo cargo;
    private final Estatisticas estatisticas;
    private final long entrouEm;

    public Membro(UUID uuid, String nome, Cargo cargo) {
        this.uuid = uuid;
        this.nome = nome;
        this.cargo = cargo;
        this.estatisticas = new Estatisticas();
        this.entrouEm = System.currentTimeMillis();
    }

    public UUID getUuid() { return uuid; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }
    public Estatisticas getEstatisticas() { return estatisticas; }
    public long getEntrouEm() { return entrouEm; }
}
