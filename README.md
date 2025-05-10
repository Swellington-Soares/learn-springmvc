# Projeto de aprendizado no Spring Boot

Estou desenvolvendo este projeto utilizando Spring Boot MVC. 
A ideia é criar uma espécie de blog, onde os usuários possam fazer postagens, 
seguir outros perfis, curtir e compartilhar conteúdos próprios ou de terceiros, 
além de comentar nas publicações.

Após quatro protótipos focados em testar recursos específicos — como segurança, 
configurações e outros — e depois de vários dias enfrentando os desafios do Spring, 
finalmente as coisas começaram a fluir. Neste momento em que escrevo, estou muito 
animado com o progresso e o aprendizado que esse projeto tem proporcionado.

## O que atualmente funciona. [TAG 0.1](https://github.com/Swellington-Soares/learn-springmvc/releases/tag/0.1)

### ✅ Fluxo de Login Completo

- Tela de login personalizada
- Recuperação de senha com envio de link contendo token por e-mail
- Ativação de conta via link enviado por e-mail
- Sistema de "lembre-me" implementado com token persistente
- Registro de novos usuários

### 🧩 Template HTML

- Utilização do [JTE](https://jte.gg/) como template engine, configurado e funcionando perfeitamente com Kotlin
- Templates estáticos utilizados para e-mails
- Integração com [Tailwind CSS 4.0](https://tailwindcss.com/) e [Flowbite](https://flowbite.com/)
- Modo claro e escuro com persistência de preferência
- Suporte a internacionalização (i18n)
- Integração dos dados do usuário autenticado diretamente na view

### ✔️ Validações Personalizadas

- Verificação de correspondência entre *Password* e *Confirm Password*
- Validação de e-mail único
- Validação de nome de usuário único
- Verificação se o usuário já existe no sistema  

# O que aprendi até o momento [TAG 0.1](https://github.com/Swellington-Soares/learn-springmvc/releases/tag/0.1)

### 🔐 Spring Security

- Configuração do fluxo de login personalizado
- Definição de exceções personalizadas
- Implementação de serviço personalizado para autenticação de usuários
- Sistema de "lembre-me" com persistência via token no lado do servidor
- Controle de permissões de acesso nos endpoints

### 🗄️ JPA

- Criação de classes de entidades
- Anotação de campos com validações
- Desenvolvimento de validações personalizadas
- Criação de queries em repositórios utilizando o padrão de nomenclatura de métodos
- Utilização do [Flyway](https://www.red-gate.com/products/flyway/community/) para versionamento e estruturação do banco de dados

### 🚀 Spring Boot (Geral)

- Desenvolvimento seguindo o padrão MVC
- Uso do [MapStruct](https://mapstruct.org/) para mapeamento entre objetos
- Aplicação do padrão DTO (Data Transfer Object) para envio de dados entre cliente-servidor e entre camadas
- Configuração de elementos centrais como interceptors, views estáticas e mensagens traduzíveis (message sources)
- Criação e configuração de jobs (tarefas agendadas)  


### Imagens e diagramas em breve...
