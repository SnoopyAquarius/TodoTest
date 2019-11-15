package example.snoopyaquarius.todotest.model;

/**
 * Created by snoopyaquarius on 2019/11/15.
 */

public class Todo {

    private String content;
    private boolean complete;

    public Todo() {
    }

    public Todo(String content, boolean complete) {
        this.content = content;
        this.complete = complete;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "content='" + content + '\'' +
                ", complete=" + complete +
                '}';
    }
}
