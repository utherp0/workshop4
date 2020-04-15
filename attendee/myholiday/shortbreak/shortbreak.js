const express = require('express');
var ip = require("ip");
const app = express();
const router = express.Router();

var targetPort = 8080;
var versionIdentifier = "v1.0";

const port = targetPort;


app.get('/shortbreak', (request, response) => {
  console.log("short break holiday - " + versionIdentifier);
  response.send('This is the short break holiday application ' + versionIdentifier);

});

console.log( ip.address() );

// set the server to listen on the designated port
app.listen(port, () => console.log("Listening on port  + ${port}"));
