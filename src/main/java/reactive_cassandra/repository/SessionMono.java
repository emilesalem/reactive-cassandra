package reactive_cassandra.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

public class SessionMono extends Mono<CqlSession> {

    protected static Logger logger;

    static {
        logger = LoggerFactory.getLogger(SessionMono.class.getName());
    }

    @Override
    public void subscribe(CoreSubscriber<? super CqlSession> actual) {
        CqlSession.builder().buildAsync()
                .handle(((cqlSession, throwable) -> {
                    if (throwable != null) {
                        logger.error("cassandra connection error", throwable);
                        throw Exceptions.propagate(throwable);
                    }
                    Mono.just(cqlSession)
                            .subscribe(actual);
                    return null;
                }));
    }
}
