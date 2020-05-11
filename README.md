# kikora-api
Kotlin API Wrapper for [Kikora](https://feide-castor.kikora.no/beta/#). This wrapper supports kikora2.

## Disclaimer
I am **not** responsible for your actions using this library.
Kikora-api is not affiliated with Kikora in any way.

## Session ID
Unfortunately, there are currently no automated tools to login to Kikora with.
To retrieve `JSESSIONID`, login to [Kikora](https://feide-castor.kikora.no/beta/#) and grab `JSESSIONID` from cookies.

> **Inspect:** `Ctrl+Shift+I`   

## Example
See [KikoraExample.kt](src/test/kotlin/dev/cubxity/kikora/example/KikoraExample.kt) for more examples.

**personInfo:**
```kotlin
import dev.cubxity.kikora.KikoraAPI

/* ... */

val api = KikoraAPI("*Insert session ID here*")

val info = /* suspend */ api.personInfo()
println(info)
```

## Supported methods
- personInfo
- tileSet
- containerSet
- containers
- containerContent
- exercisePerson