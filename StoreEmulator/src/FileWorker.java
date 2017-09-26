import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by AdanaC on 23.09.2017.
 */
public class FileWorker {

    public void createTXTFile(List<String> lines) {
        LocalDate today = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();


        try (FileWriter writer = new FileWriter(".\\src\\resources\\raport.txt", true)) {
            writer.write("\n");
            writer.write("\n");
            writer.write("\n");
            writer.write("    " + localDateTime + "\n");
            writer.write("\n");
            for (String s : lines) {

                writer.write(s + "\n");
            }

            writer.write("\n");
            writer.append('\n');


            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createTXTFile(String string) {
        try (FileWriter writer = new FileWriter(".\\src\\resources\\raport.txt", true)) {

            writer.write(string);

            writer.append('\n');


            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
