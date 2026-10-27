package dev.mrjgodzilla.mrclans.core;

/** Líder > Moderador > Membro. */
public enum Cargo {
    LIDER,
    MODERADOR,
    MEMBRO;

    /** true se este cargo tem poder >= ao comparado. */
    public boolean ehIgualOuSuperior(Cargo outro) {
        return this.ordinal() <= outro.ordinal();
    }
}
