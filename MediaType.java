/**
 * Created by Mehmet on 18-Dec-18.
 */
import java.util.ArrayList;
import java.util.Scanner;

public class MediaType {

    private String name;
    private ArrayList <String> fieldsList = new ArrayList<String>();

    Scanner input = new Scanner(System.in);

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public ArrayList<String> getFieldsList() {
        return fieldsList;
    }

    public void setFieldsList(ArrayList<String> fieldsList) {

        this.fieldsList = fieldsList;
    }

    public MediaType(String name, ArrayList<String> fieldsList) {
        this.name = name;
        this.fieldsList = fieldsList;
    }

    public MediaType() {
        name=null;
        fieldsList=null;
    }

    public void print(){

        System.out.println("Media type: "+getName());

        System.out.println("Fields: ");
        for (int i=0; i<getFieldsList().size(); i++){

            System.out.println(getFieldsList().get(i));

        }
    }

    public void delete(){

        name=null;

        for (int i=0; i<getFieldsList().size(); i++){

            getFieldsList().remove(i);

        }

        System.gc();
    }

}
