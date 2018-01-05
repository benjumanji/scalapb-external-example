lazy val root = project.in(file(".")).aggregate(protobufs, protocol, boop)

lazy val common = Seq(
  organization := "io.artfuldodge.boop",
  scalaVersion := "2.12.4"
)

lazy val protobufs = project.in(file("protobufs"))
  .settings(common)
  .settings(
    autoScalaLibrary := false,
    crossPaths := false
  )
  
lazy val protocol = project.in(file("protocol"))
  .settings(common)
  .settings(
    PB.targets in Compile += scalapb.gen(flatPackage=true) -> (sourceManaged in Compile).value,
    libraryDependencies ++= Seq(
      "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % com.trueaccord.scalapb.compiler.Version.scalapbVersion,
      "io.grpc" % "grpc-netty" % com.trueaccord.scalapb.compiler.Version.grpcJavaVersion
    )
  )

lazy val boop = project.in(file("boop")).settings(common).dependsOn(protocol)
