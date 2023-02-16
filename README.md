# DEIInt: Projeto de Candidatura à Bolsa do DEI

## Descrição

O DEIInt é um sistema ficticio de gestão de bolsas e candidaturas a bolsas
para o departamento de informática do IST.

O sistema é composto por um backend e frontend, realizando acesso também a
uma base de dados para guardar certos dados.

## Status Quo

Atualmente, esta implementação do DEIInt encontra-se incompleta, faltando 
algumas das funcionalidades requesitadas no enunciado apresentado aos 
concorrentes a esta bolsa do DEI, no entanto outras funcionalidades já estão
implementadas.

Nomeadamente,
- Criação e gestão de **Concursos**;
- Criação e gestão de **Entrevistas**;
- Visualização de **Salas** e **Candidatos**;
- Criação e remoção de Candidatos;

Entre outras funcionalidades que me possa estar a esquecer de mencionar.

## Utilização

Para utilizar localmente esta implementação do DEIInt, clone o repositório e
siga os seguintes passos,

### Ligar a DB

Primeiro ligue a base de dados, para tal aceda ao diretório *src/database/* do repo e
corra o comando *sudo docker-compose up -d --build* .

Isto, deve iniciar a base de dados e criar as tabelas necessárias, vazias.

### Backend

Vá agora à pasta *src/backend* (do root do repo) e corra o comando *mvn clean spring-boot:run* .

### Frontend

Por fim, vá agora à pasta *src/frontend* (do root do repo) e corra o comando *npm run dev* .

#### Notas

Estes passos pressupoem que o utilizador já possui o software necessário (p.exemp. docker, npm, etc.) .
Mais informações sobre como correr o DEIInt encontram-se em ficheiros Markdown nas pastas Backend, Frontend e Database.
