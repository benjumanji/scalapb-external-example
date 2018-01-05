Protocol sharing with scalapb
=============================

This project contains two "projects". A client and a server. The server publishes a _pure_ protobuf jar. No dependencies. I can't figure out how to trick the other server projects to depend on it directly, so I went the cheeky route and symlinked them into the protocol subproject. The client depends on the "published" protobuf jar. You can test this with `publishLocal` to get it into your local ivy. The client depends on this jar with a `protobuf` configuration. This pulls it into the `protobuf_external` folder by default. We'd like to get the generated code from it, so we include the parent folder then filter out other things in that folder. Why? So we can use the same structure in our includes as in the jar.

## Why the faff?

Different code gen settings even across scalapb mean that if you say lump the protos into a folder, publish code into it and then publish _that_ you are pinning you .protos to a given scala binary version, and forcing downstream dependencies to use identical code gen (think `flatPackage` and frields) if they do any includes. It also makes the jar totally annoying for other jvm users.
