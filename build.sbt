name := "streifen"

version := "0.0.4-SNAPSHOT"

organization := "me.frmr.stripe"

scalaVersion := "2.11.8"

resolvers += "Farmdawg Temp Forks" at "http://dl.bintray.com/farmdawgnation/temp-forks"

libraryDependencies ++= {
  val liftVersion = "2.6.3"
  Seq(
    "net.liftweb"               %% "lift-common"          % liftVersion,
    "net.liftweb"               %% "lift-util"            % liftVersion,
    "net.liftweb"               %% "lift-json"            % liftVersion,
    "net.databinder.dispatch"   %% "dispatch-core"        % "0.11.2",
    "net.databinder.dispatch"   %% "dispatch-lift-json"   % "0.11.2",
    "org.scalatest"             %% "scalatest"            % "2.2.1"        % "test"
  )
}

initialCommands := """
import me.frmr.stripe._
import scala.concurrent._
import scala.concurrent.duration._
"""

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) 
    Some("snapshots" at nexus + "content/repositories/snapshots") 
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

credentials += Credentials(Path.userHome / ".sonatype")

pomExtra :=
<url>https://github.com/farmdawgnation/streifen</url>
<licenses>
  <license>
    <name>Apache 2</name>
    <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    <distribution>repo</distribution>
  </license>
</licenses>
<scm>
  <url>https://github.com/farmdawgnation/streifen.git</url>
  <connection>https://github.com/farmdawgnation/streifen.git</connection>
</scm>
<developers>
  <developer>
    <id>farmdawgnation</id>
    <name>Matt Farmer</name>
    <email>matt@frmr.me</email>
  </developer>
</developers>
