name := "streifen"

version := "0.0.2-SNAPSHOT"

organization := "me.frmr.stripe"

scalaVersion := "2.11.4"

resolvers += "Farmdawg Temp Forks" at "http://dl.bintray.com/farmdawgnation/temp-forks"

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
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
