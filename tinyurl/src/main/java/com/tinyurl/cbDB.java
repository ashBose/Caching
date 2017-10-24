package com.tinyurl;

import com.couchbase.client.java.*;
import com.couchbase.client.java.document.*;
import com.couchbase.client.java.document.json.*;
import org.apache.log4j.Logger;


public class cbDB implements database {

    Cluster cluster = null;
    Bucket bucket = null;
    Logger logger = tinyLogger.getInstance().getLogger();
    long startedAt;

    public void connect(String bucket_name, String password) {
        cluster = CouchbaseCluster.create();
        bucket = cluster.openBucket(bucket_name, password);
    }

    public void setter(String key, String lurl, String surl) {
        startedAt = System.currentTimeMillis();
        JsonObject user = JsonObject.empty()
                .put("longurl", lurl)
                .put("shorturl", surl);
        JsonDocument doc = JsonDocument.create(key, user);
        JsonDocument response = (JsonDocument) bucket.upsert(doc);
        logger.info("NoSQL setter completed . It took: " + (System.currentTimeMillis() - startedAt));
    }

    public String getter(String key, String whichurl) {
        startedAt = System.currentTimeMillis();
        JsonDocument url = bucket.get(key);
        logger.info("NoSQL getter completed . It took: " + (System.currentTimeMillis() - startedAt));
        if (url != null)
             return url.content().getString(whichurl);
        return null;
    }

    public void disconnect() {
        bucket.close();
        cluster.disconnect();
    }
}
