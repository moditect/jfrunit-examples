package org.moditect.jfrunit.demos.spring_jooq;

import java.util.concurrent.ThreadLocalRandom;
import jdk.jfr.consumer.RecordedEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.moditect.jfrunit.JfrEventTest;
import org.moditect.jfrunit.JfrEvents;
import org.moditect.jfrunit.demos.spring_jooq.generated.tables.records.TestUserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@JfrEventTest
public class SpringJooqGradleApplicationTests {

    @Autowired
    public TestUserService testUserService;

    public JfrEvents jfrEvents = new JfrEvents();

    @Test
    public void contextLoads() {
    }

    @Test
    public void createUser() {
        boolean success = testUserService.createUser(String.valueOf(ThreadLocalRandom.current().nextLong()),
                ThreadLocalRandom.current().nextInt());
        Assertions.assertThat(success).isTrue();

        jfrEvents.awaitEvents();
        Assertions.assertThat(jfrEvents.events().filter(this::isQueryEvent).count()).isEqualTo(1);
    }

    @Test
    public void createAndFindUser() {
        final String username = String.valueOf(ThreadLocalRandom.current().nextLong());
        final int age = ThreadLocalRandom.current().nextInt();
        boolean success = testUserService.createUser(username,
                age);
        Assertions.assertThat(success).isTrue();

        jfrEvents.awaitEvents();
        Assertions.assertThat(jfrEvents.events().filter(this::isQueryEvent).count()).isEqualTo(1);

        TestUserRecord ourUser = testUserService.getUserByUsername(username);
        Assertions.assertThat(ourUser).isNotNull();
        Assertions.assertThat(ourUser.getUsername()).isEqualTo(username);
        Assertions.assertThat(ourUser.getAge()).isEqualTo(age);

        jfrEvents.awaitEvents();
        Assertions.assertThat(jfrEvents.events().filter(this::isQueryEvent).count()).isEqualTo(2);
    }

    private boolean isQueryEvent(RecordedEvent event) {
        return event.getEventType().getName().equals("jooq.AbstractQuery") ||
            event.getEventType().getName().equals("jooq.AbstractResultQuery");
    }
}
