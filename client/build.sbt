lazy val root = project.in(file(".")).aggregate(client)

lazy val common = Seq(
  organization := "io.artfuldodge.boop",
  scalaVersion := "2.12.4"
)

lazy val client = project.in(file("client"))
  .settings(common)
  .settings(
    PB.protoSources in Compile += target.value / "protobuf_external",
    includeFilter in PB.generate := {
      val e = (target.value  / "protobuf_external").toPath
      new SimpleFileFilter((f: File) =>  {
        val p = f.toPath
        (!p.startsWith(e) || e.relativize(p).startsWith("boop/")) && p.getFileName.toString.endsWith(".proto")
      })
    },
    PB.targets in Compile += scalapb.gen(flatPackage=true) -> (sourceManaged in Compile).value,
    libraryDependencies ++= Seq(
      "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % com.trueaccord.scalapb.compiler.Version.scalapbVersion,
      "io.artfuldodge.boop" % "protobufs" % "0.1-SNAPSHOT" % "protobuf",
      "io.grpc" % "grpc-netty" % com.trueaccord.scalapb.compiler.Version.grpcJavaVersion
    )
  )
