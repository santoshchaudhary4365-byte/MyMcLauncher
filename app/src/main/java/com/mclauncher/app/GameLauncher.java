package com.mclauncher.app;

import android.content.Context;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameLauncher {

    private Context context;
    private File gameDir;

    public GameLauncher(Context context) {
        this.context = context;
        this.gameDir = new File(context.getExternalFilesDir(null), "minecraft");
        if (!gameDir.exists()) {
            gameDir.mkdirs();
        }
    }

    public void launchGame(String username, String version, int ramAllocationMb) {
        if (username == null || username.trim().isEmpty()) {
            Toast.makeText(context, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            List<String> launchArgs = buildLaunchArguments(username, version, ramAllocationMb);
            Toast.makeText(context, "Starting Minecraft " + version + " for " + username, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Launch Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> buildLaunchArguments(String username, String version, int ramMb) {
        List<String> args = new ArrayList<>();
        args.add("-Xms512M");
        args.add("-Xmx" + ramMb + "M");
        args.add("-Djava.library.path=" + new File(gameDir, "natives").getAbsolutePath());
        args.add("-cp");
        args.add(new File(gameDir, "bin/*").getAbsolutePath());
        args.add("net.minecraft.client.main.Main");
        args.add("--username");
        args.add(username);
        args.add("--version");
        args.add(version);
        args.add("--gameDir");
        args.add(gameDir.getAbsolutePath());
        args.add("--assetsDir");
        args.add(new File(gameDir, "assets").getAbsolutePath());

        return args;
    }

    public String getGameDirectoryPath() {
        return gameDir.getAbsolutePath();
    }
}
