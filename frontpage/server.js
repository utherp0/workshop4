//  OpenShift sample Node application
var express = require('express');
var fs      = require('fs');
var app     = express();
var eps     = require('ejs');
var got     = require('got');

app.engine('html', require('ejs').renderFile);

app.use( '/scripts', express.static('scripts'));
app.use( '/styles', express.static('styles'));
app.use( '/images', express.static('images'));

var port = process.env.PORT || process.env.OPENSHIFT_NODEJS_PORT || 8080;
var ip   = process.env.IP   || process.env.OPENSHIFT_NODEJS_IP || '0.0.0.0';

// Comment for git testing again
app.get('/', function (req, res)
{
  console.log( "Request received, serving frontpage....");
  res.render('frontpage.html');
});

app.get('/hackathon', function (req,res) {
  res.render('hackathon.html');
});

app.get('/page1.html', function (req,res ) {
  res.render('page1.html');
});

app.get('/page2.html', function (req,res) {
  res.send('<html><head><title>UthTest2 Headers Output</title></head><body>'
    + '<plaintext>'
    + JSON.stringify(res.headers)
    + '</plaintext>'
    + '</body></html>');
});

app.get('/page3.html', function (req,res) {
  var output = "";

  output += "<html>";
  output += "  <head>";
  output += "    <link rel='stylesheet' href='styles/ui.css'/>";
  output += "  </head>";
  output += "  <body>";
  output += "    <b>Third Test Page (generated from node.js)</b><br/>";
  output += "  <hr width=100% size=1/>";
  output += JSON.stringify( process.env );
  output += "  </body>";
  output += "</html>";

  res.send( output );
});

app.get('/page4.html', function (req,res) {
  var output = "";

  output += "<html>";
  output += "  <head>";
  output += "    <link rel='stylesheet' href='styles/ui.css'/>";
  output += "  </head>";
  output += "  <body>";
  output += getEnvs();
  output += "  </body>";
  output += "</html>";

  res.send( output );
});

app.get( '/geturl', function (req,res) {
  var targetURL = process.env["WORKSHOP_URL"];

  if( targetURL == null )
  {
    res.send( "NO DEFINED URL ENV");
  }
  else
  {
    res.send( targetURL );
  }
});

app.get( '/getterminal', function (req,res) {
  var targetURL = process.env["TERMINAL_URL"];

  if( targetURL == null )
  {
    res.send( "NO DEFINED TERMINAL ENV");
  }
  else
  {
    res.send( targetURL );
  }
});

app.get( '/envs', function (req,res) {
  res.send( getEnvs() );
});

app.get( '/env', function (req,res) {
  // Do I have a request variable?
  var input = req.query.name;

  if( input == null )
  {
    res.send( "\"No name parameter provided\"");
  }

  // Do I have an ENV with that name?
  var envoutput = process.env[input];

  if( envoutput == null )
  {
    res.send( "No env variable with name " + input + " found.");
  }
  else
  {
    res.send( input + ":" + envoutput ); 
  }
});

app.get( '/nasa', function (req,res) {
  var targetURL = "";
  var targetExplanation = "";
  got('https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY', { json: true }).then(response => {
    console.log(response.body.url);
    console.log(response.body.explanation);

    targetURL = response.body.url;
    targetExplanation = response.body.explanation;
    res.send( "<img src=\"" + targetURL + "\" width=\"500px\"><br/><br/>" + targetExplanation );
  }).catch(error => {
    console.log(error.response.body);
    
    targetURL = "No URL returned";
    targetExplanation = "No explanation returned";
  });

});

// error handling
app.use(function(err, req, res, next){
  console.error(err.stack);
  res.status(500).send('Something bad happened!');
});

app.listen(port, ip);
console.log('Server running on ' + ip + ':' + port);

function getEnvs()
{
  output = "";
  output += "<b>Environment Variables resident on host (generated from node.js)</b><br/>";
  output += "<hr width=100% size=1/>";

  names = getEnv();

  for( name in names )
  {
    target = names[name];
    output += "<b>" + target + "</b> " + process.env[target] + "<br/>";
  }

  return output;
}

function showObject(obj) {
  var result = "";
  for (var p in obj) {
    if( obj.hasOwnProperty(p) ) {
      result += p + " , " + obj[p] + "\n";
    } 
  }              
  return result;
}

function getEnv()
{
  var envNames = [];

  for( name in process.env )
  {
    envNames.push( name );
  }

  envNames.sort();

  return envNames;
}
