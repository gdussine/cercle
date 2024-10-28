package bot.view;

import java.util.Arrays;

public class ExceptionView extends EmbedView {

    
    private Throwable exception;

    public ExceptionView(Throwable exception) {
        this.exception = exception;
        this.template.setTitle(":no_entry: Exception");
        this.setMessage();
        this.setStackTrace();
    }

    private void setMessage() {
        this.template.addField("Message", exception.getMessage(), false);
    }

    private void setStackTrace(){
        StringBuilder stackTraceBuilder = new StringBuilder();
        Arrays.stream(this.exception.getStackTrace()).forEach(x->stackTraceBuilder.append(x.toString()).append("\n\t"));
        this.template.addField("Stack Trace", "```java\n%s\n```".formatted(stackTraceBuilder.toString().trim()), false);
    }
}
