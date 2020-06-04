package org.uth.workshop4.docbuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class DocBuilder
{
  private final static String DATEFORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

  // Static components for simplicity
  private static String _facilitorName = null;
  private static String _email = null;
  private static String _title = null;
  private static String _clusterURL = null;
  private static String _documentTitle = null;
  private static LinkedList<String> _labs = null;

  private final static String[] BASEINDEX = 
    { "= {topTitle}",
      "{author} <{email}>",
      " ",
      "include::intro.asciidoc[]",
      "include::prereq.asciidoc[]"
    }

  public static void main( String[] args )
  {
    if( args.length != 4 )
    {
      System.out.println( "Usage: java DocBuilder workingDirectory(will be created) contentManifestFile gitCloneDirectory outputDirectory");
      System.exit( 0 );
    }

    String workingDirectory = args[0];
    String contentManifestFile = args[1];
    String gitCloneDirectory = args[2];
    String outputDirectory = args[3];

    DocBuilder.log("Execution started.");

    try
    {
      // Local instances of runtime variables
      Process process = null;
      Runtime runtime = Runtime.getRuntime();
      int response = 0;

      DocBuilder.log( "  Preparing Scaffold" );

      // Scaffold the source for the doc build
      if( !DocBuilder.scaffold( workingDirectory, gitCloneDirectory )) 
      {
        System.exit(1);
      }

      // Process the manifest file
      if( !DocBuilder.processManifest(contentManifestFile, workingDirectory))
      {
        System.exit(1);
      }



      // Test
      DocBuilder.log( "Test BUILD START" );
      String[] processDefinition = new String[] { "mvn",
                                                  "clean",
                                                  "package",
                                                  "-DdocumentTitle='" + _documentTitle + "'",
                                                  "-DfacilitatorName='" + _facilitorName + "'",
                                                  "-DfacilitatorEmail='" + _email + "'",
                                                  "-DfacilitatorTitle='" + _title + "'",
                                                  "-DwebConsoleUrl='" + _clusterURL + "'",
                                                  "-f",
                                                  workingDirectory + File.separator + "pom.xml"};

      process = runtime.exec(processDefinition);
      response = process.waitFor();
      DocBuilder.log( "Test BUILD STOP " + response );

    }
    catch( Exception exc )
    {
      DocBuilder.log( exc.toString() );
    }
  }

  private static boolean mkdir( String target )
  {
    File targetDir = new File( target );

    if( !( targetDir.mkdir()))
    {
      DocBuilder.log( "  Directory create failed for " + target );
      return false;
    }

    return true;
  }

  private static boolean scaffold( String workingDirectory, String gitDirectory )
  {
    DocBuilder.log( "Scaffolding...");
    DocBuilder.log( "  Creating working directory " + workingDirectory );
    
    if( !( mkdir( workingDirectory ))) return false;

    DocBuilder.log( "  Creating ocp4devex directory" );

    if( !( mkdir( workingDirectory + File.separator + "ocp4devex" ))) return false;

    DocBuilder.log( "  Creating src directory" );
    
    if( !( mkdir( workingDirectory + File.separator + "ocp4devex" + File.separator + "src" ))) return false;
    
    DocBuilder.log( "  Creating main directory" );
    
    if( !( mkdir( workingDirectory + File.separator + "ocp4devex" + File.separator + "src" + File.separator + "main" ))) return false;

    DocBuilder.log( "  Creating asciidoc directory" );

    if( !( mkdir( workingDirectory + File.separator + "ocp4devex" + File.separator + "src" + File.separator + "main" + File.separator + "asciidoc" ))) return false;

    DocBuilder.log( "  Creating images directory" );

    if( !( mkdir( workingDirectory + File.separator + "ocp4devex" + File.separator + "src" + File.separator + "main" + File.separator + "asciidoc" + File.separator + "images" ))) return false;

    DocBuilder.log( "  Transferring Images" );

    String sourceDirectory = gitDirectory + File.separator + "labs" + File.separator + "images";
    String targetDirectory = workingDirectory + File.separator + "ocp4devex" + File.separator + "src" + File.separator + "main" + File.separator + "asciidoc" + File.separator + "images";

    if( !( DocBuilder.dirCopy(sourceDirectory, targetDirectory)))
    {
      return false;
    }

    DocBuilder.log( "  Transferring Lab Source" );

    sourceDirectory = gitDirectory + File.separator + "labs" + File.separator + "src";
    targetDirectory = workingDirectory + File.separator + "ocp4devex" + File.separator + "src" + File.separator + "main" + File.separator + "asciidoc";

    if( !( DocBuilder.dirCopy(sourceDirectory, targetDirectory)))
    {
      return false;
    }

    DocBuilder.log( "  Copying top level project pom.xml" );

    String targetPOMFile = gitDirectory + File.separator + "labs" + File.separator + "top_pom.xml";
    String destinationPOMFile = workingDirectory + File.separator + "pom.xml";

    try
    {
      Files.copy(new File( targetPOMFile ).toPath(), new File( destinationPOMFile ).toPath() );
    }
    catch( Exception exc )
    {
      DocBuilder.log( "  Failed to create top level pom file.");
      exc.printStackTrace();

      return false;
    }

    DocBuilder.log( "  Copying doc level pom.xml" );

    targetPOMFile = gitDirectory + File.separator + "labs" + File.separator + "project_pom.xml";
    destinationPOMFile = workingDirectory + File.separator + "ocp4devex" + File.separator + "pom.xml";

    try
    {
      Files.copy(new File( targetPOMFile ).toPath(), new File( destinationPOMFile ).toPath() );
    }
    catch( Exception exc )
    {
      DocBuilder.log( "  Failed to create project level pom file.");
      exc.printStackTrace();

      return false;
    }

    return true;
  }

  private static void copy( Path source, Path destination )
  {
    try
    {
      Files.copy( source, destination, StandardCopyOption.REPLACE_EXISTING);
      DocBuilder.log( "  Copied " + source );
    }
    catch( Exception exc )
    {
      DocBuilder.log( "  Failed to copy " + source + " to " + destination );
    }
  }

  private static boolean dirCopy( String source, String destination )
  {
    Path sourcePath = Paths.get(source);
    Path destinationPath = Paths.get(destination);

    try
    {
      Files.walk(sourcePath).forEach(sourceTarget -> copy( sourceTarget, destinationPath.resolve( sourcePath.relativize(sourceTarget))));
      return true;
    }
    catch( Exception exc )
    {
      return false;
    }
  }

  private static boolean processManifest( String manifestFile, String workingDirectory )
  {
    try
    {
      File manifest = new File( manifestFile );
      Scanner scanner = new Scanner( manifest );

      String data = null;

      // First, the environment for the document
      // 1: Facilitator Name
      data = scanner.nextLine();

      String[] components = data.split( ":" );

      if( "Facilitator".equals( components[0]))
      {
        _facilitorName = components[1];
      }
      else
      {
        DocBuilder.log( "  Manifest format invalid, missing Facilitator as first component" );
        scanner.close();
        return false;
      }

      // 2: Facilitator Email
      data = scanner.nextLine();

      components = data.split( ":" );

      if( "Email".equals( components[0]))
      {
        _email = components[1];
      }
      else
      {
        DocBuilder.log( "  Manifest format invalid, missing Email as second component" );
        scanner.close();
        return false;
      }

      // 3: Facilitator Title
      data = scanner.nextLine();

      components = data.split( ":" );

      if( "Title".equals( components[0]))
      {
        _title = components[1];
      }
      else
      {
        DocBuilder.log( "  Manifest format invalid, missing Title as third component" );
        scanner.close();
        return false;
      }

      // 4: Cluster URL 
      data = scanner.nextLine();

      components = data.split( ":" );

      if( "ClusterURL".equals( components[0]))
      {
        _clusterURL = components[1];
      }
      else
      {
        DocBuilder.log( "  Manifest format invalid, missing ClusterURL as fourth component" );
        scanner.close();
        return false;
      }

      // 5: Document Title
      data = scanner.nextLine();

      components = data.split( ":" );

      if( "DocumentTitle".equals( components[0]))
      {
        _documentTitle = components[1];
      }
      else
      {
        DocBuilder.log( " Manifest format invalid, missing Document Title as fifth component");
        scanner.close();
        return false;
      }

      // Break line
      data = scanner.nextLine();

      if( !( "----" ).equals( data ) )
      {
        DocBuilder.log( " Manifest format invalid, missing divider after metadata before lab list" );
        scanner.close();
        return false;
      }

      // Build Lab Queue
      data = scanner.nextLine();

      while( data != null && !( "----".equals( data )) )
      {
        if( _labs == null ) _labs = new LinkedList<String>();

        _labs.add( data );
         data = scanner.nextLine();
      }

      // Post lab load check
      if( _labs == null || _labs.size() == 0 )
      {
        DocBuilder.log( "  No labs loaded from manifest, please check manifest file " + manifestFile );
        scanner.close();
        return false;
      }

      // Lab existence check
      if !( DocBuilder.validateLabs( workingDirectory ))
      {
        scanner.close();
        return false;
      }

      scanner.close();
    }
    catch( Exception exc )
    {
      DocBuilder.log( "  Exception occurred whilst reading manifest file " + manifestFile );
      return false;
    }
    return true;
  }

  private static boolean validateLabs( String workingDirectory )
  {
    String baseDir = workingDirectory + File.separator + "ocp4devex" + File.separator + "src" + File.separator + "main" + File.separator + "asciidoc" + File.separator;

    for( String lab : _labs )
    {
      String compositeFile = baseDir + lab + ".asciidoc";
      File testFile = new File( compositeFile );

      if( testFile.exists())
      {
        DocBuilder.log( "  Lab verified - " + lab );
      }
      else
      {
        DocBuilder.log( "  Missing lab file - " + lab + ".asciidoc" );
        return false;
      }
    }

    return true;
  }

  private static boolean generateDocumentIndex( String manifestFile )
  {
    return true;
  }

  private static boolean generateDocumentation( String workingDirectory, String outputDirectory )
  {
    return true;
  }

  private static void cleanup( String workingDirectory )
  {

  }

  private static void log( String message )
  {
    System.out.println( "[" + DocBuilder.time(System.currentTimeMillis()) + "] " + message );
  }

  private static String time( long now )
  {
    SimpleDateFormat formatter = new SimpleDateFormat(DocBuilder.DATEFORMAT);
    return( formatter.format(new Date(now)));
  }
}