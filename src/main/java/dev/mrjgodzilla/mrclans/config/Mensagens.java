package dev.mrjgodzilla.mrclans.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

/** Textos do plugin. */
public final class Mensagens {
    private Mensagens() { }

    public static final String PREFIXO = "&8[&bMRClans&8] &r";
    private static final LegacyComponentSerializer LEGACY =
            LegacyComponentSerializer.legacyAmpersand();

    public static String cor(String texto) {
        // Mantém compat com APIs que ainda pedem String colorida
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', texto);
    }

    public static String comPrefixo(String texto) {
        return cor(PREFIXO + texto);
    }

    public static Component component(String textoComEComercial) {
        return LEGACY.deserialize(textoComEComercial);
    }

    public static void enviar(Player p, String textoComPrefixoLogic) {
        p.sendMessage(component(PREFIXO + textoComPrefixoLogic));
    }

    /** Convite clicável: aceitar / recusar. */
    public static void enviarConvite(Player alvo, String tag, String deQuem) {
        Component base = component(PREFIXO + "&e" + deQuem + " &7convidou você para &b" + tag);
        Component aceitar = component(" &a[ACEITAR]")
                .clickEvent(ClickEvent.runCommand("/clan aceitar " + tag))
                .hoverEvent(HoverEvent.showText(component("&aClique para entrar em " + tag)));
        Component recusar = component(" &c[RECUSAR]")
                .clickEvent(ClickEvent.runCommand("/clan recusar " + tag))
                .hoverEvent(HoverEvent.showText(component("&cRecusar convite")));
        alvo.sendMessage(base.append(aceitar).append(recusar));
    }
}
