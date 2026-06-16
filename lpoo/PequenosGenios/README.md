# Pequenos Gênios - CodeArena: Batalha do Conhecimento 🎓⚔️

Este é o projeto final do jogo de perguntas e respostas em turnos, desenvolvido para a disciplina de Linguagem de Programação Orientada a Objetos (LPOO). 

O jogador escolhe uma área de conhecimento e enfrenta uma campanha progressiva, usando estratégias e habilidades especiais polimórficas.

---

## 🛠️ Pré-requisitos

Para rodar o projeto, você vai precisar de:
- JDK (versão 17 ou superior).
- Apache Maven (versão 3.6 ou superior).

---

## 🔑 Configuração da API (OpenAI)

Como o jogo usa IA para gerar perguntas dinâmicas, siga estes passos:
1. Na pasta raiz do projeto (onde está o `pom.xml`), crie um arquivo chamado `api_key.txt`.
2. Cole sua chave secreta da OpenAI dentro dele (sem espaços ou linhas extras).
3. Salve. (O arquivo já está no `.gitignore` para sua segurança).

---

## 🚀 Como Executar

Abra o terminal na **pasta raiz** do projeto e rode os comandos:

1. Compilar:
   ```bash
   mvn clean compile

2. Rodar:

    Bash
    mvn exec:java -Dexec.mainClass="FuncaoMain.Main" 

Mecânicas
Personagens: Escolha entre Aluno de Humanas (MID) ou Aluno de Exatas (NERD).

Campanha: 3 oponentes com dificuldade crescente.

Habilidades: Use o menu tático para ativar Cura, Escudo ou Dano Duplo.

Estatísticas: Ao final, o jogo mostra um relatório completo de acertos, erros e pontuação.      