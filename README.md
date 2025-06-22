# Survey SNP bot

## 📋 Описание

Этот бот обрабатывает команды пользователей, проводит простой опрос (имя, email, оценка), сохраняет данные и генерирует отчёт в виде таблицы Word (.docx).

---

## Функциональность

### Команды:

- `/start` — приветственное сообщение, сбрасывает текущую форму.
- `/form` — запускает опрос:
    1. Имя
    2. Email (валидируется)
    3. Оценка (1–10)
- `/report` — формирует и отправляет пользователю Word-документ с таблицей:

  | Имя | Email | Оценка |
    |-----|-------|--------|

---

##  Технологии

- Java 17+
- Spring Boot
- TelegramBots (LongPollingBot)
- PostgreSQL
- Apache POI (для .docx)
- Docker / Docker Compose
- Flyway (миграции)

---

## Запуск с Docker Compose

Перед началом убедитесь, что у вас установлены следующие инструменты:

- [Docker](https://docs.docker.com/get-docker/) — для запуска контейнеров
- [Docker Compose](https://docs.docker.com/compose/install/) — для запуска нескольких сервисов (если не встроен в Docker)

### 1. Клонируйте проект

```bash
git clone https://github.com/your-username/telegram-bot-survey.git
```

### 2. Перейдите в корневую папку
```bash
cd telegram-bot-survey
```

### 3. Добавьте переменные окружение .env
```bash
DB_HOST=
DB_PORT=
DB_NAME=
DB_PASSWORD=
DB_USERNAME=
BOT_NAME=
BOT_TOKEN=
```

### 4. Запустить команду 
```bash
docker compose up -d
```
