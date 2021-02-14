# TomeitoLib

Provides useful APIs to Bukkit!

Currently, compatible with: 1.8 - 1.16.4

There are no issues on 1.7.x so far.

Please note that this plugin itself does not modify game play. It just adds some useful commands and APIs.

Also, you may use this for other purposes if you want. See licence for more information.

With `/tl debug` command, you can debug anything, comes with tab completion. (it's like eval in Java)

## Use with maven
```xml
<repository>
    <id>acrylic-repo</id>
    <url>https://repo2.acrylicstyle.xyz</url>
</repository>
```

```xml
<dependency>
    <groupId>xyz.acrylicstyle</groupId>
    <artifactId>api</artifactId>
    <version>version</version>
</dependency>
```

## Use with build.gradle.kts
```kotlin
repositories {
    maven { url = uri("https://repo2.acrylicstyle.xyz/") }
}

dependencies {
    compileOnly("xyz.acrylicstyle:apiKt:version")
}
```

Replace api with apiKt if you use kotlin.

## Using in Server
[Download TomeitoLib and install it on your spigot server.](https://ci.acrylicstyle.xyz/guestAuth/repository/download/TomeitoLibrary_Build/533:id/tomeito_plugin/target/tomeito_plugin-0.5.20.jar)

## BungeeCord

Some feature (e.g. Retrieving Protocol Version without ViaVersion) requires you to install BungeeCord plugin.

You can download it from [here](https://ci.acrylicstyle.xyz/guestAuth/repository/download/TomeitoLibrary_Build/533:id/tomeito_bungee/target/tomeito_bungee-0.5.20.jar).

Then, put this plugin into your plugins folder. (Not spigot, it's BungeeCord plugin!)
