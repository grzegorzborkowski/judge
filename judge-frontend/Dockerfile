FROM node:latest

RUN node -v
RUN npm -v

RUN npm install webpack -g

WORKDIR /tmp
COPY package.json /tmp

RUN npm config set registry http://registry.npmjs.org/ && npm install

WORKDIR /usr/src/app
COPY . /usr/src/app/
RUN cp -a /tmp/node_modules /usr/src/app/
RUN webpack

# TODO: bad command, we don't wanna npm run dev
# or at least have option to run both dev option and "deploy" option
CMD [ "npm", "run", "dev" ]
