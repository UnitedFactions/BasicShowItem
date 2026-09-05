package uk.firedev.basicshowitem;

import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.LongSupplier;

public final class CooldownTracker {

    private final long cooldownMillis;
    private final LongSupplier clock;
    private final Map<UUID, Long> nextUse = new HashMap<>();

    public CooldownTracker(long cooldownSeconds) {
        this(cooldownSeconds, System::currentTimeMillis);
    }

    CooldownTracker(long cooldownSeconds, @NonNull LongSupplier clock) {
        this.cooldownMillis = cooldownSeconds * 1000L;
        this.clock = clock;
    }

    public long remainingSeconds(@NonNull UUID playerId) {
        long remaining = nextUse.getOrDefault(playerId, 0L) - clock.getAsLong();
        return remaining <= 0 ? 0 : (remaining + 999L) / 1000L;
    }

    public void markUsed(@NonNull UUID playerId) {
        nextUse.put(playerId, clock.getAsLong() + cooldownMillis);
    }
}
