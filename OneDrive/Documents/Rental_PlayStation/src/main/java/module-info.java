module com.mycompany.rental_playstation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens com.mycompany.rental_playstation to javafx.fxml;
    exports com.mycompany.rental_playstation;
}
