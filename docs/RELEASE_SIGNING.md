# Release signing

`assembleRelease` signs the APK only when a keystore is available:

| Source | Used by | Variables |
|---|---|---|
| Repository secrets | GitHub Actions | `TV_KEYSTORE_BASE64`, `TV_KEYSTORE_PASSWORD`, `TV_KEY_ALIAS` |
| `keystore.properties` in the project root (gitignored) | local builds | `storeFile`, `storePassword`, `keyAlias`, `keyPassword`, `storeType` |

Without either, the release job still builds but the APK is **unsigned** (not installable) and a
workflow warning says so. The keystore is never committed: this repository is public, and anyone
holding the private key could sign updates that install over your app.

## One-time setup (GitHub web UI)

The automation token used by Arena cannot manage repository secrets, so this needs one manual
step with your own account:

1. You already have a generated keystore (`release.p12`, PKCS#12, alias `tvapp-release`,
   30-year validity) — keep the copy handed to you safe; it is the identity of every future
   update.
2. Encode it:
   ```bash
   base64 -w0 release.p12 > release.p12.b64
   ```
3. On GitHub: **Settings → Secrets and variables → Actions → New repository secret**, three times:
   * `TV_KEYSTORE_BASE64` ← contents of `release.p12.b64`
   * `TV_KEYSTORE_PASSWORD` ← the keystore password
   * `TV_KEY_ALIAS` ← `tvapp-release`

   Or with your own `gh` login:
   ```bash
   gh secret set TV_KEYSTORE_BASE64   < release.p12.b64
   gh secret set TV_KEYSTORE_PASSWORD < password.txt
   gh secret set TV_KEY_ALIAS         <<< "tvapp-release"
   ```
4. Push anything (or re-run the workflow): the *Assemble signed release APK* job now uploads a
   signed, minified APK and the *Verify signature* step prints its certificate fingerprint.

## Local signed builds

Put `keystore.properties` next to `settings.gradle`:

```properties
storeFile=release.p12
storePassword=<password>
keyAlias=tvapp-release
keyPassword=<password>
storeType=PKCS12
```

then `./gradlew assembleRelease` → `app/build/outputs/apk/release/app-release.apk`.

## Generating your own keystore instead

```bash
openssl req -x509 -newkey rsa:2048 -sha256 -days 10950 -nodes \
  -keyout release.key.pem -out release.cert.pem \
  -subj "/CN=<App Name>/OU=Android TV/O=<You>/C=<CC>"
openssl pkcs12 -export -inkey release.key.pem -in release.cert.pem \
  -out release.p12 -name tvapp-release -passout pass:<password>
```

(Or `keytool -genkeypair -keystore release.jks -alias tvapp-release -keyalg RSA -keysize 2048
-validity 10950` and set `storeType=JKS`.)

**Back the keystore up.** Losing it means users cannot upgrade and must uninstall first; it
cannot be recovered from the repository.
