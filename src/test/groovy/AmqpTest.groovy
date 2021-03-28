import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@Testcontainers
class AmqpTest extends Specification {

    DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(new File("./docker-compose.yml"))
            .withExposedService("rabbitmq_1", 5672, Wait.forListeningPort())
            .waitingFor("consumer_1", Wait.forLogMessage(".*Started AppConsumer.*", 1))
            .withLocalCompose(true)

    def "Testing if consumer receives and prints out the message from queue"() {

        setup:
        def body = "message to broker"
        def rabbit = dockerComposeContainer.getContainerByServiceName "rabbitmq_1"
        def consumer = dockerComposeContainer.getContainerByServiceName "consumer_1"

        when:
        rabbit.get().execInContainer "rabbitmqadmin", "publish", "exchange=my-exchange", "routing_key=my-routing-key", "payload=${body}"

        then:
        consumer.get().getLogs().contains body
    }
}
