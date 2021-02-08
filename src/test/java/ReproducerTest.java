import io.vertx.db2client.DB2Pool;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.sqlclient.SqlConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class ReproducerTest {
    private static final String CONNECTION = "db2://db2fenc1:password@localhost:50000/testdb";


    /*
        $ db2 "CREATE TABLE users (id VARCHAR(3) NOT NULL) IN DATABASE TESTDB;"
        $db2 INSERT INTO users VALUES('longer')
        DB21034E  The command was processed as an SQL statement because it was not a
        valid Command Line Processor command.  During SQL processing it returned:
        SQL0433N  Value "longer" is too long.  SQLSTATE=22001
     */
    @Test
    public void bug(VertxTestContext context) {
        final String create = "CREATE TABLE length (id VARCHAR(3) NOT NULL) IN DATABASE TESTDB;";
        final String insert = "INSERT INTO length VALUES('longer')";
        getPool().getConnection(connection -> {
            if(connection.succeeded()) {
                final SqlConnection conn = connection.result();
                conn.query(create).execute(creation -> {
                    if(creation.succeeded()) {
                        conn.query(insert).execute(insertion -> {
                            if(insertion.succeeded()) {
                                context.completeNow();
                            } else {
                                context.failNow(insertion.cause());
                            }
                            conn.close();
                        });
                    } else {
                        conn.close();
                        context.failNow(creation.cause());
                        // Release the connection to the pool
                    }
                });
            } else {
                context.failNow(connection.cause());
            }
        });
    }

    private static DB2Pool getPool() {
        return DB2Pool.pool(CONNECTION);
    }

    @AfterAll
    static void afterAll(VertxTestContext context) {
        final DB2Pool pool = getPool();
        pool.query("DROP TABLE length").execute(result -> {
            pool.close();
            if(result.failed()) {
                context.failNow(result.cause());
            } else {
                context.completeNow();
            }
        });
    }
}
