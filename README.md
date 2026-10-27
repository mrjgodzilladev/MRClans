# MRClans

Plugin de clãs pro **Paper 1.21.x**.

Eu faço plugin pra funcionar de verdade: estável, com manual, e o código fica seu.
O MRClans segue essa linha — GUI no centro, chat como atalho, staff com ferramenta na mão, dados no próprio servidor.

## O que resolve

- Criar clã, convidar, aceitar/recusar, sair, expulsar, desfazer
- Cargos (líder / moderador / membro) com promover e rebaixar
- Chat de clã, tag no global, home, PvP entre membros
- Ranking (kills / KDR), diário, aliança e rivalidade
- Menu `/clan` + comandos opcionais
- Admin: reload, desfazer forçado, force join, spy no chat
- Save em JSON local · PlaceholderAPI se existir

## Build

```bat
gradlew.bat clean build
```

```bash
./gradlew clean build
```

Jar: `build/libs/MRClans-1.0.0.jar` → pasta `plugins/`.

Precisa de **JDK 21+** pra compilar. No server: Paper/Purpur 1.21.x.

> Se o `java -version` mostrar 26 e o Gradle reclamar, o wrapper já está em 9.4.
> Alternativa: aponta o PATH pro JDK 21 e builda de novo.

## Uso

```text
/clan                      menu
/clan criar Nome TAG       cria
/clan convidar Nick        convite (com botão)
/clan admin                staff (op)
```

## Manual

Tudo explicado no **[LEIA-ME.txt](LEIA-ME.txt)** — instalação, config, jogador, staff, como o código se organiza, como usar de base e como montar algo parecido do zero.

## Licença

MIT.

---

[mrjgodzilla.squareweb.app](https://mrjgodzilla.squareweb.app/) · Discord `@mrjgodzilla.dev`
