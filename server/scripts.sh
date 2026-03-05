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

curl -H "Accept: application/json,text/event-stream" --data '{
  "jsonrpc" : "2.0",
  "id" : 1,
  "method" : "tools/list"
 }' localhost:8080/mcp

{
  "jsonrpc" : "2.0",
  "id" : 1,
  "method" : "tools/call",
  "params" : {
    "name" : "filereader",
    "arguments" : {
      "file" : "robot-readable.txt"
    }
  }