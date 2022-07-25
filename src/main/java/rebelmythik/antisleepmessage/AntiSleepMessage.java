package rebelmythik.antisleepmessage;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class AntiSleepMessage extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        if(!getServer().getPluginManager().isPluginEnabled("ProtocolLib")){
            getLogger().info("ProtocolLib is required for thisplugin to work");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if(event.getPacket().equals(PacketType.Play.Server.CHAT)) {
                    PacketContainer packet = event.getPacket();
                    List<WrappedChatComponent> components = packet.getChatComponents().getValues();
                    for (WrappedChatComponent component : components) {
                        if (component.getJson().contains("\"translate\":\"sleep.players_sleeping\"")) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
