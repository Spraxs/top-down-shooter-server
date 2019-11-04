package nl.jaimyputter.server.websocket.modules.packet.packets.out;

import nl.jaimyputter.server.websocket.modules.packet.packets.PacketOut;

public class PacketOutPlayerConnect extends PacketOut {

    public int level;
    public double speed;
    public double damage;
    public long timeInMillis;

    @Override
    public void onDataPrepare() {
        id = 0;

        level = 5;

        speed = 1.0d;
        damage = 2.5d;

        timeInMillis = System.currentTimeMillis();
    }
}
