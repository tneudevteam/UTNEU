name := "EASYTNEU"

version := "1.0"

scalaVersion := "2.11.8"

lazy val `easytneu` = (project in file(".")).enablePlugins(PlayScala)

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(cache , ws   , specs2 % Test,
  "com.aerospike"  %   "aerospike-client" % "3.2.5",
  "com.typesafe"   %   "config"           % "1.3.0",
//  "javax.inject"   %   "javax.inject"     % "1",
//  "ch.qos.logback" %   "logback-classic"  % "1.2.1",
  "org.mindrot"    %   "jbcrypt"          % "0.3m"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"