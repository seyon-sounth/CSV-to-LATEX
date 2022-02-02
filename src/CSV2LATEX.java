//----------------------------------------------------------------------
// Written by: Seyon Sounthararajah (40005646) & Nicolas Ciampa (40191585)
//-------------------------------------------------------------------------
//
import java.io.*;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * class CSV2LATEX
 * Java tool called CSV2LATEX that helps the scientists read and process CSV files and create the corresponding LaTeX tables
 */
public class CSV2LATEX {
    public static void main(String[] args) {
/**
 * Member Variables
 */
        int numberOfFiles;
        String[] fileNames;

        System.out.println("Welcome to CSV2LATEX");
        System.out.println("We help the scientists read and process CSV files and create the corresponding LaTeX tables");
        System.out.println("-------------------------------------------------------------------------------------------------------" + "\n");

        //User Input for Number of files
        Scanner keyboard = new Scanner(System.in);
        System.out.println("How many CSV files would you like to read and process?");
        String numberOfFilesInString = keyboard.nextLine();
        numberOfFiles = Integer.parseInt(numberOfFilesInString);
        fileNames = new String[numberOfFiles];


        //For Reading csv files
        Scanner[] csv = new Scanner[numberOfFiles];

        //For writing to Latex files
        PrintWriter[] tex = new PrintWriter[numberOfFiles];


        //Entering file Names
        for (int i = 0; i < numberOfFiles; i++) {
            System.out.println("Enter name of File " + (i + 1));
            fileNames[i] = keyboard.nextLine();
        }
        keyboard.close(); // Close user input


        //Opening File - Checking if
        for (int i = 0; i < numberOfFiles; i++) {
            //Check if files exist
            try {
                csv[i] = new Scanner(new FileInputStream(fileNames[i]));
            }
            //if one of the files do not exist
            catch (FileNotFoundException e) {
                System.out.println("Could not open file " + fileNames[i] + " for reading. " + "\nPlease check if file exists! " + "\nProgram will terminate after closing all opened files.");
                for (int j = i-1; j >= 0; j--) { // Close all opened files before exiting the program
                    csv[j].close();
                }
                System.exit(0);
            }
        }

        // Creating Latex Files
        for (int i = 0; i < numberOfFiles; i++) {
            // Generating Latex File Names
            String textFilesName = fileNames[i].substring(0,fileNames[i].length()-3) + "tex";

            try{
                tex[i] = new PrintWriter(new FileOutputStream(textFilesName));
            } catch (FileNotFoundException e) {
                //If cpit
                System.out.println("Could not open file " + textFilesName + "for writing. \n" +
                        "Program will terminate after closing all the files that were opened and deleting all the files were created");
                for (int j = i-1; j >=0 ; j--) {
                    tex[j].close(); //1. closing all the files that were opened
                    try {
                        Files.deleteIfExists(Path.of(fileNames[i].substring(0,fileNames[i].length()-3)+"tex")); // 2. deleting all the files were created
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    for (int k = numberOfFiles-1; k >=0; k--) {
                        csv[k].close();
                    }
                    System.exit(0); // 3. Terminating Program
                }
            }
        }

        //Processing Files for Validation
        for (int i = 0; i < numberOfFiles; i++) {
            PrintWriter logFile = null;

            try {
                 logFile = new PrintWriter(new FileOutputStream("error_log.txt",true));
            } catch (FileNotFoundException e) {
                System.out.println("Could not find log file");
                System.exit(0);
            }
            processFilesForValidation(csv[i],tex[i],fileNames[i],logFile);
            System.exit(0);
        }

    }

    /**
     *  The method should process each of these files to find out whether it is valid or not
     *     //Method should work on already opened files
     *
     *     If file is invalid,
     *       then the method must stop processing of this file,
     *
     *     If file is valid,
     *       then the method must create the corresponding LATEX files.
     */
    public static void processFilesForValidation (Scanner csv,PrintWriter tex, String fileName,PrintWriter log ){

        //Case 1: File is invalid due to missing Attirbute
        //Listing Attributes of File

        String errorMessage = "";
        String errorMessagelineNumber = "";
        String errorMessageline = "";
        String[] allAttributes;
        String numberOfColumnsLatex = "";
        String title = csv.nextLine();
        String attributes = csv.nextLine();
        int numberOfLinesOfData=0;
        int numberOfRowsOfData =0;
        int[] linesOfData;
        String[]allData;
        String[]allDataRow;







        try {
            // Trailing empty strings are therefore not included in the resulting array.
            //So you will have to use the two argument version String.split(String regex, int limit) with a negative value:
            allAttributes = attributes.split(",");

            //replace empty string with ***
            for (int i = 0; i < allAttributes.length; i++) {
                if (allAttributes[i].equals("")) {
                    allAttributes[i] = "***";
                }
            }
            for (int i = 0; i < allAttributes.length; i++) {
                if(i != allAttributes.length-1)
                    errorMessage += (allAttributes[i] + ", ");
                if (i == allAttributes.length-1){
                    errorMessage +=allAttributes[i];
                }
            }

            for (int i = 0; i < allAttributes.length; i++) {
                if (allAttributes[i].equals("***")) {
                    throw new LatexFileInvalidException(errorMessage);

                }
            }

            //number rows
            while(csv.hasNextLine()){
                numberOfRowsOfData++;
                csv.nextLine();

            }





            //Header pf table
            for (int i = 0; i < allAttributes.length; i++) {
                numberOfColumnsLatex += "|1";
            }

            tex.println("\\begin{table}[]\n\t\\begin{tabular}{"+numberOfColumnsLatex+"|}");
            tex.println("\t\t\\toprule");

            //Attributes of Table

            for (int i = 0; i<allAttributes.length-1; i++){
                if(i==0){
                    tex.print("\t\t"+allAttributes[i] + " &" );
                }else{
                    tex.print(allAttributes[i] + " & " );

                }
            }
            // Last Attribute
            tex.println(" "+ allAttributes[allAttributes.length-1] + " \\\\ \\midrule");

            //Data
            //Counting Number of Lines of Data
            Scanner scannerForNumberOfLines = new Scanner(new FileInputStream(fileName));
            String line1Title = scannerForNumberOfLines.nextLine();
            String line2Attributes = scannerForNumberOfLines.nextLine();
            while (scannerForNumberOfLines.hasNextLine()){
                scannerForNumberOfLines.nextLine();
                numberOfLinesOfData++;
            }
            scannerForNumberOfLines.close();


            allData = new String[numberOfLinesOfData];

            Scanner scannerForDataRecords = new Scanner(new FileInputStream(fileName));
            String line1Title1 = scannerForDataRecords.nextLine();
            String line2Attributes1 = scannerForDataRecords.nextLine();
            for (int i = 0; i < numberOfLinesOfData; i++) {
                allData[i]= scannerForDataRecords.nextLine();
            }
            scannerForDataRecords.close();

            //Check missing data
            for (int i = 0; i < numberOfLinesOfData; i++) {
                String[][] allDataSplit = new String[numberOfLinesOfData][allAttributes.length];
                allDataSplit[i] = allData[i].split(",");

                for (int j = 0; j < allAttributes.length; j++) {
                    if(allDataSplit[i][j].equals("")){
                        errorMessagelineNumber = " line ";
                        errorMessagelineNumber+= i+2;
                        allDataSplit[i][j]= "***";
                        for (int k = 0; k < allAttributes.length; k++) {
                            errorMessageline += allDataSplit[i][k] +", ";
                        }
                        errorMessageline+= "\n"+ "Missing: "+ errorMessagelineNumber;

                        Scanner scannerForDataRecordsMissing = new Scanner(new FileInputStream(fileName));


                        for (int l = 1; l <= numberOfLinesOfData; l++) {
                            String title1 = scannerForDataRecordsMissing.nextLine();
                            String attributes1 = scannerForDataRecordsMissing.nextLine();


                            String row="";
                            if (l < numberOfLinesOfData){
                                row = "\t\t" + scannerForDataRecordsMissing.nextLine().replace(",", " & ") + " \\\\ \\midrule";
                                tex.println(row);
                            }

                            if (l == numberOfLinesOfData) {
                                row = "\t\t" + scannerForDataRecordsMissing.nextLine().replace(",", " & ") + " \\\\ \\bottomrule";
                                tex.print(row);
                            }
                        }

                        //Last line of Table
                        tex.println("\n\t\\end{tabular}\n\t\\caption{"+title+"}\n\t\\label{"+fileName+"}\n\\end{table}");



                        throw new CSVDataMissing(errorMessagelineNumber);

                    }

                }

            }




            for (int i = 1; i <= numberOfLinesOfData; i++) {
                String row="";
                if (i < numberOfLinesOfData){
                    row = "\t\t" + csv.nextLine().replace(",", " & ") + " \\\\ \\midrule";
                    tex.println(row);
                }

                if (i == numberOfLinesOfData) {
                    row = "\t\t" + csv.nextLine().replace(",", " & ") + " \\\\ \\bottomrule";
                    tex.print(row);
                }
            }

            //Last line of Table
            tex.println("\n\t\\end{tabular}\n\t\\caption{"+title+"}\n\t\\label{"+fileName+"}\n\\end{table}");


        } catch (LatexFileInvalidException e) {
            log.println("File " + fileName + " is invalid: attribute is missing");
            log.println(errorMessage);
            log.println("File is not converted to LATEX.");

        }

        catch (CSVDataMissing e){
            System.out.println("In the file " + fileName + errorMessagelineNumber+ " not converted to Latex");
            log.println("File "+fileName+ errorMessagelineNumber + ".");
            log.println(errorMessageline);


        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        finally {
            log.close();
//            tex.close();
            System.exit(0);

        }
    }
}



