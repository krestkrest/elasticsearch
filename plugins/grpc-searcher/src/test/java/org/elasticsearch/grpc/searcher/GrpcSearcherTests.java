package org.elasticsearch.grpc.searcher;

import org.elasticsearch.test.ESIntegTestCase;

@ESIntegTestCase.ClusterScope(supportsDedicatedMasters = false, numDataNodes = 0, numClientNodes = 0)
public class GrpcSearcherTests extends ESIntegTestCase {

    public void testSearch() {
        // start master node
        final String masterNode = internalCluster().startMasterOnlyNode();
        /*
        request = new CreateIndexRequest("twitter2");
                //tag::create-index-mappings-map
                Map<String, Object> message = new HashMap<>();
                message.put("type", "text");
                Map<String, Object> properties = new HashMap<>();
                properties.put("message", message);
                Map<String, Object> mapping = new HashMap<>();
                mapping.put("properties", properties);
                request.mapping(mapping); // <1>
                //end::create-index-mappings-map
                CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
         */
        createIndex("test");
        index("test", "doc", "1", "");
    }
}
