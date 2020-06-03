package org.uth.workshop4.docbuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocBuilder
{
  private final static String DATEFORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

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

      if( !scaffold( workingDirectory, gitCloneDirectory )) 
      {
        System.exit(1);
      }

      // Test
      DocBuilder.log( "Test BUILD START" );
      String[] processDefinition = new String[] { "mvn",
                                                  "clean",
                                                  "package",
                                                  "-DfacilitatorName='DSA Team'",
                                                  "-DfacilitatorEmail='ilawson@redhat.com'",
                                                  "-DfacilitatorTitle='DSA Team'",
                                                  "-DwebConsoleUrl='https://console-openshift-console.apps.cluster-nomura-9cd9.nomura-9cd9.example.opentlc.com/'",
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