FROM bitnami/wildfly:29
LABEL authors="jellz"

# Set correct working directory
WORKDIR /opt/bitnami/wildfly/standalone/deployments/

# Copy WAR file to the correct deployment directory
COPY target/dockerAPI-1.0-SNAPSHOT.war .

# Optionally replace the standalone.xml if you need custom config
# COPY standalone.xml /opt/bitnami/wildfly/standalone/configuration/standalone.xml

# Expose default WildFly HTTP port
EXPOSE 8080

# Start WildFly
CMD ["/opt/bitnami/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
