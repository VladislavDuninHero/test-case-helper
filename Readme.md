# Test Case Helper Application

Spring Boot приложение с PostgreSQL, JWT-аутентификацией и Docker-развёртыванием.

## 🚀 Быстрый старт

### 1. Предварительные требования
- Установите [Docker](https://docs.docker.com/get-docker/)
- Установите [Docker Compose](https://docs.docker.com/compose/install/)
- Java 17+ (только для разработки)

### 2. Настройка окружения

Создайте `.env` файл в корне проекта:

```bash
cp .env.example .env
```
### 3. Заполните .env файл своими данными

```
SPRING_DATASOURCE_URL=...
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
SPRING_DOCKER_DATASOURCE_URL=...
JWT_SECRET=...
ADMIN_TOKEN=...
APPLICATION_DEV_URL=...
ALLOWED_ORIGIN=...
POSTGRES_DB=...
POSTGRES_USER=...
POSTGRES_PASSWORD=...
```

### 4. Два варианта сборки:

1) Полный запуск через docker-compose

```
# Сборка образов и запуск в фоновом режиме
docker-compose up --build -d

# Просмотр логов приложения
docker-compose logs -f app

# Просмотр логов PostgreSQL
docker-compose logs -f postgres
```
2) Пошаговая сборка

```
# Используйте для Gradle
./gradlew clean build

# Или для Maven
./mvnw clean package

Соберите Docker-образ:
docker build -t test-case-helper .

Запустите инфраструктуру:
docker-compose up -d postgres

# Когда БД готова, запустите приложение
docker-compose up -d app
```