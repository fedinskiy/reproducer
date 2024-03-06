package io.quarkus.ts.sqldb.panacheflyway;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;

@ApplicationScoped
@Path("/data-source")
public class DataSourceResource {
    @Inject
    AgroalDataSource defaultDataSource;

    @GET
    @Path("/default/connection-provider-class")
    public String defaultConnectionProviderClass() {
        return getConnectionProviderClass(defaultDataSource);
    }

    private String getConnectionProviderClass(AgroalDataSource dataSource) {
        return dataSource.getConfiguration().connectionPoolConfiguration()
                .connectionFactoryConfiguration().connectionProviderClass().getName();
    }
}
