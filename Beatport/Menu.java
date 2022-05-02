import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public final class Menu{
    /**
    Class, dedicated to menu methods
    */

    private Menu(){}

    //Asking the user to input his/her username
    public static String inputUsername(Scanner in){
        String username;

        while(true) {
            Utility.printLoginText();
            username = in.nextLine();

            /*if(username.contains("\"")) {
                System.err.println(Utility.ERR_SYMBOL_NOT_ALLOWED);
                continue;
            }*/

            break;
        }

        return username;
    }

    //Asking the user to input a yes/no answer
    public static String inputYesNo(Scanner in, String inputText){
        String answer;

        while(true) {
            System.out.print(inputText);
            answer = in.nextLine().toLowerCase();

            if(answer.equals("yes") || answer.equals("no") || answer.equals(Utility.CANCEL_STR)) 
                break;
            
            System.err.println(Utility.ERR_WRONG_INPUT);
        }

        return answer;
    }

    //Asking the user to input the command, in addition - splitting and trimming each argument
    public static Object[] inputArguments(Scanner in, boolean selectionRequired) throws NumberFormatException{      
        String line = in.nextLine();

        if(line.toLowerCase().equals(Utility.CANCEL_STR) && !selectionRequired)
            return null;

        String[] arguments = line.split(",");
        ArrayList<String> trimmedArgs = (ArrayList<String>) Arrays.stream(arguments).map(String::trim).collect(Collectors.toList());

        String command = trimmedArgs.get(0);
        int selection;

        if(selectionRequired){
            try {
                selection = Integer.parseInt(command);
            }
            catch(NumberFormatException e){
                throw e;
            }

            return new Object[]{trimmedArgs, selection};
        }
        
        return new Object[]{trimmedArgs};
    }
}