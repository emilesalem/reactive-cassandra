package reactive_cassandra;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactive_cassandra.repository.Repository;
import reactive_cassandra.repository.TableA.PartitionKey;
import reactive_cassandra.repository.TableA.PrimaryKey;
import reactor.core.publisher.Flux;

public class Application {

    protected static Logger logger;

    static {
        logger = LoggerFactory.getLogger(Application.class.getName());
    }

    public static void main(String[] argv) {
        Repository repo = new Repository();

        repo.init().subscribe(
                s -> {
                    repo.selectFromTableA(new PartitionKey("partition1"))
                            .subscribe(reactiveRow ->
                                    logger.info(reactiveRow.getFormattedContents())
                            );
                    repo.selectFromTableA(
                            new PrimaryKey(new PartitionKey("partition2"), "cluster2"))
                            .subscribe(reactiveRow ->
                                    logger.info(reactiveRow.getFormattedContents())
                            );
                }
        );
        Flux.interval(Duration.ofSeconds(1))
                .subscribe(x -> logger.info("time elapsed {}", x));
    }
}
