package uk.firedev.basicshowitem.tag;

import org.bukkit.inventory.EquipmentSlot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ItemTagResolverTest {

    @Test
    void mapsOnlyStandaloneSupportedTags() {
        assertEquals(EquipmentSlot.HAND, ItemTagResolver.slotForExactToken("[item]"));
        assertEquals(EquipmentSlot.HAND, ItemTagResolver.slotForExactToken(" [I] "));
        assertEquals(EquipmentSlot.OFF_HAND, ItemTagResolver.slotForExactToken("[offhand]"));
        assertEquals(EquipmentSlot.HEAD, ItemTagResolver.slotForExactToken("[head]"));
        assertNull(ItemTagResolver.slotForExactToken("look at [item]"));
        assertNull(ItemTagResolver.slotForExactToken("hello"));
    }
}
