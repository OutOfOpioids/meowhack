package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.module.misc.Spammer;
import me.notkronos.meowhack.util.chat.ChatUtil;
import me.notkronos.meowhack.util.chat.MessageType;
import me.notkronos.meowhack.util.file.FileSystemUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpammerFile extends Command {

        public SpammerFile() {
            super("spammerfile", "Used to change the spammer file", new String[]{});
        }


        @Override
        public void onExecute(String[] args) {
            if (args.length != 1) {
                ChatUtil.commandFeedback("SpammerFile takes 1 argument. Correct usage is " + Meowhack.PREFIX + "spammerfile " + getUseCase(), MessageType.ERROR);
            }
            String filename = args[0];

            Path path = Paths.get(FileSystemUtil.getSpammerFile(filename).toString());

            if (!path.toFile().exists()) {
                ChatUtil.commandFeedback("File " + filename + ".txt does not exist", MessageType.ERROR);
                return;
            }

            String[] allLines = new String[0];

            try {
                allLines = Files.readAllLines(path).toArray(new String[0]);
                Spammer.INSTANCE.spam = allLines;
                Spammer.INSTANCE.isFileSet = true;
                ChatUtil.commandFeedback("Spammer file set to " + filename + ".txt", MessageType.SUCCESS);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        @Override
        public String getUseCase() {
            return "<name>";
        }

        @Override
        public int getArgSize() {
            return 1;
        }

        @Override
        public String getName() {
            return "SpammerFile";
        }
}
