package nl.jaimyputter.server.websocket.framework.geometry;

import nl.jaimyputter.server.websocket.Server;
import nl.jaimyputter.server.websocket.modules.world.WorldModule;
import nl.jaimyputter.server.websocket.modules.world.framework.WorldObject;
import nl.jaimyputter.server.websocket.modules.world.framework.creatures.Player;

/**
 * Created by Spraxs
 * Date: 12/11/2019
 */

public class GeometryHelper {

    public static Player rayCastHitPlayer(Vector2 rayPosition, Vector2 rayDirection, Player... filteredPlayers) {

        WorldModule worldModule = Server.byModule(WorldModule.class);

        for (Player player : worldModule.getAllPlayers()) {
            boolean skipPlayer = false;

            for (Player filterPlayer : filteredPlayers) {
                if (filterPlayer.equals(player))  {
                    skipPlayer = true;
                    break;
                }
            }

            if (skipPlayer) {
                continue;
            }

            Player p = (Player) calculateRayCastHit(player, rayPosition, rayDirection);

            if (p == null) continue;

            return p;
        }

        return null;
    }

    private static WorldObject calculateRayCastHit(WorldObject worldObject, Vector2 rayPosition, Vector2 rayDirection) {

        BoxCollider2 boxCollider2 = worldObject.getBoxCollider2();

        if (boxCollider2 == null) return null;

        boolean hit;

        hit = calculateRayCastHitByBoundary(boxCollider2.getPointA(), boxCollider2.getPointB(), rayPosition, rayDirection); // A to B

        if (hit) return worldObject;

        hit = calculateRayCastHitByBoundary(boxCollider2.getPointB(), boxCollider2.getPointC(), rayPosition, rayDirection); // B to C

        if (hit) return worldObject;

        hit = calculateRayCastHitByBoundary(boxCollider2.getPointC(), boxCollider2.getPointD(), rayPosition, rayDirection); // C to D

        if (hit) return worldObject;

        hit = calculateRayCastHitByBoundary(boxCollider2.getPointD(), boxCollider2.getPointA(), rayPosition, rayDirection); // D to A

        if (hit) return worldObject;

        return null;
    }

    private static boolean calculateRayCastHitByBoundary(Vector2 beginPoint, Vector2 endPoint, Vector2 rayPosition, Vector2 rayDirection) {
        final float x1 = beginPoint.getX();
        final float y1 = beginPoint.getY();
        final float x2 = endPoint.getX();
        final float y2 = endPoint.getY();

        final float x3 = rayPosition.getX();
        final float y3 = rayPosition.getY();
        final float x4 = x3 + rayDirection.getX();
        final float y4 = y3 + rayDirection.getY();

        final float den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        // When den is 0, it means that the boundary line (begin- and endpoint) are parallel
        if (den == 0) {
            return false;
        }

        final float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
        final float u = -(((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den);

        return t > 0 && t < 1 && u > 0;
    }
}
