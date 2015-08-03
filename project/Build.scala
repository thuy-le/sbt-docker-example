import sbt._
import Keys._
import sbtdocker._
import sbtassembly.AssemblyPlugin.autoImport._
import DockerKeys._
import java.io.File

object Build extends Build {
	lazy val root = (project in file(".") ).aggregate(foo, bar).enablePlugins(DockerPlugin).settings(
      	run := {
        	(run in bar in Compile).evaluated
      	},
      	docker <<= docker dependsOn assembly,
		dockerfile in docker := {
		  	new Dockerfile {
		    	from("java")
		    	maintainer("apiumtech")
		    	expose(1339,1338)
		    	add((outputPath in assembly).value, s"/app/${(outputPath in assembly).value.name}")
  				workDir("/app")
		    	entryPoint("java", "-jar", (outputPath in assembly).value.name)
		  	}
		}
    )

    lazy val foo = (project in file("foo")).enablePlugins(DockerPlugin).settings(
    	mainClass := {
    		(mainClass in Compile).value
    	},
      	docker <<= docker dependsOn assembly,
		dockerfile in docker := {
		  	new Dockerfile {
		    	from("java")
		    	maintainer("apiumtech")
		    	expose(1339,1338)
		    	add((outputPath in assembly).value, s"/app/${(outputPath in assembly).value.name}")
  				workDir("/app")
		    	entryPoint("java", "-jar", (outputPath in assembly).value.name)
		  	}
		}
    )

	lazy val bar = (project in file("bar"))
}
