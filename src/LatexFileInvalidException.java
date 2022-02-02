//----------------------------------------------------------------------
// Written by: Seyon Sounthararajah (40005646) & Nicolas Ciampa (40191585)
//-------------------------------------------------------------------------
//
public class LatexFileInvalidException extends Exception{

    //The message is to be stored in the thrown object
    /**
     * default constrcutor
     */
    public LatexFileInvalidException() {
        super("Error: Input row cannot be parsed due to missing information");
    }

    //The passing of any error if desired
    // This will actually be use throughout the assignment
    /**
     * Parametrized constructor
     * @param message
     */
    public LatexFileInvalidException(String message) {
        super(message);
    }

}
