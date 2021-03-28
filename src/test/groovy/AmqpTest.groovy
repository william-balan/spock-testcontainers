import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import java.time.Duration

import static org.awaitility.Awaitility.await

@Testcontainers
class AmqpTest extends Specification {

    DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(new File("./docker-compose.yml"))
            .withExposedService("rabbitmq_1", 5672, Wait.forListeningPort())
            .withLocalCompose(true)

    static final BODY = "message to broker"

    def setup() {
        def rabbit = dockerComposeContainer.getContainerByServiceName "rabbitmq_1"
        rabbit.get().execInContainer "rabbitmqadmin", "publish", "exchange=my-exchange", "routing_key=my-routing-key", "payload=${BODY}"
    }

    def "Testing if consumer receives and prints out the message from queue"() {

        setup:
        await().atMost Duration.ofSeconds(5)

        when:
        def consumer = dockerComposeContainer.getContainerByServiceName "kotlin-consumer_1"

        then:
        consumer.get().getLogs().contains BODY
    }
}
