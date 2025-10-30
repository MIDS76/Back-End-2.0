# Sistema de Gestão de Conselhos de Classe – Back-End

Este repositório contém o back-end do sistema de gestão de conselhos de classe, desenvolvido com Spring Boot, Spring Security e outras dependências do ecossistema Spring. O projeto segue boas práticas de arquitetura, com separação em camadas.

## Tecnologias e Dependências
**Java 21**
**Spring Boot**
**Spring Security**
**Spring Data JPA**
**Maven** para gerenciamento de dependências
**Banco de Dados** (Aiven)
**Lombok**

## Arquitetura do Back-End

### Estrutura do Código
O projeto segue arquitetura limpa, organizada em camadas:
**Controller**: Recebe requisições HTTP, faz validação inicial e retorna respostas.
**Service**: Contém a lógica de negócio e coordena operações entre repositórios e entidades.
**Repository**: Realiza operações de acesso a dados no banco via JPA.
**Model**: Representa as tabelas do banco de dados.
**Security**: Configura autenticação, autorização e gerenciamento de sessões.

## Principais Entidades

| Classe                | Descrição                                                                                                                                                                  |
|-----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Usuario**           | Representa todos os tipos de usuários do sistema (Aluno, Professor, Pedagógico). Campos: id, nome, email, senha, role, turma_id, is_representative(apenas na classe aluno) |
| **Aluno**             | Usuário do tipo aluno.                                                                                                                              |
| **Professor**         | Usuário do tipo professor.                                                                                                                                                 |
| **Pedagogico**        | Usuário do tipo pedagógico.                                                                                                                                                |
| **Turma**             | Representa uma turma do curso. Campos: id, nome, ano, curso                                                                                                                |
| **UnidadeCurricular** | Representa as disciplinas da turma.                                                                                                                                     |
| **UC_Turma**          | Associação entre Turma e Unidade Curricular, vinculando professores responsáveis.                                                                                          |
| **EventoConselho**    | Representa os conselhos de classe, com data, etapas e associação a turma e disciplinas.                                                                                    |
| **Formulario**        | Armazena respostas dos pré-conselhos (turma ou aluno).                                                                                                                     |
| **Feedback**          | Feedback de alunos sobre professores e instituição.                                                                                                                        |                                                                                                                                 |

Observação: As relações são implementadas via JPA, com cardinalidades 1:1, 1:N ou N:M, conforme necessário.

## Como Inicializar o Projeto

1. **Clonar o repositório**
   
bash
   git clone https://github.com/seu-usuario/nome-do-repositorio.git
   cd nome-do-repositorio
   

2. **Build com Maven**
   
bash
   mvn clean install
   

3. **Configurar Banco de Dados**
   Configure o application.properties ou application.yml com as credenciais do seu banco.

## Testando Endpoints
Você pode testar os endpoints utilizando Postman, como por exemplo:

**Usuários:**
GET /usuarios → Listar todos os usuários
POST /usuarios → Criar novo usuário
PUT /usuarios/{id} → Atualizar usuário
DELETE /usuarios/{id} → Deletar usuário

**Eventos de Conselho:**
POST /eventos → Criar novo evento
GET /eventos → Listar eventos
PUT /eventos/{id} → Atualizar etapas do evento

**Formulários:**
POST /formularios/pre-conselho/turma → Submissão pré-conselho turma
POST /formularios/pre-conselho/aluno → Submissão pré-conselho individual

**Feedbacks:**
GET /feedbacks?turma_id={id} → Consultar feedbacks filtrando por turma
GET /feedbacks?aluno_id={id} → Consultar feedbacks filtrando por aluno

## Segurança
Autenticação via Spring Security  
Controle de permissões baseado em perfil do usuário (Aluno, Professor, Pedagógico)  
Sessões stateless com JWT (se configurado),
