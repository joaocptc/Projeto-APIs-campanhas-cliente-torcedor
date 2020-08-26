# Campanhas do time do coração do cliente torcedor
<p align="center">Este projeto apresenta duas APIs, a API campanhas e a API cliente, na primeira encontramos um CRUD com os serviços referentes às campanhas, 
enquanto na segunda encontramos um serviço que permite cadastrar novos clientes e também fazer a associação destes com as campanhas consumidas da API campanhas. 
São usadas as seguintes tecnologias, Java, Spring boot, JPA e PostgreSQL. </p>

<h3> Arquitetura </h3>
<p align="justify">
  As duas API's foram construídas com base na arquivo MVC - Model, View, Controller
</p>

<h3> Tecnologias </h3>
<p align="justify">
  Linguagem JAVA, Spring boot, JPA, Base de Dados PostgreSQL versão 10 
</p
>
h3>

<h3> Arquivos </h3>
<ul>
  <li> <b> api-campanhas: </b> Contem os fontes da API campanhas </li>
  <li> <b> clientes: </b> Contem os fontes da API clientes </li>
  <li> <b> sql.sql: </b> Contem os comandos para criar a base de dados </li>
  <li> <b> requisicoes-postman.json: </b> Arquivo que pode ser importado no postman com diversas requisições para teste das APIs </li>
  <li> <b> testes gerais.pdf </b> documento com testes que foram feitos durante o desenvolvimento </p>
</ul>

<h3> Instruções para uso </h3>
<p align="justify">
Baixa os arquivos, importe no Eclipse ou em algum IDE de sua preferência, antes de subir a aplicação, abra o arquivo sql.sql e execute o comando no PostgreSQL. As tabelas serão criadas automaticamente, pois as classes estão mapeadas como tabelas em ambas as API’s. Caso queira fazer o uso de outra base de dados, é possível, mas será necessário acrescentar a dependência do driver da base desejada e fazer as alterações no arquivo com as informações da conexão com a base de dados.
</p>