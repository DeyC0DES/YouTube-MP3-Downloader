import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String videoUrl = "";
        String outputDir = "./sounds";

        File file = new File(outputDir);

        if (!file.exists()) {
            file.mkdirs();
        }

        while (true) {
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.println();
            System.out.print(" Video URL: ");
            videoUrl = scanner.next();
            System.out.println();
            System.out.println();
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.println();

            if (!videoUrl.isEmpty()) {
                videoUrl = videoUrl.strip();
                convert(videoUrl, outputDir);
            }

            System.out.println();
            System.out.print(" You want to convert another video or music [Y/N]");

            if (scanner.next().equalsIgnoreCase("N")) {
                break;
            }
        }
    }

    public static void convert(String url, String dir) {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "yt-dlp",
                    "-x",
                    "--audio-format", "mp3",
                    "--output", dir + "/%(title)s.%(ext)s%",
                    url
            );

            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();
            System.out.println("Download completed!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
