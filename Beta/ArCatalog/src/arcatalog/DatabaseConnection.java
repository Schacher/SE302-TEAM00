/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcatalog;

/**
 *
 * @author can
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseConnection {
  private Connection Connection;

    public DatabaseConnection() {
        ConnectToDatabase();
    }
  
  
  //Connect The Database
    public void ConnectToDatabase(){
        String connectionUrl = "jdbc:sqlite:Collection.db";
        try {
            Connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
  
  
    }
    
    // Add New Table
       public void AddNewCollection(String Table, ArrayList<String> Columns) throws SQLException {
        String CreateCollection = "CREATE TABLE IF NOT EXISTS " + Table + " (id integer primary key autoincrement);";

        Statement createCollectionStt = Connection.createStatement();
        createCollectionStt.execute(CreateCollection);
        int i = 0;
        while(i < Columns.size()){
            String AddNewColumn = "ALTER TABLE "+Table+" ADD COLUMN "+Columns.get(i)+" text";
            PreparedStatement ps = Connection.prepareStatement(AddNewColumn);
            ps.executeUpdate();
            i++;
        }
    }
       // Delete Collection/Table
          public void DeleteCollection(String tableName) throws SQLException {

        String DeleteTableQuery = "DROP TABLE IF EXISTS " + tableName;

        Statement Delete = Connection.createStatement();

        Delete.executeUpdate(DeleteTableQuery);

    }
          //Edit Collection/Table Name
          public void EditCollectionName(String CurrentName , String NewName) throws SQLException {
        String EditQuery = "ALTER TABLE " + CurrentName+ " RENAME TO "+ NewName;

        Statement Edit = Connection.createStatement();

        Edit.executeUpdate(EditQuery);

    }
          //Return All Collections/Tables In Database
            public ResultSet WholeCollections() throws SQLException {
        String CollectionsQuery = "SELECT name FROM sqlite_master WHERE type='table'";

        Statement WholeTablesStatement = Connection.createStatement();

        ResultSet Collections = WholeTablesStatement.executeQuery(CollectionsQuery);

        return Collections;
    }
         //Return Row Count For Given Collection/Table
        public int CollectionRows(String Collection) throws SQLException {
        String TableRowQuery = "SELECT count(*) FROM "+ Collection;
        Statement TableRowCount= Connection.createStatement();
        ResultSet Set = TableRowCount.executeQuery(TableRowQuery);
        System.out.println("Row Count "+Set.getInt(1));
        return Set.getInt(1);
    }
   
         //Add New Row In Given Collection/Table
               public void AddNewRowCollection(String Collection,ArrayList<String> Values)throws SQLException{
                   String StrValues="";
                   for(String El : Values){
                       StrValues+="'"+El+"'"+",";
                       System.out.println("Will Add "+El);
                   }
                  ArrayList<String> Names = CollectionColumns(Collection);
                  Names.remove(0);
                  String StrNames="";
                  for(String El : Names){
                      StrNames+=El+",";
                      System.out.println("Collumn Name "+El);
                  }
                    System.out.println("Str Names " +StrNames);
                    System.out.println("StrValues "+ StrValues);
                    
                    StrNames=StrNames.replaceFirst(".$","");
                    StrValues = StrValues.replaceFirst(".$","");
                    System.out.println("Str Names " +StrNames);
                    System.out.println("StrValues "+ StrValues);
                    String AddNewRow = "INSERT INTO " + Collection + "("+StrNames+")VALUES("+StrValues+")";
                    System.out.println("Query "+AddNewRow);
                    Statement AddRowStatement=Connection.createStatement();
                    AddRowStatement.executeUpdate(AddNewRow);
                   
                   
               }
               
         //Return Columns In Given Collection/Table
        public ArrayList<String> CollectionColumns(String Collection) throws SQLException {
        String getTableSql = "SELECT * FROM "+Collection+" LIMIT 0";
        ArrayList<String> Columns = new ArrayList<>();
        Columns.clear();
        Statement ColumnStatement = Connection.createStatement();
        ResultSet Set = ColumnStatement.executeQuery(getTableSql);
        ResultSetMetaData SetMeta = Set.getMetaData();
        int i = 1;
        while (i <= SetMeta.getColumnCount()){
            Columns.add(SetMeta.getColumnName(i));
            System.out.println("Column "+SetMeta.getColumnName(i));

            i++;
        }
        return Columns;
    }
        //Get The Collection/Table Information For Insert To JTable
            public ResultSet SelectCollection(String Collection){
        ResultSet  SelectedSet=null;
        String Select = "SELECT * FROM "+ Collection;

        try {
            Statement SelectStatement = Connection.createStatement();

           SelectedSet = SelectStatement.executeQuery(Select);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return SelectedSet;
    }
            
            //Delete Selected Row From Collection/Table
             public void DeleteRowCollection(String Collection, int Id) {

        String DeleteRow = "DELETE FROM " + Collection + " WHERE id = " + Id;

        try {

            PreparedStatement Delete = Connection.prepareStatement(DeleteRow);
            Delete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

