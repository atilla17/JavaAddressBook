import com.sun.deploy.util.StringUtils;
import com.sun.javafx.binding.StringFormatter;
import javafx.application.Application;
import  javafx.collections.FXCollections;
import  javafx.event.ActionEvent;
import javafx.event.EventHandler;
import  javafx.geometry.Insets;
import  javafx.geometry.Insets;
import  javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import  javafx.stage.Stage;

import javax.tools.FileObject;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import  java.io.File;



public class task1 extends  Application{

    //Working Data
    String name_;
    String street_;
    String city_;
    String state_;
    String Zip_;

    byte[] nameData;
    byte[] streetData;
    byte[] cityData;
    byte[] stateData;
    byte[] zipData;


    ArrayList<String> fileItems;

    int chunckSize = 91;
    int readPos = -1;

    //presistantData
    RandomAccessFile datafile_;
    String fileName = "Address.dat";

    //GUI DATA
    Label lblName;
    Label lblStreet;
    Label lblCity;
    Label lblState;
    Label lblZip;


    TextField txtName;
    TextField txtStreet;
    TextField txtCity;
    TextField txtState;
    TextField txtzip;



    private  void UpdateFile(int pos_) throws  IOException
    {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        PullDataFromFields();
        raf.seek(pos_ * 91);
        raf.write(nameData);
        raf.write(streetData);
        raf.write(cityData);
        raf.write(stateData);
        raf.write(zipData);
        raf.close();

        //this will refresh the text field
        ReadAddress( pos_);

    }

    private void ReadAddress(int pos_) throws IOException
    {
        System.out.println("Reading file " + pos_);
        int pos = 91 * pos_;
        String fileString = name_ + '|' + street_ + '|' + city_ + "|" + state_ + "|" + Zip_;
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        raf.seek(pos);

        byte[] b = new byte[32];

        raf.read(b);
        txtName.setText(new String(b).trim());

        raf.read(b);
        txtStreet.setText(new String(b).trim());

        b = new byte[20];

        raf.read(b);
        txtCity.setText(new String(b).trim());

        b = new byte[2];
        raf.read(b);
        txtState.setText(new String(b).trim());
        b = new byte[5];
        raf.read(b);
        txtzip.setText(new String(b).trim());

        readPos = pos/91;

    }


    private boolean CheckFields()
    {
        boolean flag = true;
        ArrayList<String> emptyFields = new ArrayList<String>();
        if(txtName.getText() == null || txtName.getText().length() <= 0)
        {
           flag = false;
           emptyFields.add("Name");
        }
        if(txtStreet.getText() == null || txtStreet.getText().length() <= 0)
        {
            flag = false;
            emptyFields.add("Street");
        }
        if(txtCity.getText() == null || txtCity.getText().length() <= 0)
        {
            flag = false;
            emptyFields.add("City");
        }
        if(txtState.getText() == null || txtState.getText().length() <= 0)
        {
            flag = false;
            emptyFields.add("State");
        }
        if(txtzip.getText() == null || txtzip.getText().length() <= 0)
        {
            flag = false;
            emptyFields.add("Zip");
        }

        if(flag == false)
        {

            String alertContent = "The following field(s) have been left empty \n";

            for(int i = 0; i<emptyFields.size(); i++)
            {
                alertContent += emptyFields.get(i) + "\n";
            }
            alertContent += "Please fill out all fields";

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing info");
            alert.setHeaderText(null);
            alert.setContentText(alertContent);
            alert.showAndWait();
        }

        return flag;
    }


    private boolean PullDataFromFields()
    {
        if(CheckFields())
        {
            name_ = txtName.getText();
            name_ = String.format("%-32.32s", name_);
            nameData = name_.getBytes();

            street_ = txtStreet.getText();
            street_ = String.format("%-32.32s", street_);
            streetData = street_.getBytes();

            city_ = txtCity.getText();
            city_ = String.format("%-20.20s", city_);
            cityData = city_.getBytes();

            state_ = txtState.getText();
            state_ = String.format("%-2.2s", state_);
            stateData = state_.getBytes();

            Zip_ = txtzip.getText();
            Zip_ = String.format("%-5.5s", Zip_);
            zipData = Zip_.getBytes();
            return true;
        }
        else
        {
            return false;
        }
    }

    private void AddFunction() throws IOException
    {
        File fileObject = new File(fileName);
        if(PullDataFromFields())
        {
            if(!fileObject.exists())
            {
                System.out.println("creating file");
                RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
                raf.write(nameData);
                raf.write(streetData);
                raf.write(cityData);
                raf.write(stateData);
                raf.write(zipData);
                raf.close();
            }
            else
            {
                System.out.println("adding to file");
                String fileString = name_ + '|' + street_ + '|' + city_ + "|" + state_ + "|" + Zip_;
                RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
                raf.seek(fileObject.length());
                raf.write(nameData);
                raf.write(streetData);
                raf.write(cityData);
                raf.write(stateData);
                raf.write(zipData);
                raf.close();

            }
        }
    }

    public void beginTask1(String[] args)
    {
        launch(args);

    }

    @Override
    public  void start(Stage primaryStage) throws  Exception
    {

        primaryStage.setTitle("Address Book");


        lblName = new Label();
        lblStreet = new Label();
        lblCity = new Label();
        lblState = new Label();
        lblZip = new Label();


        txtName = new TextField();
        txtStreet = new TextField();
        txtCity = new TextField();
        txtState = new TextField();
        txtzip = new TextField();


        lblName.setText("Name");
        lblStreet.setText("Street");
        lblCity.setText("City");
        lblState.setText("State");
        lblZip.setText("zip");

        //Name Positioning
        lblName.setLayoutX(25);
        lblName.setLayoutY(25);
        txtName.setLayoutX(lblName.getLayoutX()  + 50);
        txtName.setLayoutY(lblName.getLayoutY() - 5);
        txtName.setPrefWidth(430);

        //Street Positioning
        lblStreet.setLayoutX(25);
        lblStreet.setLayoutY(60);
        txtStreet.setLayoutX(lblStreet.getLayoutX() + 50);
        txtStreet.setLayoutY(lblStreet.getLayoutY() - 5);
        txtStreet.setPrefWidth(430);

        //city positioning
        lblCity.setLayoutY(95);
        lblCity.setLayoutX(25);
        txtCity.setLayoutY(lblCity.getLayoutY() - 5);
        txtCity.setLayoutX(lblCity.getLayoutX() + 50);

        //state positioning
        lblState.setLayoutX(txtCity.getLayoutX() +  190);
        lblState.setLayoutY(lblCity.getLayoutY());
        txtState.setLayoutX(lblState.getLayoutX() + 50);
        txtState.setLayoutY(txtCity.getLayoutY());
        txtState.setPrefWidth(60);

        //Zip positioning
        lblZip.setLayoutX(txtState.getLayoutX() + 70);
        lblZip.setLayoutY(lblState.getLayoutY());
        txtzip.setLayoutX(lblZip.getLayoutX() + 30);
        txtzip.setLayoutY(lblZip.getLayoutY() - 5);
        txtzip.setPrefWidth(80);


        //add button
        Button btnAdd = new Button("Add");
        btnAdd.setLayoutX(50);
        btnAdd.setLayoutY(135);

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("btn1 pressed");
                try {
                    AddFunction();
                }
                catch (IOException e)
                {
                    System.out.println(e);
                }
            }
        });


        //firstButton
        Button btnFirst = new Button("First");
        btnFirst.setLayoutX(btnAdd.getLayoutX() + 55);
        btnFirst.setLayoutY(btnAdd.getLayoutY());
        btnFirst.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    ReadAddress(0);
                }
                catch (IOException e)
                {
                    System.out.println(e);
                }
            }
        });

        //next button
        Button btnNext = new Button("Next");
        btnNext.setLayoutX(btnFirst.getLayoutX() + 55);
        btnNext.setLayoutY(btnFirst.getLayoutY());
        btnNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                readPos++;
                try{
                    System.out.println(readPos);

                        File f = new File(fileName);
                        System.out.println("file length is " + f.length()/91);

                        if(readPos > f.length() / 91 -1) {
                            readPos = ((int)f.length()/ 91) - 1;


                        }
                    ReadAddress(readPos);
                }
                catch (IOException e)
                {
                    System.out.println(e);
                }
            }
        });


        //Previous button
        Button btnPrev = new Button("Previous");
        btnPrev.setLayoutX(btnNext.getLayoutX() + 60);
        btnPrev.setLayoutY(btnNext.getLayoutY());
        btnPrev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                readPos--;
                try{
                    if(readPos < 0)
                    {
                        readPos = 0;
                    }
                    System.out.println(readPos);
                    ReadAddress(readPos);
                }
                catch (IOException e)
                {
                    System.out.println(e);
                }
            }
        });

        //last button
        Button btnLast = new Button("Last");
        btnLast.setLayoutX(btnPrev.getLayoutX() + 85);
        btnLast.setLayoutY(btnPrev.getLayoutY());
        btnLast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    File f = new File(fileName);
                    readPos = (int)f.length()/ 91 - 1;
                    ReadAddress(readPos);
                }
                catch (IOException e)
                {
                    System.out.println(e);
                }
            }
        });
        //update button
        Button btnUpdate = new Button("Update");
        btnUpdate.setLayoutY(btnLast.getLayoutY());
        btnUpdate.setLayoutX(btnLast.getLayoutX() + 55);
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(readPos < 0)
                {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText(null);
                    a.setTitle("warning");
                    a.setContentText("No file selected to update!");
                    a.showAndWait();
                }
                else
                {
                    try {
                        if(CheckFields()) {
                            UpdateFile(readPos);
                        }
                    }
                    catch (IOException e)
                    {
                        System.out.println(e);
                    }
                }
            }
        });




        Pane root = new Pane();
        root.getChildren().addAll(lblName, txtName, lblStreet, txtStreet, lblCity, txtCity, lblState, txtState, lblZip, txtzip, btnAdd, btnFirst, btnNext, btnPrev, btnLast, btnUpdate);




        Scene scene = new Scene(root, 550, 170);


        primaryStage.setScene(scene);
        primaryStage.show();
    }




}
