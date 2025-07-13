# Test Case Helper Application

Spring Boot приложение с MySQL, JWT-аутентификацией и Docker-развёртыванием.

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

Для DEV:
```
SPRING_DATASOURCE_URL=...
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
SPRING_DOCKER_DATASOURCE_URL=...
JWT_SECRET=...
ADMIN_TOKEN=...
APPLICATION_DEV_URL=...
ALLOWED_ORIGIN=...
MYSQL_DB=...
MYSQL_USER=...
MYSQL_PASSWORD=...
SPRING_REDIS_HOST=...
```
Для PROD:
```
SPRING_DATASOURCE_PROD_URL=...
SPRING_DATASOURCE_PROD_USERNAME=...
SPRING_DATASOURCE_PROD_PASSWORD=...
MYSQL_PROD_DB=...
MYSQL_PROD_USER=...
MYSQL_PROD_PASSWORD=...
JWT_PROD_SECRET=...
ADMIN_PROD_TOKEN=...
APPLICATION_PROD_URL=...
ALLOWED_PROD_ORIGIN=...
SPRING_PROD_REDIS_HOST=...
```
P.s. для запуска с разными профилями, при запуске нужно передавать "Active profiles": "dev" по-умолчанию и "prod" если нужен prod профиль.

### 4. Два варианта сборки:

1) Полный запуск через docker-compose

```
# Сборка образов и запуск в фоновом режиме
./gradlew clean build
docker-compose up --build -d
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
### 5. Использование:

Типичный тест-кейс:
1) После запуска приложения сразу создается юзер с ролью и правами QA_LEAD: admin / 123456w
2) Логин в систему
3) Создание проекта
4) Создание тестового набора
5) Создание тест-кейса

Чтобы создавать своих пользователей, нужно отправить запрос на соответствующий эндпоинт и передать ранее сгенерированный ADMIN_TOKEN.
