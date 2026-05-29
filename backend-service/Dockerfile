FROM eclipse-temurin:25-jre AS runner

USER root

LABEL maintainer=Aquerr

# Intall rentacar-backend-service
RUN groupadd --gid 1001 rentacar-backend-service \
    && useradd --home-dir /home/rentacar-backend-service --create-home --uid 1001 --gid 1001 rentacar-backend-service \
    && mkdir /rentacar-backend-service \
    && mkdir /rentacar-backend-service/config \
    && mkdir /rentacar-backend-service/data \
    && chown rentacar-backend-service:rentacar_backend -R /rentacar-backend-service

WORKDIR /rentacar-backend-service

COPY ./target/rentacar-backend-service-*.jar /rentacar_backend/rentacar-backend-service.jar
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# RentaCar Properties
ENV RENTACAR_CONFIG_DIR="./config"

ENV LANG="en_US.UTF-8"
ENV LANGUAGE="en_US:en"
ENV LC_ALL="en_US.UTF-8"

HEALTHCHECK --interval=2m --timeout=5s --retries=3 \
  CMD curl -f http://localhost:8086/api/v1/actuator/health || exit 1

VOLUME ["/rentacar-backend-service/data", "/rentacar-backend-service/config"]

EXPOSE 8086/tcp

USER rentacar-backend-service:rentacar-backend-service

ENTRYPOINT ["sh", "/entrypoint.sh"]