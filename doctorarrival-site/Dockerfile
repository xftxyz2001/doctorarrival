# Dockerfile for site
FROM node:20.10.0

WORKDIR /app

COPY .output .

ENTRYPOINT ["node", "./server/index.mjs"]

EXPOSE 3000