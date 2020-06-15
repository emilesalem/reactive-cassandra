package reactive_cassandra.repository;


import com.datastax.dse.driver.api.core.cql.reactive.ReactiveRow;
import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Repository {

    protected static Logger logger;

    static {
        logger = LoggerFactory.getLogger(Repository.class.getName());
    }

    private CqlSession cqlSession;

    public Mono init() {
        return new SessionMono()
                .doOnNext(s -> this.cqlSession = s)
                .flatMap(s -> Mono.from(s.executeReactive("SELECT dse_version from system.local"))
                                .doOnNext(x -> logger.info("connected to cassandra version {}", x.getString("dse_version"))));
    }

    public Flux<ReactiveRow> selectFromTableA(TableA.PartitionKey partitionKey) {
        return Flux.from(cqlSession.executeReactive(TableA.Queries.selectPartition(partitionKey)));
    }

    public Flux<ReactiveRow> selectFromTableA(TableA.PrimaryKey primaryKey) {
        return Flux.from(cqlSession.executeReactive(TableA.Queries.selectRow(primaryKey)));
    }
}
