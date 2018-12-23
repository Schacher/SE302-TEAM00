/**
 * Created by Mehmet on 18-Dec-18.
 */
public class Item {

    private String name;
    private int itemID;
    private MediaType mediaType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Item(String name, int itemID, MediaType mediaType) {
        this.name = name;
        this.itemID = itemID;
        this.mediaType = mediaType;
    }

    public void print(){

        System.out.println("Item Name: "+getName());
        System.out.println("Item ID: "+getItemID());
        System.out.print("Media Type: "+mediaType.getName());

        System.out.println();

        System.out.println("Fields: ");

        for (int i=0; i<mediaType.getFieldsList().size(); i++){

            System.out.println(mediaType.getFieldsList().get(i));

        }
    }
}
