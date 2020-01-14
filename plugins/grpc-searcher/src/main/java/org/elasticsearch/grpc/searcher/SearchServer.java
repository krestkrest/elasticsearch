package org.elasticsearch.grpc.searcher;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.component.AbstractLifecycleComponent;

import java.io.IOException;

public class SearchServer extends AbstractLifecycleComponent {
    Server server;

    public SearchServer(Client client) {
        server = ServerBuilder.forPort(1337).addService(new SearchService(client)).build();
    }

    @Override
    protected void doStart() {
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doStop() {
        server.shutdownNow();
    }

    @Override
    protected void doClose() throws IOException {
        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }
}
