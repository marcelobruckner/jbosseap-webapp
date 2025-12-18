# Aplicaçao de exemplo de uso de datasource gerenciado pelo JBOSS EAP 7.4

Esta aplicação de exemplo foi gerada utilizando o Eclipse Foundation starter for Jakarta EE no link https://start.jakarta.ee/ com as configurações abaixo.

- Jakarta Version EE: Jakarta EE 8
- Jakarta EE profile: Web Profile
- Java SE version: Java SE 17
- Runtime: WildFly
- Docker support: No

Para executar o aplicativo, basta rodar o comando a partir do diretório onde este arquivo está localizado. Antes de começar, verifique se você tem uma implementação Java SE compatível com a versão do Jakarta EE que está utilizando (este exemplo assume [Java SE](https://adoptium.net) 17).
Não se preocupe se você não tiver o Maven instalado, pois o [Maven Wrapper](https://maven.apache.org/wrapper/) já está incluído no projeto. Se necessário, você pode primeiro rodar o comando `chmod +x mvnw` para garantir que o script do Maven Wrapper tenha permissão de execução.

Observação importante:
Antes de executar o comando abaixo, que vai utilizar o wildfly provido na aplicação, garanta que a configuração do arquivo persistence.xml aponte para o datasource do banco em memória H2.
Esta linha é que define qual JNDI vai ser utilizado:

```
java:jboss/datasources/ExampleDS
```

```
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="
        http://xmlns.jcp.org/xml/ns/persistence
        http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
    <persistence-unit name="primary">
        <jta-data-source>java:jboss/datasources/ExampleDS</jta-data-source>
        <properties>
            <!-- Properties for Hibernate -->
            <property name="hibernate.hbm2ddl.auto" value="update" />
            <property name="hibernate.show_sql" value="false" />
        </properties>
    </persistence-unit>
</persistence>
```

Aqui o comando para executar o projeto no wildfly embarcado na aplicação, o que agiliza muito um teste rápido.

```
./mvnw clean package wildfly:dev
```

## Para testar a aplicação

Para incluir aluno:

```
curl -X POST http://localhost:8080/datasource-hello/api/alunos \
-H "Content-Type: application/json" \
-d '{"nome": "João Silva"}'
```

Para buscar a lista de alunos:

```
curl -X GET http://localhost:8080/datasource-hello/api/alunos
```

# Agora começa a nossa aventura.

Temos uns passo a seguir para atingir o objetivo.

- Instalar o Jboss eap 7.4
- Criar usuário administrador do Jboss
- Definir o seu banco de dados
- Registrar o driver JDBC do banco de dados no Jboss
- Criar o datasource no Jboss
- Apontar a sua aplicação para o datasource criado no Jboss

## Instalar o Jboss eap 7.4

Para instalar o Jboss, siga as instruções da [página oficial](https://docs.redhat.com/en/documentation/red_hat_jboss_enterprise_application_platform/7.4/html/installation_guide/index)

## Criar usuário administrador do Jboss

Para criar o usuário administrador, acesse o [link](https://docs.redhat.com/en/documentation/red_hat_jboss_enterprise_application_platform/7.4/html/configuration_guide/jboss_eap_management#adding_a_management_user) e siga os passos.

## Definir o seu banco de dados

No meu caso eu escolhi o PostgreSql. E utilizo pelo [Docker](https://www.docker.com/). Caso queria também, acesse a página oficial e siga as instruções.

Crie uma rede para comunicação entre o PostgreSql e o PgAdmin:

```
docker network create --driver bridge postgres-network
```

Depois execute o comando abaixo para criar o container do PostgreSql. Note que está usando a rede criada no comando anterior.

```
docker run --name teste-postgres --network=postgres-network -e "POSTGRES_PASSWORD=12345" -p 5432:5432 -v /home/seu_usuario/Desenvolvimento/PostgreSQL:/var/lib/postgresql/data -d postgres
```

Execute o comando abaixo para iniciar o container e note que também está linkada na rede. Observe que o e-mail que você informar, será o login na página de acesso ao PgAdmin

```
docker run --name pgadmin --network=postgres-network -p 15432:80 -e "PGADMIN_DEFAULT_EMAIL=seu@email.com" -e "PGADMIN_DEFAULT_PASSWORD=admin" -d dpage/pgadmin4
```

Se precisar de um acompanhamento mais próximo neste tópico, use esta postagem [aqui](https://renatogroffe.medium.com/postgresql-docker-executando-uma-inst%C3%A2ncia-e-o-pgadmin-4-a-partir-de-containers-ad783e85b1a4), que me ajudou. Dá uma passada no Linkedin do [Renato](https://www.linkedin.com/in/renatogroffe/) pra conhecer mais um pouco sobre ele.

## Registrar o driver JDBC do banco de dados no Jboss

Baixe o driver JDBC compatível com seu banco de dados na página oficial. No meu caso, foi nessa [página aqui](https://jdbc.postgresql.org/download/).

Antes de criar o datasource, temos que registrar o driver JDBC compatível com seu banco
de dados no Jboss.

Os passos para registrar o driver estão descritos [nesta página](https://docs.redhat.com/en/documentation/red_hat_jboss_enterprise_application_platform/7.4/html/configuration_guide/datasource_management#add_jdbc_driver_core_module_datasources).

## Criar o datasource no Jboss

Após o registro, podemos criar o datasource em si.
Não tem dificuldade nenhuma e está tudo detalhado [aqui](https://docs.redhat.com/en/documentation/red_hat_jboss_enterprise_application_platform/7.4/html/configuration_guide/datasource_management#adding_datasources).

## Apontar a sua aplicação para o datasource criado no Jboss

A parte mais simples é configurar no arquivo persistence.xml o nome JNDI do datasource criado.
Note que agora o arquivo aponta para o JNDI recém criado.

```
java:/PostgresDS
```

```
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="
        http://xmlns.jcp.org/xml/ns/persistence
        http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
    <persistence-unit name="primary">
        <jta-data-source>java:/PostgresDS</jta-data-source>
        <properties>
            <!-- Properties for Hibernate -->
            <property name="hibernate.hbm2ddl.auto" value="update" />
            <property name="hibernate.show_sql" value="false" />
        </properties>
    </persistence-unit>
</persistence>
```

# Iniciando o processo para testar após a alteração de JNDI

Lembra que quando começamos, testamos nossa aplicação no Wildfly embarcado?
Agora devemos implantar nossa aplicação direto no Jboss e para isso devemos ter certeza que paramos o Wildfly embarcado.

Vá até o console que utilizou e realize um ctrl + c, parando o servidor.

Após esse passo inicie o Jboss acessando a pasta <JBOSS_HOME>/bin e iniciando o aquivo standalone.sh.

Agora você volta na sua IDE, acesse o terminal e execute o comando abaixo.
Observe que diferentemente do comando usado no início, agora é o **deploy**.

```
./mvnw clean package wildfly:deploy
```

Realize novamente os teste e confirme que está acessando o banco de dados configurado.

Para incluir aluno:

```
curl -X POST http://localhost:8080/datasource-hello/api/alunos \
-H "Content-Type: application/json" \
-d '{"nome": "João Silva"}'
```

Para buscar a lista de alunos:

```
curl -X GET http://localhost:8080/datasource-hello/api/alunos
```
