# Kafka-test

Для работы нужно:
- cкачать проект
- перейти в папку с проектом в командной строке
- выполнить команду docker-compose up -d
- запустить приложение  
Запустить приложение в одном из двух режимов:
1) Для записи данных первой таблицы в Kafka топик выполнить команду
java -jar C:\testSobec\kafka\build\libs\kafka-0.0.1-SNAPSHOT.jar "1" 
2) Для получения всех сообщений топика и записи их во вторую таблицу выполнить команду
java -jar C:\testSobec\kafka\build\libs\kafka-0.0.1-SNAPSHOT.jar "2"
