package uet.oop.bomberman;

public class StopWatch {
    public final long TIME;
    public long start = 0;
    public long end = 0;
    public boolean check_end = false;

    public StopWatch(long TIME) {
        this.TIME = TIME;
    }

    public boolean checkEnd() {
        if (start == 0) {
            start = System.currentTimeMillis();
        }
        end = System.currentTimeMillis();
        if (end - start >= TIME) {
            check_end = true;
        }
        return check_end;
    }

    public void setStart() {
        this.check_end = false;
        this.start = 0;
    }

    public boolean getCheckEnd() {
        return this.check_end;
    }
}
