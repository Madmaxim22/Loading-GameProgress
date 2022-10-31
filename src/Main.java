import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {

        String pathFile = "/home/maksim/Games/savegames/save.zip";
        String pathDir = "/home/maksim/Games/savegames/";
        String pathFile1 = "/home/maksim/Games/savegames/Save1.dat";
        String pathFile2 = "/home/maksim/Games/savegames/Save2.dat";
        String pathFile3 = "/home/maksim/Games/savegames/Save3.dat";
        openZip(pathFile, pathDir);
        GameProgress gameProgress1 = openProgress(pathFile1);
        GameProgress gameProgress2 = openProgress(pathFile2);
        GameProgress gameProgress3 = openProgress(pathFile3);
    }

    public static void openZip(String pathFile, String pathDir) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathFile))) {
            ZipEntry entry;
            String name;
            long size;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                size = entry.getSize();
                System.out.printf("File name: %s \t File size: %d \n", name, size);

                FileOutputStream fout = new FileOutputStream(pathDir + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }

                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String pathFile) {
        GameProgress progress = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pathFile))) {
            progress = (GameProgress) ois.readObject();
            System.out.println(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return progress;
    }
}
