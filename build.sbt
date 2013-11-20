name := "sphinx"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "de.undercouch" % "bson4jackson" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.0" force(),
  "com.fasterxml.jackson.core" % "jackson-core" % "2.1.0" force(),
  "org.mongodb" % "mongo-java-driver" % "2.11.3",
  "org.jongo" % "jongo" % "0.4",
  "uk.co.panaxiom" %% "play-jongo" % "0.6.0-jongo0.4",
  "org.glassfish.jersey.core" % "jersey-client" % "2.4"
)     

play.Project.playJavaSettings
