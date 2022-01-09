> Task :compileGatlingScala FAILED
[Error] /Users/laymui/dev/taiger/restapi-gatling/src/gatling/scala/restapi/message.scala:29: value feed is not a member of io.gatling.http.request.builder.HttpRequestBuilder
possible cause: maybe a semicolon is missing before `value feed`?
one error found


Fix:
```
The compiler is telling you "feed" doesn't go there.
It goes at the same level as "exec".
```