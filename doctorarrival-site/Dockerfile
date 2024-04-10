# Dockerfile for site
FROM node:20.10.0

WORKDIR /app

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


COPY .output .

ENTRYPOINT ["node", "./server/index.mjs"]

EXPOSE 3000