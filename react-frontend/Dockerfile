FROM node:23-alpine
WORKDIR /app
COPY . .
RUN npm install && npm run build
EXPOSE 4173
CMD ["npm", "run", "preview"]
