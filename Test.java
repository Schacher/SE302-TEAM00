/**
 * Created by Mehmet on 18-Dec-18.
 */
import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    public static void main (String [] args){

        Scanner input = new Scanner(System.in);

        ArrayList<String> tempFieldsList = new ArrayList<String>();

        System.out.println("**Create a MediaType** ");

        System.out.println("Enter Name of Media Type: ");
        String tempName = input.nextLine();

        System.out.println("Enter number of fields: ");
        int count = input.nextInt();
        input.nextLine();

        for (int i=0; i<count; i++){
            System.out.println("Enter field name: ");
            String tempFieldName = input.nextLine();

            tempFieldsList.add(tempFieldName);
        }

        MediaType mediaType = new MediaType(tempName,tempFieldsList);
        mediaType.print();

        System.out.println();

        System.out.println("**Create new Item**");

        System.out.println("Enter Item name: ");
        String tempItemName = input.nextLine();

        System.out.println("Enter Item ID: ");
        int tempID = input.nextInt();
        input.nextLine();


        Item item = new Item(tempItemName, tempID, mediaType);
        item.print();








        }

}
