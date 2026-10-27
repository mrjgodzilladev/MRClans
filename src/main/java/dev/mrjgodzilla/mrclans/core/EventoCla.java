package dev.mrjgodzilla.mrclans.core;

import java.time.Instant;

/** Entrada do diário do clã. */
public class EventoCla {
    private final String tipo;
    private final String jogador;
    private final long timestamp;
    private final String descricao;

    public EventoCla(String tipo, String jogador, String descricao) {
        this.tipo = tipo;
        this.jogador = jogador;
        this.timestamp = Instant.now().toEpochMilli();
        this.descricao = descricao;
    }

    public String getTipo() { return tipo; }
    public String getJogador() { return jogador; }
    public long getTimestamp() { return timestamp; }
    public String getDescricao() { return descricao; }
}
