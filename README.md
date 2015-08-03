# sbt-docker-example with assembly

- Demonstrates how to dockerize a Scala application with sbt-docker and sbt-assembly

## Installation 

(If you don't have sbt installed on your machine, please follow this instruction: http://www.scala-sbt.org/release/tutorial/Setup.html)

(If you don't have docker installed on your machine, please follow this instruction: https://docs.docker.com/installation/)

```sh
$ sbt docker
```

```sh
$ docker run apiumtech/sbt-docker-example
```

## Overview

- Add sbt-docker plugin

```sh
# project/plugin.sbt
addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.2.0")
```

- Create a file named assembly in /app-directory/project
- Add sbt-assembly plugin

```sh
# project/target
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")
```

- Docker file is defined in /app-directory/project/Build.scala

### Overriding "sbt run"

```sh
# project/Build.scala
lazy val root = (project in file(".") ).aggregate(foo, bar).enablePlugins(DockerPlugin).settings(
   	run := {
       	(run in bar in Compile).evaluated
   	}
```

- With the above code added, when we run "sbt run", the Main method of [bar] project will be called instead of the one in [root]
- In the case we have a multi-module project, we can use this to point directly to the project that we want to execute instead of having to do something like "`sbt` `'project bar'` `'run'`"