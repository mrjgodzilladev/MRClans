package dev.mrjgodzilla.mrclans.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** Regras do clã. Se a lógica errou, o bug é aqui. */
public class GerenciadorClas {
    private final Map<String, Clan> clasPorTag = new HashMap<>();
    private final Map<UUID, Clan> claDoJogador = new HashMap<>();
    /** tag do clã -> (uuid convidado -> expira em ms) */
    private final Map<String, Map<UUID, Long>> convites = new HashMap<>();

    private final int maxMembros;
    private final int conviteExpiraSegundos;

    public GerenciadorClas(int maxMembros, int conviteExpiraSegundos) {
        this.maxMembros = maxMembros;
        this.conviteExpiraSegundos = conviteExpiraSegundos;
    }

    public Clan getClaPorTag(String tag) {
        if (tag == null) return null;
        return clasPorTag.get(tag.toLowerCase());
    }

    public Clan getClaDoJogador(UUID uuid) {
        return claDoJogador.get(uuid);
    }

    public Collection<Clan> getTodos() {
        return clasPorTag.values();
    }

    public void importar(Clan clan) {
        clasPorTag.put(clan.getTag().toLowerCase(), clan);
        for (UUID uuid : clan.getMembros().keySet()) {
            claDoJogador.put(uuid, clan);
        }
    }

    public Clan criar(String nome, String tag, UUID lider, String nomeLider) {
        String key = tag.toLowerCase();
        if (clasPorTag.containsKey(key) || claDoJogador.containsKey(lider)) return null;

        Clan clan = new Clan(nome, tag, lider);
        clan.adicionarMembro(new Membro(lider, nomeLider, Cargo.LIDER));
        clasPorTag.put(key, clan);
        claDoJogador.put(lider, clan);
        return clan;
    }

    public boolean convidar(Clan clan, UUID convidado) {
        if (clan.temMembro(convidado)) return false;
        if (clan.getMembros().size() >= maxMembros) return false;
        if (claDoJogador.containsKey(convidado)) return false;

        long expira = System.currentTimeMillis() + (conviteExpiraSegundos * 1000L);
        convites.computeIfAbsent(clan.getTag().toLowerCase(), k -> new HashMap<>())
                .put(convidado, expira);
        return true;
    }

    /** Retorna a tag do convite válido mais recente, ou null. */
    public String tagDoConvitePendente(UUID jogador) {
        long agora = System.currentTimeMillis();
        String melhor = null;
        long melhorExpira = 0;
        for (Map.Entry<String, Map<UUID, Long>> e : convites.entrySet()) {
            Long exp = e.getValue().get(jogador);
            if (exp != null && exp >= agora && exp > melhorExpira) {
                melhor = e.getKey();
                melhorExpira = exp;
            }
        }
        return melhor;
    }

    public boolean aceitarConvite(Clan clan, UUID jogador, String nomeJogador) {
        Map<UUID, Long> doCla = convites.get(clan.getTag().toLowerCase());
        if (doCla == null) return false;
        Long expira = doCla.get(jogador);
        if (expira == null || expira < System.currentTimeMillis()) return false;
        if (clan.getMembros().size() >= maxMembros) return false;
        if (claDoJogador.containsKey(jogador)) return false;

        doCla.remove(jogador);
        clan.adicionarMembro(new Membro(jogador, nomeJogador, Cargo.MEMBRO));
        claDoJogador.put(jogador, clan);
        return true;
    }

    public boolean recusarConvite(UUID jogador, String tag) {
        Map<UUID, Long> doCla = convites.get(tag.toLowerCase());
        if (doCla == null) return false;
        return doCla.remove(jogador) != null;
    }

    public boolean sair(Clan clan, UUID jogador) {
        if (!clan.temMembro(jogador)) return false;
        if (clan.getLider().equals(jogador)) {
            desfazer(clan);
            return true;
        }
        clan.removerMembro(jogador);
        claDoJogador.remove(jogador);
        return true;
    }

    public boolean expulsar(Clan clan, UUID executor, UUID alvo) {
        Membro mExecutor = clan.getMembros().get(executor);
        Membro mAlvo = clan.getMembros().get(alvo);
        if (mExecutor == null || mAlvo == null) return false;
        if (mAlvo.getCargo() == Cargo.LIDER) return false;
        if (!mExecutor.getCargo().ehIgualOuSuperior(mAlvo.getCargo())) return false;
        // Moderador não expulsa outro moderador
        if (mExecutor.getCargo() == Cargo.MODERADOR && mAlvo.getCargo() == Cargo.MODERADOR) return false;

        clan.removerMembro(alvo);
        claDoJogador.remove(alvo);
        return true;
    }

    public void desfazer(Clan clan) {
        for (UUID uuid : new ArrayList<>(clan.getMembros().keySet())) {
            claDoJogador.remove(uuid);
        }
        clan.getMembros().clear();
        clasPorTag.remove(clan.getTag().toLowerCase());
        convites.remove(clan.getTag().toLowerCase());
    }

    public boolean promover(Clan clan, UUID executor, UUID alvo) {
        Membro mEx = clan.getMembros().get(executor);
        Membro mAlvo = clan.getMembros().get(alvo);
        if (mEx == null || mAlvo == null) return false;
        if (mEx.getCargo() != Cargo.LIDER) return false;
        if (alvo.equals(clan.getLider())) return false;
        if (mAlvo.getCargo() != Cargo.MEMBRO) return false;
        mAlvo.setCargo(Cargo.MODERADOR);
        return true;
    }

    public boolean rebaixar(Clan clan, UUID executor, UUID alvo) {
        Membro mEx = clan.getMembros().get(executor);
        Membro mAlvo = clan.getMembros().get(alvo);
        if (mEx == null || mAlvo == null) return false;
        if (mEx.getCargo() != Cargo.LIDER) return false;
        if (alvo.equals(clan.getLider())) return false;
        if (mAlvo.getCargo() != Cargo.MODERADOR) return false;
        mAlvo.setCargo(Cargo.MEMBRO);
        return true;
    }

    public boolean aliado(Clan a, Clan b) {
        if (a == null || b == null || a == b) return false;
        a.adicionarRelacao(b.getTag(), Relacao.ALIADO);
        b.adicionarRelacao(a.getTag(), Relacao.ALIADO);
        return true;
    }

    public boolean rival(Clan a, Clan b) {
        if (a == null || b == null || a == b) return false;
        a.adicionarRelacao(b.getTag(), Relacao.RIVAL);
        b.adicionarRelacao(a.getTag(), Relacao.RIVAL);
        return true;
    }

    public boolean removerRelacao(Clan a, Clan b) {
        if (a == null || b == null) return false;
        a.removerRelacao(b.getTag());
        b.removerRelacao(a.getTag());
        return true;
    }

    public List<Clan> ranking(String metrica) {
        List<Clan> lista = new ArrayList<>(clasPorTag.values());
        if ("kdr".equalsIgnoreCase(metrica)) {
            lista.sort((x, y) -> Double.compare(y.getEstatisticas().getKdr(), x.getEstatisticas().getKdr()));
        } else if ("membros".equalsIgnoreCase(metrica)) {
            lista.sort((x, y) -> Integer.compare(y.getMembros().size(), x.getMembros().size()));
        } else {
            lista.sort((x, y) -> Integer.compare(y.getEstatisticas().getKills(), x.getEstatisticas().getKills()));
        }
        return lista;
    }

    public void registrarKill(Clan assassino, Clan vitima) {
        if (assassino != null) assassino.getEstatisticas().adicionarKill();
        if (vitima != null) vitima.getEstatisticas().adicionarDeath();
    }

    public int getMaxMembros() {
        return maxMembros;
    }

    public int getConviteExpiraSegundos() {
        return conviteExpiraSegundos;
    }
}
