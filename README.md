Caching

TINYURL Caching solution comparison using Couchbase, MySQL, Java

#How to run mvn clean compile exec:java -Dexec.mainClass="main.java.com.tinyurl.tinyurlservice"

#HTTP service running at 8080

#POST request --------->

curl -v http://127.0.0.1:8080/postUrl -d "url:stackoverflow.com/questions/742013/how-to-code-a-url-shortener"

Trying 127.0.0.1...
TCP_NODELAY set
Connected to 127.0.0.1 (127.0.0.1) port 8080 (#0)
POST /postUrl HTTP/1.1 Host: 127.0.0.1:8080 User-Agent: curl/7.51.0 Accept: / Content-Length: 66 Content-Type: application/x-www-form-urlencoded
upload completely sent off: 66 out of 66 bytes < HTTP/1.1 200 OK < Date: Sun, 20 Aug 2017 00:51:14 GMT < Content-length: 28 <
Curl_http_done: called premature == 0
Connection #0 to host 127.0.0.1 left intact new_url:http://shortme/NtMM7
#GET request---------->

curl -v http://127.0.0.1:8080/GetUrl -d "url:NtMM7"

Trying 127.0.0.1...
TCP_NODELAY set
Connected to 127.0.0.1 (127.0.0.1) port 8080 (#0)
POST /GetUrl HTTP/1.1 Host: 127.0.0.1:8080 User-Agent: curl/7.51.0 Accept: / Content-Length: 9 Content-Type: application/x-www-form-urlencoded
upload completely sent off: 9 out of 9 bytes < HTTP/1.1 200 OK < Date: Sun, 20 Aug 2017 00:52:10 GMT < Content-length: 62 <
Curl_http_done: called premature == 0
Connection #0 to host 127.0.0.1 left intact stackoverflow.com/questions/742013/how-to-code-a-url-shortener
#couchbase used as caching and storage to store that

[client] -------------> [ HTTP tinyURL Server] -----> [Couchbase] [MySQL]
