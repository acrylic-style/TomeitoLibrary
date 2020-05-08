package xyz.acrylicstyle.tomeito_api.subcommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks class as SubCommand.<br />
 * Annotated class must extend SubCommandExecutor or it will fail to load command.<br />
 * Class must be registered as sub command using {@link xyz.acrylicstyle.tomeito_api.BaseTomeitoAPI#registerCommands(ClassLoader, String, String)}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SubCommand {
    String name();
    String usage();
    String description();
}
