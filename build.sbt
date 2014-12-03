name := "Sound Trainer"

version := "0.1"

scalaVersion := "2.10.4"

fork in run := true

connectInput in run := true

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies +=
"com.typesafe.akka" %% "akka-actor" % "2.3.7"