package main.google.web.driver.custom;

import org.opentest4j.AssertionFailedError;

public class AssertionNoAsserts extends AssertionFailedError {

    private final String todo;

    private AssertionNoAsserts(String todo) {
        super("No asserts.", new Throwable(todo));
        this.todo = todo;
    }

    public String getTodo() {
        return todo;
    }

    public static void fail(String todo) {
        throw new AssertionNoAsserts(todo);
    }

    @Override
    public String toString() {
        return "AssertionNoAsserts{\n" +
                "\tmessage='" + super.getMessage() + "'," +
                "\ttodo='" + todo + "'\n" +
                "}";
    }
}
