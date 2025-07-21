# CourtAndGo
Uma plataforma digital completa para reserva e gestão de campos desportivos


[![AWS](https://img.shields.io/badge/AWS-Serverless-orange.svg)](https://aws.amazon.com/serverless/)
[![Kotlin](https://img.shields.io/badge/Kotlin-Multiplatform-purple.svg)](https://kotlinlang.org/docs/multiplatform.html)
[![Ktor](https://img.shields.io/badge/Ktor-Client-blue.svg)](https://ktor.io/)
[![SST](https://img.shields.io/badge/SST-Framework-green.svg)](https://sst.dev/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12+-blue.svg)](https://www.postgresql.org/)
[![Material-UI](https://img.shields.io/badge/Material--UI-7.2.0-blue.svg)](https://mui.com/)
[![TypeScript](https://img.shields.io/badge/TypeScript-4.9-blue.svg)](https://www.typescriptlang.org/)
[![Node.js](https://img.shields.io/badge/Node.js-18+-green.svg)](https://nodejs.org/)
[![npm](https://img.shields.io/badge/npm-8+-red.svg)](https://www.npmjs.com/)
[![React](https://img.shields.io/badge/React-19.1.0-blue.svg)](https://reactjs.org/)


## 📋 Sobre o Projeto

O **CourtAndGo** é uma aplicação completa para gestão e reserva de campos desportivos que conecta jogadores e proprietários de clubes através de uma plataforma moderna e eficiente. A solução oferece três componentes principais:

- 🏃‍♂️ **Aplicação Mobile** (Android/iOS) - Para jogadores procurarem e reservarem campos
- 💻 **Portal Web Admin** - Para proprietários gerirem os seus clubes e campos
- ☁️ **Backend Serverless** - API robusta e escalável na AWS

## 🏗️ Arquitetura

### Tecnologias Principais

**Frontend Mobile:**
- **Kotlin Multiplatform** - Partilha de código entre Android e iOS
- **Jetpack Compose Multiplatform** - UI moderna e reativa
- **Ktor Client** - Comunicação HTTP
- **KMPAuth** - Autenticação Google OAuth

**Frontend Web Admin:**
- **React 19.1.0** com **TypeScript** - SPA moderna
- **Material-UI 7.2.0** - Design system consistente
- **React Router DOM** - Navegação client-side

**Backend Serverless:**
- **AWS Lambda** - Funções serverless
- **API Gateway V2** - Endpoint HTTP único
- **Amazon RDS PostgreSQL** - Base de dados relacional
- **AWS Cognito** - Gestão de identidades
- **SST Framework** - Infrastructure as Code

## Como Começar

Para iniciar o projeto, siga os passos abaixo:

No pgAdmin, crie uma base de dados chamada `local` ou o nome que desejar, e altere esse nome no ficheiro `sst.config.ts` caso necessário.

Vá para a diretoria /courtandgo e execute o comando:

```bash
npm install
```
em seguida siga os passos deste ficheiro:
- [Configurar API Mobile](docs/infoG15.pdf)

## 📁 Estrutura do Projeto

```
court-go-app/
├── courtandgo/                    # Backend Serverless
│   ├── sst.config.ts             # Configuração de infraestrutura
│   ├── packages/
│   │   ├── functions/            # Funções Lambda por domínio
│   │   └── core/                 # Queries e mappers da BD
│   ├── frontend-admin/           # Portal Web para proprietários
│   └── infra/                    # Configurações AWS
├── CourtAndGo-Frontend/          # Aplicação Mobile (KMP)
│   ├── composeApp/               # Código comum Kotlin
│   ├── iosApp/                   # Projeto iOS específico
│   └── CONFIGURE_API.md          # Instruções de configuração
├── DataBase/                     # Scripts SQL
└── docs/                         # Documentação do projeto
```

## 🌟 Funcionalidades

### Para Jogadores (Mobile)
- 🔍 **Pesquisa avançada** de clubes por localização, modalidade e nome
- 📅 **Sistema de reservas** com calendário visual
- 🔐 **Autenticação** via email/password ou Google OAuth
- 👤 **Gestão de perfil** e histórico de reservas
- 📱 **Notificações** e lembretes de reservas
- 📊 **Integração com calendário** nativo

### Para Proprietários (Web Admin)
- 🏢 **Gestão completa de clubes** - criação, edição e configuração
- ⚽ **Gestão de campos** - especificações técnicas e preços
- ⏰ **Sistema de horários** - semanais e especiais
- 📈 **Dashboard**
- 🔑 **Autenticação segura** via AWS Cognito


## 🛠️ Desenvolvimento

### Estrutura de APIs (Backend)

O backend está organizado por domínios de negócio:

- **UserService** - Gestão de utilizadores e autenticação
- **ClubService** - Operações CRUD de clubes
- **CourtService** - Gestão de campos desportivos
- **ReservationService** - Sistema completo de reservas
- **ScheduleService** - Horários semanais e especiais
- **LocationService** - Dados geográficos

### Arquitetura Mobile

A aplicação mobile implementa **Clean Architecture** com:

- **Domain Layer** - Entidades de negócio
- **Data Layer** - Repositories e services
- **Presentation Layer** - ViewModels e UI Compose

## 🌍 Deployment

### Ambientes Suportados

- **Development** - Local com SST Dev

### AWS Services Utilizados

- **Lambda** - Compute serverless
- **API Gateway V2** - HTTP endpoints
- **RDS PostgreSQL** - Base de dados
- **Cognito** - Autenticação
- **VPC** - Isolamento de rede
- **CloudFront** - CDN para frontend
- **IAM** - Gestão de permissões

## 📚 Documentação

- 📖 [Configurar API Mobile](docs/infoG15.pdf)
- 🪪 [Cartaz do Projeto](docs/cG15.pdf)
- 🔌 [API Documentation](docs/API.postman_collection.json)
- 📋 [Relatório do Projeto](docs/rfG15.pdf)



## 👥 Equipa

Desenvolvido por alunos do Instituto Superior de Engenharia de Lisboa (ISEL).

Tiago Silva - 48252
João Mendonça - 48180

