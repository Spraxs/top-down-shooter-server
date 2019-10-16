package nl.jaimyputter.server.modules.world.creatures;

import com.spraxs.js.modules.network.packet.SendablePacket;
import nl.jaimyputter.server.modules.network.client.Client;

import java.util.logging.Logger;

/**
 * Created by Spraxs
 * Date: 25-9-2018
 */

public class Player extends Creature {
    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());
    private static final String RESTORE_CHARACTER = "SELECT * FROM characters WHERE name=?";
    private static final String STORE_CHARACTER = "UPDATE characters SET name=?, class_id=? WHERE account=? AND name=?";

    private final Client _client;
    private final String _name;
    private int _classId = 0;
    // private final Inventory _inventory;

    public Player(Client client, String name)
    {
        _client = client;
        _name = name;

        /*
        // Load information from database.
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement ps = con.prepareStatement(RESTORE_CHARACTER))
        {
            ps.setString(1, name);
            try (ResultSet rset = ps.executeQuery())
            {
                while (rset.next())
                {
                    _classId = rset.getInt("class_id");
                    getLocation().setX(rset.getFloat("x"));
                    getLocation().setY(rset.getFloat("y"));
                    getLocation().setZ(rset.getFloat("z"));
                    // TODO: Restore player stats (STA/STR/DEX/INT).
                    // TODO: Restore player level.
                    // TODO: Restore player Current HP.
                    // TODO: Restore player Current MP.
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.warning(e.getMessage());
        }
        */

        // Initialize inventory.
        //_inventory = new Inventory(_name);
        // TODO: Send inventory slotId/itemId list packet (InventoryInformation) to client.
    }


    //TODO Setup StoreME
    public void storeMe()
    {
        /*
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement ps = con.prepareStatement(STORE_CHARACTER))
        {
            ps.setString(1, _name);
            ps.setInt(2, _classId);
            // TODO: Save location.
            // TODO: Save player stats (STA/STR/DEX/INT).
            // TODO: Save player level.
            // TODO: Save player Current HP.
            // TODO: Save player Current MP.
            ps.setString(3, _client.getAccountName());
            ps.setString(4, _name);
            ps.execute();
        }
        catch (Exception e)
        {
            LOGGER.warning(e.getMessage());
        }
        // Save inventory.
        _inventory.store(_name);
        */
    }


    public Client getClient()
    {
        return _client;
    }

    public String getName()
    {
        return _name;
    }

    public int getClassId()
    {
        return _classId;
    }

    /*
    public Inventory getInventory()
    {
        return _inventory;
    }*/

    public void channelSend(SendablePacket packet)
    {
        _client.channelSend(packet);
    }

    @Override
    public void onDeath() {
        super.onDeath();

        // TODO: Send player confirm dialog to go at nearest re-spawn location.
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public Player asPlayer() {
        return this;
    }
}