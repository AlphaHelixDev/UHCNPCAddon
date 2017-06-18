package de.alphahelix.npcaddon.files;

import de.alphahelix.alphalibary.file.SimpleJSONFile;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.npcaddon.NPCAddon;
import de.alphahelix.npcaddon.instances.NPCOptions;

public class NPCFile extends SimpleJSONFile {
    public NPCFile() {
        super(NPCAddon.getInstance().getDataFolder().getAbsolutePath(), "npcs.json");
        init();
        NPCAddon.setNpcOptions(getValue("Options", NPCOptions.class));
    }

    private void init() {
        if (jsonContains("Options")) return;

        setValue("Options", new NPCOptions(
                "§3Stats",
                "§3Rewards",
                UUIDFetcher.getUUID("Zealock")
        ));
    }
}
