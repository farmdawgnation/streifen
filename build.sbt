name := "streifen"

version := "0.1.0"

organization := "me.frmr.stripe"

scalaVersion := "2.12.4"

libraryDependencies ++= {
  val liftVersion = "3.2.0"
  Seq(
    "net.liftweb"               %% "lift-common"          % liftVersion,
    "net.liftweb"               %% "lift-util"            % liftVersion,
    "net.liftweb"               %% "lift-json"            % liftVersion,
    "net.databinder.dispatch"   %% "dispatch-core"        % "0.13.3",
    "net.databinder.dispatch"   %% "dispatch-lift-json"   % "0.13.3",
    "org.scalatest"             %% "scalatest"            % "3.0.5"        % "test"
  )
}

initialCommands := """
import me.frmr.stripe._
import scala.concurrent._
import scala.concurrent.duration._
"""

enablePlugins(BuildInfoPlugin)

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)

buildInfoPackage := "me.frmr.stripe"

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
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
