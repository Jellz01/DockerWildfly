FROM bitnami/wildfly:29
LABEL authors="jellz"

# Copy WAR file (with embedded PostgreSQL driver) to the deployment directory
COPY target/dockerAPI-1.0-SNAPSHOT.war /opt/bitnami/wildfly/standalone/deployments/

# Expose default WildFly HTTP portc
EXPOSE 8080

# Start WildFly
CMD ["/opt/bitnami/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]