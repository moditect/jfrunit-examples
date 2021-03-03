package org.moditect.jfrunit.demos.todo;

import org.junit.jupiter.api.Test;

import dev.morling.jfrunit.EnableEvent;
import dev.morling.jfrunit.JfrEventTest;
import dev.morling.jfrunit.JfrEvents;
import static dev.morling.jfrunit.JfrEventsAssert.*;

import java.time.Duration;

import static dev.morling.jfrunit.ExpectedEvent.*;

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

        events.stream()
            .forEach(e -> System.out.println(e));
    }
}
