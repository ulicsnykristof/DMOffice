package Main;

import java.io.*;

public class Version {

    public static String get(){

        String ver = null;

        try {
            InputStream is = Version.class.getResourceAsStream("../Other/config.txt");
            Reader r = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            int l = 0;
            while((line =((BufferedReader) r).readLine()) != null){
                l++;
                if(l == 9){ ver = line; }
            }
            r.close();
        }catch(IOException ex){
            System.err.println("Config file is missing");
        }

        return ver;
    }

}
