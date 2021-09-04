FROM quay.io/keycloak/keycloak:15.0.2

ARG AUTHENTICATOR_JAR=target/KeyclockSpi.jar
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005

# copy the jars ...
COPY ${AUTHENTICATOR_JAR} /opt/jboss/keycloak/standalone/deployments/
