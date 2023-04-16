package com.mradhit.lorevoter.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VotePartyEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public final int voteAmount;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public VotePartyEvent(int amount) {
        this.voteAmount = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
