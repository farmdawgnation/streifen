name := "streifen"

version := "0.0.1-SNAPSHOT"

organization := "me.frmr.stripe"

scalaVersion := "2.11.2"

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  Seq(
    "net.liftweb"     %% "lift-common"  % liftVersion,
    "net.liftweb"     %% "lift-util"    % liftVersion,
    "net.liftweb"     %% "lift-json"    % liftVersion,
    "org.scalatest"   %% "scalatest"    % "2.2.1"        % "test"
  )
}
