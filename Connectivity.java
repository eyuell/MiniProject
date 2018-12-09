package MiniProject;

public class Connectivity {
    private String startOrFinish;
    private Task linkedTo;
    private long connectivityDuration;

    public Connectivity(Task linkedTo, String startOrFinish, long connectivityDuration){
        this.startOrFinish = startOrFinish;
        this.linkedTo = linkedTo;
        this.connectivityDuration = connectivityDuration;
    }

    public Task getLinkedTo() {
        return linkedTo;
    }

    public String getStartOrFinish() {
        return startOrFinish;
    }

    public long getConnectivityDuration() {
        return connectivityDuration;
    }

    public void setConnectivityDuration(long connectivityDuration) {
        this.connectivityDuration = connectivityDuration;
    }

    public void setLinkedTo(Task linkedTo) {
        this.linkedTo = linkedTo;
    }

    public void setStartOrFinish(String startOrFinish) {
        this.startOrFinish = startOrFinish;
    }
}