# FreeLogJ
### Start a conversation in your company about paying open source maintainers!

In the meantime enjoy this simple logging framework^W class.

### Features:

- It's a single Class! (+Enum). Easy to understand and audit.

- API similar to SLF4j, varargs log parameters are substituted for braces {}

- isXEnabled() for wrapping expensive log sections.

- error() and fatal() have a no-varargs signature that accepts a Throwable to get a stack trace.

- Log levels: TRACE, DEBUG, INFO, WARN, ERROR, FATAL. Log level is not modifiable after logger is created.

- No config files. You can pass your own DateFormat and PrintStream to the constructor.

- Default PrintStream is System.out, if you want file logging then pass a PrintStream that wraps an OutputStream.

### Caveats:

- No LoggerFactory class. If enough people ask I'll make one (for easier drop-in replacement).

- Performance is probably meh. If you're logging that much then consider JFR events!

### But seriously

The Log4j team work hard and deserve your respect. So much of the Java ecosystem depends on them (and all the other OSS maintainers).

We can all benefit from taking some time to understand our software dependencies better and whether any functionality they contain might clash with our security requirements.

Much love, @chriswhocodes

