package main.java.com.tinyurl;

import com.couchbase.client.java.*;
import com.couchbase.client.java.document.*;
import com.couchbase.client.java.document.json.*;


public class cbDB implements database {

    Cluster cluster = null;
    Bucket bucket = null;

    public void connect(String bucket_name, String password) {
        cluster = CouchbaseCluster.create();
        bucket = cluster.openBucket(bucket_name, password);
    }

    public void setter(String key, String lurl, String surl) {
        JsonObject user = JsonObject.empty()
                .put("longurl", lurl)
                .put("shorturl", surl);
        JsonDocument doc = JsonDocument.create(key, user);
        JsonDocument response = (JsonDocument) bucket.upsert(doc);
    }

    public String getter(String key, String whichurl) {

        JsonDocument url = bucket.get(key);
        if (url != null)
             return url.content().getString(whichurl);
        return null;
    }

    public void disconnect() {
        bucket.close();
        cluster.disconnect();
    }
}
