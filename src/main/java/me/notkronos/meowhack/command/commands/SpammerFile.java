package me.notkronos.meowhack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.module.misc.Spammer;
import me.notkronos.meowhack.util.chat.MessageSender;
import me.notkronos.meowhack.util.file.FileSystemUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpammerFile extends Command {

        public static SpammerFile INSTANCE;
        private final String name = "spammerfile";

        private final MessageSender messageSender = new MessageSender();

        public SpammerFile() {
            super("spammerfile", "Used to change the spammer file", new String[]{});
        }


        @Override
        public void onExecute(String[] args) {
            if (args.length == 1) {
                String filename = args[0];

                Path path = Paths.get(FileSystemUtil.getSpammerFile(filename).toString());

                if (path.toFile() != null) {
                    String[] allLines = new String[0];
                    try {
                        if(!path.toFile().exists()) {
                            messageSender.sendMessageClientSide(ChatFormatting.RED + "File " + filename + ".txt does not exist." + ChatFormatting.RESET + " Please create it in the Meowhack folder.");
                            return;
                        }
                        allLines = Files.readAllLines(path).toArray(new String[0]);
                        Spammer.INSTANCE.spam = allLines;
                        Spammer.INSTANCE.isFileSet = true;
                        messageSender.sendMessageClientSide(ChatFormatting.GREEN + "Spammer file set to " + filename + ".txt");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                messageSender.sendMessageClientSide(ChatFormatting.RED + "SpammerFile takes 1 argument." + ChatFormatting.RESET +  " Correct usage is " + Meowhack.PREFIX + "Spammerfile " + getUseCase());
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
