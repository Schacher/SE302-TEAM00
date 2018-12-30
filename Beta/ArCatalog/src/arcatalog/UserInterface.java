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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils; 

public class UserInterface extends JFrame {

    ButtonFactory AddFactory = new AddFactory();
    ButtonFactory EditFactory = new EditFactory();
    ButtonFactory DeleteFactory = new DeleteFactory();
    JPanel Panel;
    JButton Search;
    JButton Refresh;
    JButton View;
    JButton Edit;
    JButton Delete;
    JButton Collection;
    JTextField InputSearch;
    DefaultListModel<String> Model;
    JScrollPane ScrollPane;
    JList<String> List;
    DatabaseConnection DbConnect;

    public UserInterface() throws SQLException {
        CreateMenu();
    }

    public void CreateMenu() throws SQLException {
        DbConnect = new DatabaseConnection();
        setTitle("arcatalog");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);

        //Create Panel    
        Panel = new JPanel();
        Panel.setLayout(null);
        Search = new Search();
        Search.addActionListener(new ButtonListener(Search));
        Panel.setOpaque(false);

        //Set Background
        getContentPane().setBackground(new java.awt.Color(101, 150, 166));

        //Refresh Button
        Refresh = new Refresh();
        Refresh.addActionListener(new ButtonListener(Refresh));

        // View Button
        View = new View();
        View.addActionListener(new ButtonListener(View));

        //Edit Collection Button From ButtonFactory
        Edit = EditFactory.CreateButton("Collection");
        Edit.addActionListener(new ButtonListener(Edit));

        // Delete Collection Button From ButtonFactory
        Delete = DeleteFactory.CreateButton("Collection");
        Delete.addActionListener(new ButtonListener(Delete));

        //Add New Colection Button From ButtonFactory
        Collection = AddFactory.CreateButton("Collection");
        Collection.addActionListener(new ButtonListener(Collection));

        //Seacrh Field
        InputSearch = new JTextField();
        InputSearch.setBounds(450, 10,280, 30);

        JLabel StrCollections = new JLabel("Collections");
        StrCollections.setBounds(30, 10, 130, 30);
        StrCollections.setFont(new Font("Arial Black", Font.PLAIN, 20));

        //Scroll Pane List
        Model = new DefaultListModel<>();
        ScrollPane = new JScrollPane();
        List = new JList<>(Model);
        RefreshList();
        List.setSelectedIndex(0);
        ScrollPane.setViewportView(List);
        ScrollPane.setBounds(50, 50, 600, 350);

        // Add Elements In To Panel
        Panel.add(StrCollections);
        Panel.add(Search);
        Panel.add(Refresh);
        Panel.add(InputSearch);
        Panel.add(ScrollPane);
        Panel.add(View);
        Panel.add(Edit);
        Panel.add(Delete);
        Panel.add(Collection);
        getContentPane().add(Panel);

        //Create That Frame Middle Of The Window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dim.width / 2) - (this.getSize().width / 2), (dim.height / 2) - (this.getSize().height / 2));

        setVisible(true);

    }

    public class ButtonListener implements ActionListener {

        JButton Button;

        public ButtonListener(JButton bSource) {
            Button = bSource;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            JFrame NewFrame = new JFrame();
            System.out.println(Button.getText());

            if (Button.getText().equals("Refresh")) {
                try {
                    RefreshList();
                    System.out.println("Refresh Button Pressed");
                } catch (SQLException ex) {
                    Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            if (Button.getText().equals("Add New Collection")) {

                NewFrame.setSize(500, 200);
                JTextField NameField = new JTextField(5);
                JTextField NumberField = new JTextField(5);
                NumberField.setBounds(160, 40, 10, 20);
                NameField.setBounds(160, 60, 10, 20);

                JButton Continue = new JButton("Continue");
                Continue.setBounds(260, 180, 30, 10);
                Continue.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (NameField.getText().isEmpty() || NumberField.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please Fill All Fields.",
                                    "Error", JOptionPane.WARNING_MESSAGE);
                        } else {
                            NewFrame.dispose();
                            int LabelNumber = Integer.parseInt(NumberField.getText());
                            ArrayList<String> Arr = new ArrayList<>();
                            JTextField[] Labels = new JTextField[LabelNumber];
                            JFrame NewFrame = new JFrame();
                            NewFrame.setSize(200 * LabelNumber, 200);
                            JPanel NewPanel = new JPanel();
                            NewPanel.add(new JLabel("Fill All Labels:"));
                            for (int i = 0; i < LabelNumber; i++) {
                                Labels[i] = new JTextField(5);
                                NewPanel.add(Labels[i]);
                            }
                            JButton Okay = new JButton("Create");
                            Okay.setBounds(50, 70, 10, 10);
                            Okay.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    boolean Check = true;
                                    for (int i = 0; i < LabelNumber; i++) {
                                        if (Labels[i].getText().isEmpty()) {
                                            Check = false;
                                            Arr.clear();
                                            JOptionPane.showMessageDialog(null, "Please Fill All.",
                                                    "Error", JOptionPane.WARNING_MESSAGE);
                                        } else {
                                            Arr.add((String) Labels[i].getText());
                                        }
                                        System.out.println(Labels[i].getText());
                                    }
                                    if (Check) {
                                        try {
                                            DbConnect.AddNewCollection(NameField.getText(), Arr);
                                            Model.addElement(NameField.getText());
                                            NewFrame.dispose();
                                            JOptionPane.showConfirmDialog(null, "Created",
                                                    "Created", JOptionPane.DEFAULT_OPTION);
                                        } catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                    }

                                }

                            });

                            JButton CancelAdd = new JButton("Cancel");
                            CancelAdd.setBounds(310, 180, 10, 10);
                            CancelAdd.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    NewFrame.dispose();
                                }
                            });

                            NewPanel.add(Okay);
                            NewPanel.add(CancelAdd);
                            NewFrame.add(NewPanel);
                            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                            NewFrame.setLocation((dim.width / 2) - (NewFrame.getSize().width / 2), (dim.height / 2) - (NewFrame.getSize().height / 2));
                            NewFrame.setVisible(true);

                        }
                    }

                });

                JButton Cancel = new JButton("Cancel");
                Cancel.setBounds(310, 180, 30, 10);
                Cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        NewFrame.dispose();
                    }
                });
                JPanel NewPanel = new JPanel();
                NewPanel.add(new JLabel("Enter The Name Of Collection:"));
                NewPanel.add(NameField);
                NewPanel.add(new JLabel("Enter The Number Of Labels:"));
                NewPanel.add(NumberField);
                NewPanel.add(Continue);
                NewPanel.add(Cancel);
                NewFrame.add(NewPanel);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                NewFrame.setLocation((dim.width / 2) - (NewFrame.getSize().width / 2), (dim.height / 2) - (NewFrame.getSize().height / 2));
                NewFrame.setVisible(true);

            } else if (Button.getText().equals("Delete Collection")) {
                if (List.getSelectedIndex() != -1) {
                    int clickedIndex = List.getSelectedIndex();
                    String clickedCollection = List.getSelectedValue();
                    try {
                        DbConnect.DeleteCollection(clickedCollection);
                        Model.remove(clickedIndex);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select One.",
                            "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else if (Button.getText().equals("Edit The Collection Name")) {
                if (List.getSelectedIndex() != -1) {
                    String ClickedName = List.getSelectedValue();
                    int ClickedIndex = List.getSelectedIndex();
                    JFrame EditFrame = new JFrame();
                    EditFrame.setSize(400, 100);
                    JTextField EditInput = new JTextField(8);
                    JPanel NewPanel = new JPanel();
                    JLabel EditLabel = new JLabel("New Name:");
                    JButton ButtonEdit = new JButton("Edit");
                    ButtonEdit.setBounds(30, 100, 10, 10);
                    ButtonEdit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (!EditInput.getText().equals("")) {
                                try {
                                    DbConnect.EditCollectionName(ClickedName, EditInput.getText());
                                    Model.setElementAt(EditInput.getText(), ClickedIndex);
                                    EditFrame.dispose();
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Enter New Name.",
                                        "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    });
                    JButton Cancel = new JButton("Cancel");
                    ButtonEdit.setBounds(45, 100, 10, 10);
                    Cancel.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            EditFrame.dispose();
                        }
                    });

                    NewPanel.add(EditLabel);
                    NewPanel.add(EditInput);
                    NewPanel.add(ButtonEdit);
                    NewPanel.add(Cancel);
                    EditFrame.add(NewPanel);
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                    EditFrame.setLocation((dim.width / 2) - (EditFrame.getSize().width / 2), (dim.height / 2) - (EditFrame.getSize().height / 2));
                    EditFrame.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Select One Collection.",
                            "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else if (Button.getText().equals("Search")) {
                String SearchInput = InputSearch.getText();
                if (SearchInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Parameter For Search", null, JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        ResultSet CollectionResultSet = DbConnect.WholeCollections();
                        ArrayList<String> CollectionArray = FillCollections(CollectionResultSet);
                        Model.clear();
                        for (String Collection : CollectionArray) {
                            if (Collection.toLowerCase().contains(SearchInput.toLowerCase())) {
                                Model.addElement(Collection);

                            }
                        }
                        if (Model.size() == 0) {
                            JOptionPane.showMessageDialog(null, "No Match", "null", JOptionPane.INFORMATION_MESSAGE);
                            RefreshList();
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }

            } else if (Button.getText().equals("View Collection")) {
                String Selected = List.getSelectedValue();
                if(Selected!=null){
                JFrame ViewFrame = new JFrame();
                ViewFrame.setTitle(Selected);
                JPanel ViewPanel = new JPanel();
                ViewFrame.setSize(500, 500);
                ViewPanel.setLayout(new BorderLayout());
                ResultSet Rs = DbConnect.SelectCollection(Selected);
                JTable ViewTable = new JTable();
                ViewTable.setModel(DbUtils.resultSetToTableModel(Rs));

                JButton AddRow = new JButton("Add Row");
                AddRow.setBounds(100, 420, 100, 25);
                AddRow.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        try {
                            ArrayList<JTextField> Fields = new ArrayList<JTextField>();
                            int LabelNumber = DbConnect.CollectionColumns(Selected).size();
                            ArrayList<String> Arr = DbConnect.CollectionColumns(Selected);

                            JFrame NewFrame = new JFrame();
                            JPanel NewPanel = new JPanel();

                            for (int i = 0; i < LabelNumber; i++) {
                                JLabel NewLabel = new JLabel(Arr.get(i));
                                if (!NewLabel.getText().equals("id")) {
                                    NewPanel.add(NewLabel);
                                    JTextField TextField = new JTextField(6);
                                    NewPanel.add(TextField);
                                    Fields.add(TextField);
                                }

                            }
                            JButton AddNew = new JButton("Add New");
                            AddNew.setBounds(70, 220, 30, 20);
                            NewPanel.add(AddNew);
                            NewFrame.setSize(150 * LabelNumber, 150);
                            AddNew.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println("Added Row");
                                    boolean Check = true;
                                    ArrayList<String> Row = new ArrayList<String>();
                                    for (JTextField Field : Fields) {
                                        System.out.println(Field.getText());
                                        if (Field.getText().isEmpty()) {
                                            Check = false;
                                            JOptionPane.showMessageDialog(null, "Fill All", "null", JOptionPane.INFORMATION_MESSAGE);
                                            Row.clear();
                                        } else {
                                            Row.add(Field.getText());
                                        }

                                    }
                                    if (Check) {
                                        try {
                                            DbConnect.AddNewRowCollection(Selected, Row);                                        
                                            NewFrame.dispose();
                                            ResultSet Rs = DbConnect.SelectCollection(Selected);
                                            ViewTable.setModel(DbUtils.resultSetToTableModel(Rs));
                                            ViewFrame.revalidate();
                                            ViewFrame.repaint();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                            });
                            NewFrame.add(NewPanel);
                            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                NewFrame.setLocation((dim.width / 2) - (NewFrame.getSize().width / 2), (dim.height / 2) - (NewFrame.getSize().height / 2));
                            NewFrame.setVisible(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                JButton DeleteRow = new JButton("Delete Row");
                DeleteRow.setBounds(200, 420, 130, 25);
                DeleteRow.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    if(ViewTable.getSelectedRow()!=-1){
                    String Id = String.valueOf(ViewTable.getModel().getValueAt(ViewTable.getSelectedRow(), 0 ));
                    DbConnect.DeleteRowCollection(Selected ,Integer.parseInt(Id));
                        ((DefaultTableModel)ViewTable.getModel()).removeRow(ViewTable.getSelectedRow());
                    }
                    else{ JOptionPane.showMessageDialog(null, "Select One Row", "null", JOptionPane.INFORMATION_MESSAGE);}
                    }
                    
                });

                JPanel AlterPanel = new JPanel();
                AlterPanel.add(AddRow);
                AlterPanel.add(DeleteRow);
                ViewPanel.add(ViewTable.getTableHeader(), BorderLayout.PAGE_START);
                ViewPanel.add(ViewTable, BorderLayout.CENTER);
                ViewFrame.add(AlterPanel, BorderLayout.SOUTH);
                ViewFrame.add(ViewPanel);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                ViewFrame.setLocation((dim.width / 2) - (ViewFrame.getSize().width / 2), (dim.height / 2) - (ViewFrame.getSize().height / 2));
                ViewFrame.setVisible(true);

            }
}
        }

    }

    public void RefreshList() throws SQLException {
        Model.clear();
        ResultSet ResultSet = DbConnect.WholeCollections();
        for (String Collection : FillCollections(ResultSet)) {
            if (!Collection.contains("sqlite")) {
                Model.addElement(Collection);
            }

        }
    }

    public ArrayList<String> FillCollections(ResultSet Result) throws SQLException {
        ArrayList<String> Collections = new ArrayList<>();
        while (Result.next()) {
            String Str = Result.getString("name");
            Collections.add(Str);
        }
        return (Collections);
    }

}
