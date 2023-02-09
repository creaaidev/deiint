# DEIInt: Exercício Prático BL267/2022_IST-ID

Dado o crescente número de concursos públicos administrados no âmbito do Departamento de Engenharia Informática (DEI), largamente incluindo uma componente de avaliação baseada em entrevistas a candidatos por examinadores do Departamento, torna-se necessário o desenvolvimento de uma aplicação web simples para a gestão destas: o **DEIInt: Interviews @ DEI Management System** (o seu exercício).

Esta aplicação deve permitir a gestão de:

-   concursos públicos (`Calls`) sob administração do DEI, guardando, para cada um, pelo menos o nome do concurso (e.g., `BL267/2022_IST-ID`), se o período de entrevistas está aberto, e outros campos que considere relevantes;
-   entrevistas a candidatos (`Interviews`), realizadas por um ou mais examinadores (`Examiner`), guardando, para cada uma, pelo menos o concurso associado, a sala onde decorre, o candidato, se esta se encontra terminada, uma figura sumário em formato imagem (opcionalmente), e outros campos que considere relevantes;
-   possíveis salas onde poderão decorrer entrevistas (`Rooms`; guardadas externamente - ver abaixo); e
-   candidatos que poderão ser sujeitos a entrevistas (`Candidates`; guardados externamente - ver abaixo).

Visto que as salas e os registos dos candidatos não são responsabilidade do DEI, mas sim dos Órgãos de Escola a nível central, a base de dados da aplicação não deve armazenar detalhes sobre tais entidades, estando estes presentes no sistema proprietário **_SchoolReg_** (que já se encontra desenvolvido). O _SchoolReg_ disponibiliza uma API REST que permitirá a integração do **DEIInt** com o mesmo de forma transparente ao utilizador.

Deve consultar a documentação da API do _SchoolReg_[\[1\]] para obter mais informações sobre a mesma e determinar como a utilizar. Por motivos de segurança, o sistema _SchoolReg_ exige que cada aplicação (incluindo o **DEIInt**) se autentique com um _token_ - para efeitos deste exercício, deve utilizar o seu IST ID (por exemplo, `ist199311`) como _token_. A exceção são _endpoints_ de consulta, que não necessitam de autenticação. A aplicação **DEIInt** deve ser tão flexível quanto o sistema _SchoolReg_ permitir, no que toca à gestão de salas e candidatos.

Note-se que, por razões de segurança, a API da _SchoolReg_ só permite atualizar e eliminar candidatos (`Candidates`) a quem os criou originalmente (comparando o _token_ enviado com o _token_ original). Para além disso, a manipulação de salas disponíveis (`Rooms`) é, evidentemente, da responsabilidade dos Órgãos Centrais, não sendo possível a aplicações externas, portanto, adicionar, atualizar ou remover salas através da API do _SchoolReg_.

Inicialmente, o **DEIInt** será utilizado apenas por examinadores autorizados do DEI, estando acessível apenas dentro da rede interna dos Serviços, não estando previsto para o momento atual o desenvolvimento de autenticação e autorização. Para além disso, não se pretende, por agora, a implementação de mecanismos de auditoria.

## Implementação

O objetivo deste exercício é desenvolver a aplicação **DEIInt** usando Spring Boot[\[2\]] (backend) e Vue.js[\[3\]] (frontend).

A aplicação **DEIInt** deverá permitir, pelo menos:

-   Visualizar todos os concursos numa tabela de consulta rápida, contendo pelo menos o seu nome e se o período de entrevistas está aberto;
-   Adicionar novos concursos;
-   Atualizar e remover concursos existentes;
-   Fechar o período de entrevistas de um concurso;
-   Visualizar todos os candidatos numa tabela de consulta rápida, contendo pelo menos nome e endereço de e-mail;
-   Visualizar um candidato em particular numa página dedicada, contendo pelo menos o seu nome, endereço de e-mail, e resultados de entrevistas passadas;
-   Adicionar novos candidatos;
-   Atualizar e remover candidatos existentes;
-   Visualizar todas as salas numa tabela de consulta rápida, contendo pelo menos nome, edifício e andar;
-   Visualizar todas as entrevistas numa tabela de consulta rápida, contendo pelo menos nome do concurso, sala, candidato e se já se encontra terminada;
-   Visualizar, na mesma tabela (descrita acima) ou noutra com características semelhantes, todas as entrevistas associadas a um concurso em específico;
-   Visualizar uma entrevista em particular numa página dedicada, contendo pelo menos o nome do concurso associado, a sala onde decorre, o candidato, se esta se encontra terminada, figura sumário (se existir) e avaliações registadas (com o nome do examinador que submeteu cada uma);
-   Adicionar novas entrevistas, escolhendo o concurso associado, uma sala (que esteja disponível) e um candidato;
-   Atualizar e remover entrevistas existentes;
-   Registar uma avaliação numa entrevista (que não esteja terminada), especificando o nome do examinador e notas, inteiros entre 0 e 5 (inclusive), para cada uma das seguintes categorias:
    1.  _Soft Skills_ (`ss`);
    2.  Raciocínio em Programação (`rp`);
    3.  Linux (`lin`);
    4.  Windows (`win`);
    5.  Hardware (`hw`);
-   Marcar uma entrevista como terminada (de modo a não permitir futuros registos de avaliações, e apenas se pelo menos uma avaliação estiver registada), adicionando, opcionalmente, uma figura sumário (uma imagem), que ilustre o _spirit animal_ do candidato (i.e., um animal que o represente espiritualmente), do ponto de vista dos examinadores; e
-   Submeter junto dos Órgãos de Escola o resultado final de uma entrevista, contendo, para cada categoria descrita acima, a média das avaliações registadas pelos examinadores - deve escolher se esta ação deve ser tomada de forma explícita pelo utilizador ou automaticamente pelo sistema no(s) momento(s) indicado(s).

É permitida (mas não obrigatória) a implementação de funcionalidades adicionais às acima, especialmente se considerar que algum aspeto melhoraria significativamente a _User Experience (UX)_ durante a utilização do sistema.

É-lhe fornecido **código base** que deve analisar e utilizar como ponto de partida, estando algumas das funcionalidades acima já parcial ou integralmente implementadas. Deve seguir as convenções presentes no código base. Não é necessária a implementação de testes. Será penalizada qualquer falta de validação de input do utilizador.

Deve realizar o exercício de forma modular. Serão valorizadas qualidade e estética do código e da interface web apresentada.

_Note-se que a situação descrita neste enunciado é fictícia e concebida expressamente pelo propósito de dar motivação ao seu exercício prático, não retratando necessariamente a realidade. A sua solução será usada apenas para fins de avaliação._

## Submissão

É obrigatório e alvo de avaliação a utilização de um sistema de controlo de versões (especificamente, `git`[\[4\]]) durante o desenvolvimento do exercício. Deve submeter a sua solução no repositório privado que lhe foi atribuído para o efeito, na instância de _GitLab_ da RNL[\[5\]], e que inicialmente contém o código base, do qual deve partir. Não é necessária a utilização de nenhuma funcionalidade específica do _GitLab_ (e.g., _Issues_ e _Merge Requests_), apenas da ferramenta `git` em si. Assim, ser-nos-á possível validar a sua capacidade de utilização desta ferramenta, podendo até ser-lhe benéfico durante a componente de entrevista.

Deve incluir, junto da sua solução, um ficheiro `README` que descreva o procedimento para iniciar um servidor local de testes. Pode incluir nesse ficheiro `README` qualquer informação que considere relevante para a avaliação do seu trabalho, tal como alguma eventual justificação de decisões tomadas. Não submeta artefactos irrelevantes (e.g., pasta `node_modules`).

**Prazo máximo de entrega: quinta-feira, dia 16 de fevereiro de 2023, às 16h59.**

Não serão aceites quaisquer entregas após o prazo limite, por qualquer razão. Qualquer candidato que não entregue uma solução até ao prazo limite será automaticamente avaliado com a cotação de 0 (zero) valores na componente de seleção correspondente ao exercício prático.

**Boa sorte!**

**NOTA:** Durante os testes de adição de novos candidatos ao _SchoolReg_, pedimos que seja responsável nos dados utilizados, sendo este um sistema partilhado entre todos os candidatos.

## Recursos Potencialmente Úteis

-   https://vuetifyjs.com/en/
-   https://spring.io/guides
-   https://www.javatpoint.com/spring-boot-architecture

[\[1\]]: https://eindhoven.rnl.tecnico.ulisboa.pt/school-reg
[\[2\]]: https://spring.io/projects/spring-boot
[\[3\]]: https://vuejs.org/
[\[4\]]: https://git-scm.com/
[\[5\]]: https://gitlab.rnl.tecnico.ulisboa.pt/
