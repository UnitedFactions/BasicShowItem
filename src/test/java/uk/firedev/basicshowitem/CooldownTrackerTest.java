package uk.firedev.basicshowitem;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CooldownTrackerTest {

    @Test
    void expiresWithoutOffByOneOrNegativeTime() {
        AtomicLong now = new AtomicLong(1_000L);
        CooldownTracker tracker = new CooldownTracker(20, now::get);
        UUID player = UUID.randomUUID();

        assertEquals(0, tracker.remainingSeconds(player));
        tracker.markUsed(player);
        assertEquals(20, tracker.remainingSeconds(player));
        now.addAndGet(19_001L);
        assertEquals(1, tracker.remainingSeconds(player));
        now.addAndGet(999L);
        assertEquals(0, tracker.remainingSeconds(player));
    }
}
