const express = require('express');
const app = express();
var ip = require("ip");

var versionIdentifier = "v1.0";
const port = 8080;

var holidayType = process.env.HOLIDAY_TYPE;

var path = "/" + holidayType;

app.get(path, (request, response) => {
  console.log("holiday " + holidayType + " url hit .... ");
  response.send("This is the " + holidayType + " holiday application " + versionIdentifier);

});

console.log( ip.address() );
console.log(holidayType + " holiday - " + versionIdentifier);

// set the server to listen on the designated port
app.listen(port, () => console.log(`Listening on port ${port}`));
