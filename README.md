# DockerWildfly

### CORRER EL DOCKER 

	mvn clean package



    docker-compose down --remove-orphans                                                                                                                      
    docker-compose up -d


   
   
    docker cp "PATH/DE/TU/RPOYECTO.WAR" josephwildfly:/opt/bitnami/wildfly/standalone/deployments/

    docker cp "/home/jellz/Downloads/helloworld-rs (1)/dockerAPI/target/dockerAPI-1.0-SNAPSHOT.war" josephwildfly:/opt/bitnami/wildfly/standalone/deployments/


### Base de datos

1. docker exec -it docker_database_1 psql -U myuser -d mydb
2. CREATE TABLE premier_league_teams (
   id SERIAL PRIMARY KEY,
   team_name VARCHAR(100) NOT NULL,
   city VARCHAR(100),
   manager VARCHAR(100),
   stadium VARCHAR(100),
   founded_year INT,
   website VARCHAR(255)
   );

3. INSERT INTO premier_league_teams (team_name, city, manager, stadium, founded_year, website)
   VALUES
   ('Manchester United', 'Manchester', 'Erik ten Hag', 'Old Trafford', 1878, 'https://www.manutd.com'),
   ('Liverpool', 'Liverpool', 'Jürgen Klopp', 'Anfield', 1892, 'https://www.liverpoolfc.com'),
   ('Chelsea', 'London', 'Graham Potter', 'Stamford Bridge', 1905, 'https://www.chelseafc.com'),
   ('Arsenal', 'London', 'Mikel Arteta', 'Emirates Stadium', 1886, 'https://www.arsenal.com'),
   ('Manchester City', 'Manchester', 'Pep Guardiola', 'Etihad Stadium', 1880, 'https://www.mancity.com'),
   ('Tottenham Hotspur', 'London', 'Antonio Conte', 'Tottenham Hotspur Stadium', 1882, 'https://www.tottenhamhotspur.com'),
   ('Leeds United', 'Leeds', 'Jesse Marsch', 'Elland Road', 1919, 'https://www.leedsunited.com'),
   ('West Ham United', 'London', 'David Moyes', 'London Stadium', 1895, 'https://www.whufc.com'),
   ('Aston Villa', 'Birmingham', 'Steven Gerrard', 'Villa Park', 1874, 'https://www.avfc.co.uk'),
   ('Everton', 'Liverpool', 'Frank Lampard', 'Goodison Park', 1878, 'https://www.evertonfc.com');
