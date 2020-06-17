//  OpenShift sample Node application
var express = require('express');
var fs      = require('fs');
var app     = express();
var eps     = require('ejs');
var got     = require('got');
var http = require('http');
var url = require('url');
var ippackage = require('ip');
var os = require('os');

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

app.get('/containerip', function (req,res)
{
  var containerip = ippackage.address();

  res.write( containerip );
  res.end();
});

app.get('/fileappend', function (req,res)
{
  requestURL = req.url;

  console.log( "  URL: " + requestURL );
  console.log( "  Method: " + req.method );

  fullURL = 'http://' + req.headers.host + req.url;
  console.log( "  Full URL: " + fullURL );

  var text = req.query.text;
  var file = req.query.file;
  
  if( text == null || file == null )
  {
    res.write( "Missing parameters (requires 'file' and 'text')...");
    res.end();
    return;
  }

  console.log( "Params:" );
  console.log( "  file: " + file );
  console.log( "  textToAdd: " + text );

  fs.appendFile( file, text + os.EOL, function (err)
  {
    if( err )
    {
      console.log( err.message );
      res.write( "Serverside issue: " + err.message );
      res.end();
      return;
    } 
    else
    {
      console.log( "  Updated " + file + " with " + text );
      res.write( "Updated '" + file + "' with '" + text + "'");
      res.end();
      return;
    }
  });
});

app.get( '/log', function( req,res ) {
  // Dual function - with no params loop 20 times outputting to console, with param output the param
  var input = req.query.message;

  if( input == null )
  {
    for( loop = 0; loop < 20; loop++ )
    {
      console.log( "User requested log messages, count " + loop );
    }

    res.send( "<b>Logged 20 messages....</b>" );
    return;
  }

  // At this point we have a log message
  console.log( input );
  res.send( "Logged <b>" + input + "</b>" );
  return;
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

// error handling
app.use(function(err, req, res, next){
  console.error(err.stack);
  res.status(500).send('Serverside error occurred: ' + err.message);
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
