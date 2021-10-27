sudo git pull origin master
sudo sh mvnw spring-boot:run -Dspring-boot.run.jvmArguments=-Djasypt.encryptor.password=123 -f pom.xml -Dspring-boot.run.arguments="--server.port=443 --spring.datasource.url=jdbc:postgresq>


