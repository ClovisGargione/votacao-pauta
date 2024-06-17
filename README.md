# votacao-pauta
Aplicação gerenciador de votação em pautas por associado

**Versões:**
*Java 17*
*Maven 3.8.6*
*jmeter 3.7.0*
*Spring boot 3.3.0*

**Banco de dados**
*SQLite*

## Documentação API
http://<seu-dominio>/api/v1/swagger-ui/index.html

## Execução
Iniciar navegação pela url **http://localhost:8080/api/v1/associados/listar-associados**

## Definção de domínio
o arquivo application.properties tem uma propriedade chamada **dominio.url**, onde pode ser definido o seu domínio.
Esta propriedade não é obrigatório, se não for definida as url's serão geradas com o ip e porta do servidor que estiver executando a aplicação.

**Exemplo:**
dominio.url=www.meudominio.com.br
