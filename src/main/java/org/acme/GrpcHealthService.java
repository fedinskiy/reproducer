package org.acme;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@GrpcService
// todo this should return statuses, but it isn't failing the deployment
public class GrpcHealthService implements Health {

    @Override
    public Uni<HealthOuterClass.HealthCheckResponse> check(HealthOuterClass.HealthCheckRequest request) {
        return Uni.createFrom().failure(new RuntimeException("Error!"));
    }

    @Override
    public Multi<HealthOuterClass.HealthCheckResponse> watch(HealthOuterClass.HealthCheckRequest request) {
        return Multi.createFrom().failure(new RuntimeException("Error!"));
    }
}
