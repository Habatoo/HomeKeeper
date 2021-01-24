#### Перед клонированием репозитория нужно установить PostgreSQL и настроить Intellij Idea
(версия PostgreSQL не ниже 11.7)
#### В файле pom.xml прописываем зависимости 
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
#### Создаем БД с именем homekeeper (CREATE DATABASE homekeeper;)
- проверяем что бд создана (\l)
#### Создаем пользователя с именем hkuser и паролем 1234567890 (CREATE ROLE hkuser LOGIN SUPERUSER PASSWORD '1234567890';)
- проверяем что пользователь создался (\du)
#### Указываем права пользователя hkuser на бд homekeeper
- GRANT ALL PRIVILEGES on database homekeeper to hkuser;
#### Все пароли и настройки доступа к базе записаны в файле application.properties в строчках - spring.datasource.password = ${dbSecret}
#### Настройки для PostgreSQL
- spring.datasource.url=jdbc:postgresql://localhost:5432/homekeeper
- spring.datasource.username=hkuser
- spring.datasource.password=${dbSecret}
#### В строке homekeeper.app.jwtSecret= ${dbSecret} записан ключ для токена - для простотты он ссылается на пароль к базе
#### Пароль сохраняем в Intellij Idea - в панели запуска склонированного репозитория выбираем - Edit configuration, далее выбираем раздел Environtment variables - и в них прописываем dbSecret (или другое имя переменной) и сам пароль
#### После поднятия репо в файле application.properties меняем 
- spring.jpa.generate-ddl = true
- spring.jpa.show-sql = true
- spring.jpa.hibernate.ddl-auto = create
#### в файле V1__Init_DB.sql - надо закомменировать все строки
#### стартуем приложение - дожны создаться таблицы в базе - надо это проверить в MySql - use homekeeper; show tables;
#### если таблицы создались то надо роли залить в таблицу roles роли - 
для этого пишем 
-INSERT INTO roles(role_name) VALUES('ROLE_ADMIN');
-INSERT INTO roles(role_name) VALUES('ROLE_USER');
#### в файле V1__Init_DB.sql - надо раскомменировать все строки
#### После создания таблиц в базе и заливки данных в файле application.properties меняем 
- spring.jpa.generate-ddl = false
- spring.jpa.show-sql = false
- spring.jpa.hibernate.ddl-auto = validate
