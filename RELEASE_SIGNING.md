# Release signing

The factory generated a local Android upload key for this project.

Signing files:
- upload-keystore.jks and keystore.properties

Build Android App Bundle locally:

```bash
./gradlew bundleRelease
```

AAB output:

```text
app/build/outputs/bundle/release/app-release.aab
```

Keep the keystore and properties file private. If this app is uploaded to Google Play,
the same upload key is needed for future releases unless you reset the upload key in
Google Play Console.
