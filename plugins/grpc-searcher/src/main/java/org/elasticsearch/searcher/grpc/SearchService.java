package org.elasticsearch.searcher.grpc;

import io.grpc.stub.StreamObserver;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;

public class SearchService extends SearchServiceGrpc.SearchServiceImplBase {
    Client client;

    public SearchService(Client client) {
        this.client = client;
    }

    @Override
    public void search(Service.SearchRequest request, StreamObserver<Service.SearchResponse> responseObserver) {
        try {
            var query = QueryBuilders.queryStringQuery(request.getQuery());
            var searchResponse = client.prepareSearch(request.getIndex()).
                setQuery(query).setSize((int)request.getLimit()).
                execute().actionGet(new TimeValue(300));

            var hits = searchResponse.getHits().getHits();
            var ids = new ArrayList<String>();
            ids.ensureCapacity(hits.length);
            for (var hit: hits) {
                ids.add(hit.getId());
            }

            var response = Service.SearchResponse.newBuilder().
                addAllIds(ids).setTotal(hits.length).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
