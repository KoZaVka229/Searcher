package koz.sdamgiasr;

public class ResultOfSearch {
    private final String text;
    private final int taskId;

    public ResultOfSearch(String text, int taskId) {
        this.text = text;
        this.taskId = taskId;
    }

    public String getText() {
        return text;
    }
    public int getTaskId() {
        return taskId;
    }
}
