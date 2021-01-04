Перед клонированием репозитория нужно установить MySql и настроить Intellij Idea

### Создаем БД с именем homekeeper
### Для пользователя с именем root устанавливаем пароль
### Все пароли и настройки доступа к базе записаны в файле application.properties в строчках - spring.datasource.password = ${dbSecret}
### В строке homekeeper.app.jwtSecret= ${dbSecret} записан ключ для токена - для простотты он ссылается на пароль к базе
### Пароль сохраняем в Intellij Idea - в панели запуска склонированного репозитория выбираем - Edit configuration, далее выбираем раздел Environtment variables - и в них прописываем dbSecret (или другое имя переменной) и сам пароль
### После поднятия репо в файле application.properties меняем 
- spring.jpa.generate-ddl = true
- spring.jpa.show-sql = true
- spring.jpa.hibernate.ddl-auto = create
### в файле V1__Init_DB.sql - надо закомменировать все строки
### стартуем приложение - дожны создаться таблицы в базе - надо это проверить в MySql - use homekeeper; show tables;
### если таблицы создались то надо роли залить в таблицу roles роли - 
для этого пишем 
-INSERT INTO roles(role_name) VALUES('ROLE_ADMIN');
-INSERT INTO roles(role_name) VALUES('ROLE_USER');
### в файле V1__Init_DB.sql - надо раскомменировать все строки
### После создания таблиц в базе и заливки данных в файле application.properties меняем 
- spring.jpa.generate-ddl = false
- spring.jpa.show-sql = false
- spring.jpa.hibernate.ddl-auto = validate
