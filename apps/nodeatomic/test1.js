const http = require('http');
const url = require('url');
const ip = require('ip');
const fs = require('fs');
const os = require('os');

const server = http.createServer((request, response) => 
{
  console.log( "In server (" + ip.address() + ") response..." );
  //console.log( "Full Request Object:" );
  //console.log( request );

  requestURL = request.url;

  console.log( "  URL: " + requestURL );
  console.log( "  BaseUrl: " + request.baseUrl );
  console.log( "  Method: " + request.method );

  fullURL = 'http://' + request.headers.host + request.url;
  console.log( "  Full URL: " + fullURL );

  if(request.method === 'GET' && requestURL.startsWith('/test')) 
  {
    currentURL = new URL(fullURL);
    params = currentURL.searchParams;

    target = "NO TARGET PARAM";
    textToAdd = "NO ADDITIONAL TEXT";

    if(params.has('file')) target = params.get('file');
    if(params.has('textToAdd')) textToAdd = params.get('textToAdd'); 

    console.log( "Params:" );
    console.log( "  file: " + target );
    console.log( "  textToAdd: " + textToAdd );

    // If the file parameter is present attempt a write
    if( params.has('file') && params.has('textToAdd') )
    {
      fs.appendFile( target, textToAdd + os.EOL, function (err)
      {
        if( err ) console.log( err.message );
        else console.log( "  Updated " + target + " with " + textToAdd );
      });
    }

    response.write( "Server return...");
    response.end();
  }
});

server.listen(8082);
