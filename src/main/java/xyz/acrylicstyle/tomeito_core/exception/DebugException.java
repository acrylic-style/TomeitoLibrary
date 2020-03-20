package xyz.acrylicstyle.tomeito_core.exception;

@SuppressWarnings("unused")
@Deprecated
public class DebugException extends Exception {
    public DebugException() {
        super("(THIS IS NOT AN ERROR)");
    }
}
