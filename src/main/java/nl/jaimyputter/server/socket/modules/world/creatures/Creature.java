package nl.jaimyputter.server.socket.modules.world.creatures;

import lombok.Getter;
import lombok.Setter;
import nl.jaimyputter.server.socket.modules.world.WorldObject;
import nl.jaimyputter.server.socket.utils.Rnd;

/**
 * Created by Spraxs
 * Date: 25-9-2018
 */

public class Creature extends WorldObject {

    private static final int BASE_HP = 90;
    private static final int BASE_MP = 90;
    private static final float LEVEL_HP_MODIFIER = 1.1f;
    private static final float LEVEL_MP_MODIFIER = 1.2f;
    private static final int BASE_LAND_RATE = 70;
    private static final int BASE_CRIT_RATE = 5;
    private static final float MELEE_DAMAGE_MODIFIER = 1.2f;
    private static final float MELEE_SKILL_DAMAGE_MODIFIER = 1.4f;
    private static final float MAGIC_SKILL_DAMAGE_MODIFIER = 2.0f;

    private int _level = 1;
    private @Getter int hp = BASE_HP;
    private @Getter int mp = BASE_MP;
    private @Getter int sta = 10;
    private @Getter @Setter int str = 10;
    private @Getter @Setter int dex = 10;
    private int _int = 10;
    private boolean _isAlive = true;
    private WorldObject _target;

    public Creature() {
    }

    public int getLevel()
    {
        return _level;
    }

    public void setLevel(int level)
    {
        _level = level;
        // TODO: If player - Send Level status update to client.
        calculateHP();
        calculateMP();
    }

    public synchronized void setHP(int value) {
        if (hp < 1) {
            hp = 0;
            if (_isAlive)
            {
                _isAlive = false;
                onDeath();
            }
        } else {
            hp = value;
        }
    }

    public synchronized void setMP(int value) {
        mp = value;
    }

    public void setSTA(int value)
    {
        sta = value;
        calculateHP();
    }

    public int getINT()
    {
        return _int;
    }

    public void setINT(int value)
    {
        _int = value;
        calculateMP();
    }

    private void calculateHP()
    {
        final float hpModifier = _level * LEVEL_HP_MODIFIER;
        hp = (int) (BASE_HP + (sta * hpModifier));
        // TODO: If player - Send HP status update to client.
    }

    private void calculateMP()
    {
        final float mpModifier = _level * LEVEL_MP_MODIFIER;
        mp = (int) (BASE_MP + (_int * mpModifier));
        // TODO: If player - Send MP status update to client.
    }

    public boolean calculateHitSuccess(Creature enemy)
    {
        return Rnd.get(100) < ((BASE_LAND_RATE + dex) - enemy.getDex());
    }

    public boolean calculateHitCritical() {
        return Rnd.get(100) < (BASE_CRIT_RATE + dex);
    }

    public int calculateHitDamage() {
        final float hitModifier = _level * MELEE_DAMAGE_MODIFIER;
        return (int) ((str * hitModifier) + Rnd.get(-hitModifier, hitModifier));
    }

    public boolean calculateSkillSuccess(Creature enemy)
    {
        return Rnd.get(100) < ((BASE_LAND_RATE + dex) - enemy.getDex());
    }

    public boolean calculateSkillCritical()
    {
        return Rnd.get(100) < (BASE_CRIT_RATE + dex);
    }

    public int calculateMagicSkillDamage() {
        final float skillModifier = _level * MAGIC_SKILL_DAMAGE_MODIFIER;
        return (int) ((_int * skillModifier) + Rnd.get(-skillModifier, skillModifier));
    }

    public int calculateMeleeSkillDamage() {
        final float skillModifier = _level * MELEE_SKILL_DAMAGE_MODIFIER;
        return (int) ((str * skillModifier) + Rnd.get(-skillModifier, skillModifier));
    }

    public boolean isAlive()
    {
        return _isAlive;
    }

    public void setAlive(boolean value)
    {
        _isAlive = value;
    }

    public WorldObject getTarget()
    {
        return _target;
    }

    public void setTarget(WorldObject target)
    {
        _target = target;
    }

    public void onDeath()
    {
        // TODO: Send death animation.
    }

    @Override
    public boolean isCreature()
    {
        return true;
    }

    @Override
    public Creature asCreature()
    {
        return this;
    }

    /**
     * Verify if object is instance of Player.
     * @return {@code true} if object is instance of Player, {@code false} otherwise.
     */
    public boolean isPlayer()
    {
        return false;
    }

    /**
     * @return {@link Player} instance if current object is such, {@code null} otherwise.
     */
    public Player asPlayer()
    {
        return null;
    }
}