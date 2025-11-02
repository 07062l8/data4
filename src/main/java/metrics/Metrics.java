package metrics;

public class Metrics {
    private long timeNano = 0;
    private long dfsVisits = 0;
    private long dfsEdges = 0;
    private long kahnPushes = 0;
    private long kahnPops = 0;
    private long kahnEdges = 0;
    private long relaxations = 0;

    public void reset() {
        timeNano = 0;
        dfsVisits = dfsEdges = kahnPushes = kahnPops = kahnEdges = relaxations = 0;
    }
    public void setTimeNano(long t){ timeNano = t; }
    public long getTimeNano(){ return timeNano; }

    public void incDfsVisits(){ dfsVisits++; }
    public void incDfsEdges(){ dfsEdges++; }
    public void incKahnPushes(){ kahnPushes++; }
    public void incKahnPops(){ kahnPops++; }
    public void incKahnEdges(){ kahnEdges++; }
    public void incRelaxations(){ relaxations++; }

    public long getDfsVisits(){ return dfsVisits; }
    public long getDfsEdges(){ return dfsEdges; }
    public long getKahnPushes(){ return kahnPushes; }
    public long getKahnPops(){ return kahnPops; }
    public long getKahnEdges(){ return kahnEdges; }
    public long getRelaxations(){ return relaxations; }

    @Override
    public String toString() {
        return String.format("time(ns)=%d, dfsVisits=%d, dfsEdges=%d, kahnPushes=%d, kahnPops=%d, kahnEdges=%d, relax=%d",
                timeNano, dfsVisits, dfsEdges, kahnPushes, kahnPops, kahnEdges, relaxations);
    }
}
