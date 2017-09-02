FROM jboss/wildfly:latest

COPY target/*.war "/opt/jboss/wildfly/standalone/deployments/"

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-full.xml", "-b", "0.0.0.0"]

