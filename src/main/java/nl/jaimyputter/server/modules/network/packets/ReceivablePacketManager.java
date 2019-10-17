package nl.jaimyputter.server.modules.network.packets;

import nl.jaimyputter.server.modules.network.client.Client;
import nl.jaimyputter.server.modules.network.packets.receivable.PingServer;

/**
 * Created by Spraxs
 * Date: 25-9-2018
 */

public class ReceivablePacketManager {

    public static void handle(Client client, ReceivablePacket packet) {

        new PingServer(client, packet);

        switch (packet.readShort()) {
            case 0: {
                //    new AccountAuthenticationRequest(client, packet);
                break;
            }
            case 2: {
                //    new CharacterSelectionInfoRequest(client, packet);
                break;
            }
            case 3: {
                //    new CharacterCreationRequest(client, packet);
                break;
            }
            case 4: {
                //    new CharacterDeletionRequest(client, packet);
                break;
            }
            case 5: {
                //    new CharacterSlotUpdate(client, packet);
                break;
            }
            case 6: {
                //    new CharacterSelectUpdate(client, packet);
                break;
            }
            case 7: {
                //new EnterServerRequest(client, packet);
                break;
            }
            case 8: {
               // new LocationUpdate(client, packet);
                break;
            }
            case 9: {
                //new ObjectInfoRequest(client, packet);
                break;
            }
            case 10: {
                //    new ChatRequest(client, packet);
                break;
            }
            case 11: {
                //new LoadInWorld(client, packet);
                break;
            }
        }
    }
}