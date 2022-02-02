import java.io.File;

//----------------------------------------------------------------------
// Written by: Seyon Sounthararajah (40005646) & Nicolas Ciampa (40191585)
//-------------------------------------------------------------------------
//

/**
 * class CSVDataMissing
 *
 */
public class CSVDataMissing extends Exception{
    /**
     * default constrcutor
     */
    public CSVDataMissing() {
        super("Error: Input row cannot be parsed due to missing information");
        File folder = new File("//Users/seyonsounthararajah/Desktop/Desktop - Seyon’s MacBook Pro/Coding/Assignment_3_ COMP 249");
        File fList[] = folder.listFiles();

        for (int i = 0; i <fList.length; i++) {
            File pes = fList[i];
        }
        for (File f : fList) {
            if (f.getName().endsWith(".tex")) {
                f.delete();
            }
        }
    }


    /**
     * Parametrized constructor
     * @param message
     */
    public CSVDataMissing(String message) {
        super(message);
        File folder = new File("//Users/seyonsounthararajah/Desktop/Desktop - Seyon’s MacBook Pro/Coding/Assignment_3_ COMP 249");
        File fList[] = folder.listFiles();

        for (int i = 0; i <fList.length; i++) {
            File pes = fList[i];
        }
        for (File f : fList) {
            if (f.getName().endsWith(".tex")) {
                f.delete();
            }
        }
    }
}
