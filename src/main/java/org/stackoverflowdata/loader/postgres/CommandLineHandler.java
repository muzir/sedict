package org.stackoverflowdata.loader.postgres;

import java.util.HashMap;
import java.util.Map;

public class CommandLineHandler {

    private Map<String, String> commandLineArgs;

    public CommandLineHandler(String[] args) {
        commandLineArgs = parseCommandLineArgs(args);
    }

    private Map<String, String> parseCommandLineArgs(String[] args) {
        Map<String, String> parsedArgs = new HashMap<>();
        String currentFlag = null;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.startsWith("--")) {
                // Found a flag
                currentFlag = arg.substring(2); // Remove leading "--"
            } else if (currentFlag != null) {
                // Found an argument for the current flag
                parsedArgs.put(currentFlag, arg);
                currentFlag = null; // Reset the current flag
            }
        }

        return parsedArgs;
    }

    public String getFilePath() {
        return commandLineArgs.get("file");
    }
}
