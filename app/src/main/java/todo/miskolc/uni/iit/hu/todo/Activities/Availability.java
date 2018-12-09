package todo.miskolc.uni.iit.hu.todo.Activities;

import org.jetbrains.annotations.Contract;

public enum Availability {
    Busy(1),
    Free(2),
    Tentative(3);

    private int code;

    Availability(int code) {
        this.code = code;
    }

    @Contract(pure = true)
    public int getCode() {
        return code;
    }

    public void setCode(int code) throws IllegalArgumentException {
        if (code > 0 && code < 4) {
            this.code = code;
        } else {
            throw new IllegalArgumentException("Legal codes are 1, 2, 3!");
        }
    }
}
