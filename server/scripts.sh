# init:
curl -i -H "Accept: application/json,text/event-stream" --data '{
       "jsonrpc": "2.0",
       "id": 0,
       "method": "initialize",
       "params":{
         "protocolVersion" : "2025-11-25",
         "clientInfo" : {
           "name" : "custom",
           "version" : "1.0"
         }
     }
 }' localhost:8080/mcp

 curl -i -H "Accept: application/json,text/event-stream" --data '{"jsonrpc": "2.0","id": 0,"method": "initialize","params":{"protocolVersion" : "2025-11-25","clientInfo" : {"name" : "custom","version" : "1.0"}}}' localhost:8080/mcp

curl -H "Accept: application/json,text/event-stream" --data '{
  "jsonrpc" : "2.0",
  "id" : 1,
  "method" : "tools/list"
 }' localhost:8080/mcp | jq ".result"

 curl -H "Accept: application/json,text/event-stream" --data '{
   "jsonrpc" : "2.0",
   "id" : 2,
   "method" : "tools/list",
   "params" : {
     "cursor" : "MjAyNi0wMy0xMFQxNjoyMTo0OS41MTcwMDY5MzdaJCQkc2FtcGxlZA=="
   }

  }' localhost:8080/mcp | jq ".result.tools[].name, .result.nextCursor"


{
  "jsonrpc" : "2.0",
  "id" : 2,
  "method" : "tools/call",
  "params" : {
    "name" : "elicitation"
  }
}