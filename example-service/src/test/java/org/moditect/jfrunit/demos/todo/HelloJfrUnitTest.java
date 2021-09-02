package org.moditect.jfrunit.demos.todo;

import org.junit.jupiter.api.Test;

import org.moditect.jfrunit.EnableEvent;
import org.moditect.jfrunit.JfrEventTest;
import org.moditect.jfrunit.JfrEvents;
import static org.moditect.jfrunit.JfrEventsAssert.*;

import java.time.Duration;

import static org.moditect.jfrunit.ExpectedEvent.*;

@JfrEventTest
public class HelloJfrUnitTest {

    public JfrEvents events = new JfrEvents();

    @Test
    @EnableEvent("jdk.GarbageCollection")
    @EnableEvent("jdk.ThreadSleep")
    public void basicTest() throws Exception {
        System.gc();
        Thread.sleep(1_000);

        events.awaitEvents();

        assertThat(events).contains(event("jdk.GarbageCollection"));
        assertThat(events).contains(event("jdk.ThreadSleep").with("time", Duration.ofSeconds(1)));

        events.events()
            .forEach(e -> System.out.println(e));
    }
}
