const express = require('express');
var ip = require("ip");
const app = express();
const router = express.Router();
var bunyan = require('bunyan');
const uuid = require('uuid')
const id = uuid.v1()

var start = Date.now();

var log = bunyan.createLogger({
      name: "Slave",
      streams: [{
      path: './slave.log',
    }]
  });

var targetPort;  
targetPort = process.env.NODE_APP_SLAVE_SERVICE_PORT;
log.info({app: 'slave', phase: 'setup' }, `targetPort  ${targetPort}`);
if (targetPort === undefined) {
  targetPort = process.env.NODE_APP_SLAVE_APP_SERVICE_PORT;
}

const port = targetPort;
var counter = 0;
var ignore_switch = 0;

app.get('/', (request, response) => {
  response.send('Hello - this is the simple slave REST interface');
});

app.get('/health', (request, response) => {
  if (ignore_switch == 0) {
    var millis = Date.now() - start;

    response.send('..  The slave has been running for ' + (millis / 1000) + ' seconds');
  }
});

app.get('/ip', (request, response) => {
  if (ignore_switch == 0) {
    var messageText = ip.address();
    counter++;
    log.info({app: 'slave', phase: 'operational', id: id, counter: counter, slave_ip: ip.address()}, " responded .... " + counter);
    response.json(messageText);
  }
});

app.get('/ignore', (request, response) => {
  var messageText = ip.address() + " ignore switch activated";
  counter++;
  log.info({app: 'slave', phase: 'probe management', slave_ip: ip.address()}, " ignore switch activated");
  if (ignore_switch == 0) {
    ignore_switch = 1;
  }
  response.json(messageText);
});

app.get('/restore', (request, response) => {
  var messageText = ip.address() + " restore switch activated";
  counter++;
  log.info({app: 'slave', phase: 'probe management', slave_ip: ip.address()}, " restore switch activated");
  if (ignore_switch == 1) {
    ignore_switch = 0;
  }
  response.json(messageText);
});

log.info( ip.address() );

// set the server to listen on the designated port
app.listen(port, () => log.info({app: 'slave', phase: 'setup', }, `Listening on port ${port}`));
