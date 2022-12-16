FROM eclipse-temurin:17.0.5_8-jdk-alpine as build
WORKDIR /src
COPY . .

FROM eclipse-temurin:17.0.5_8-jre-alpine AS runtime
ENV PATH "/usr/local/server/bin:${PATH}"
RUN mkdir -p /usr/local/server \
  && groupadd -r iottah --gid=1001 \
  && useradd -r -g iottah --uid=1001 iottah
COPY --chown=iottah:iottah --from=build /src/entrypoint.sh /usr/local/bin
COPY --chown=iottah:iottah --from=build /src/server-assembly/distribution /usr/local/server
ENTRYPOINT ["entrypoint.sh"]
CMD ["grpc-server"]
