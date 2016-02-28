package hu.laci200270.games.sbs3djumper.utils;

import java.io.*;
import java.net.URL;

/**
 * Created by Laci on 2016. 01. 23..
 */
public class WebUtils {

    public static void downloadFile(String url, File where) throws IOException {
        if (isFileUpdated(new URL(url), where)) {
            URL link = new URL(url);

            //Code to download
            InputStream in = new BufferedInputStream(link.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();

            FileOutputStream fos = new FileOutputStream(where);
            fos.write(response);
            fos.close();
            where.setLastModified(link.openConnection().getLastModified());
        }
    }

    public static boolean isFileUpdated(URL original, File local) throws IOException {
        if (!local.exists())
            return true;
        System.out.println(String.format("Last modified on %s (local and the upstream %s", local.lastModified(), original.openConnection().getLastModified()));
        return local.lastModified() != original.openConnection().getLastModified();
    }
}
