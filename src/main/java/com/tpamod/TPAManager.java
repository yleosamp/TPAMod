package com.tpamod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TPAManager {
    private static Map<UUID, TPARequest> activeRequests = new HashMap<>();
    private static Map<UUID, Long> combatTimers = new HashMap<>();
    
    public static class TPARequest {
        public final UUID requester;
        public final UUID target;
        public final long requestTime;
        
        public TPARequest(UUID requester, UUID target) {
            this.requester = requester;
            this.target = target;
            this.requestTime = System.currentTimeMillis();
        }
    }
    
    public static boolean isInCombat(UUID player) {
        if (!combatTimers.containsKey(player)) return false;
        return System.currentTimeMillis() - combatTimers.get(player) < 10000;
    }
    
    public static void tagPlayerInCombat(UUID player) {
        combatTimers.put(player, System.currentTimeMillis());
    }
    
    public static void createRequest(UUID requester, UUID target) {
        activeRequests.put(target, new TPARequest(requester, target));
    }
    
    public static TPARequest getRequest(UUID target) {
        return activeRequests.get(target);
    }
    
    public static void removeRequest(UUID target) {
        activeRequests.remove(target);
    }
}
