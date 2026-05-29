FROM eclipse-temurin:25-jre AS runner

USER root

LABEL maintainer=Aquerr

# Intall rentacar-workflow-service
RUN groupadd --gid 1001 rentacar-workflow-service \
    && useradd --home-dir /home/rentacar-workflow-service --create-home --uid 1001 --gid 1001 rentacar-workflow-service \
    && mkdir /rentacar-workflow-service \
    && mkdir /rentacar-workflow-service/config \
    && mkdir /rentacar-workflow-service/data \
    && chown rentacar-workflow-service:rentacar-workflow-service -R /rentacar-workflow-service

WORKDIR /rentacar-workflow-service

COPY ./target/rentacar-workflow-service-*.jar /rentacar_workflow/rentacar-workflow-service.jar
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# RentaCar Properties
ENV RENTACAR_CONFIG_DIR="./config"

ENV LANG="en_US.UTF-8"
ENV LANGUAGE="en_US:en"
ENV LC_ALL="en_US.UTF-8"

HEALTHCHECK --interval=2m --timeout=5s --retries=3 \
  CMD curl -f http://localhost:8086/api/v1/actuator/health || exit 1

VOLUME ["/rentacar-workflow-service/data", "/rentacar-workflow-service/config"]

EXPOSE 8086/tcp

USER rentacar-workflow-service:rentacar-workflow-service

ENTRYPOINT ["sh", "/entrypoint.sh"]