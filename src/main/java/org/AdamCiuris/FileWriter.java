package org.AdamCiuris;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.*;
import java.time.LocalDateTime;

public class FileWriter {
    File file;
    Dotenv dotenv = Dotenv.load();
    String filename;

    FileOutputStream fos;
    OutputStreamWriter osw;
    BufferedWriter output;
    public FileWriter() throws IOException {
        filename = (LocalDateTime.now() + dotenv.get("account_to_scrape") + "BY" + dotenv.get("gmail_login"))
                .replace(':', '.'); // windows can't have : in filenames
        file = new File("output\\"+filename +".txt");
        int count = 0;
        System.out.println(filename);
        // while loop edge case handling duplicate file names
        while (!file.createNewFile()) {
            file = new File("output\\" + filename + count+ ".txt");
            count++;
        };
        fos = new FileOutputStream(file, true);
        osw = new OutputStreamWriter(fos, "UTF-8");
        output = new BufferedWriter(osw);
    }
    public void write(String toWrite) throws IOException {

        output.write(toWrite);
        output.write('\n');
        output.flush();
    }
    public void close() throws IOException {
        fos.close();
        osw.close();
        output.close();
    }
}
