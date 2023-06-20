module com.pclient.posteoclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.pclient.posteoclient to javafx.fxml;
    exports com.pclient.posteoclient;
}