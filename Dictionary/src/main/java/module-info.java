module Module {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires freetts;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires com.google.gson;
    requires okhttp3;



    opens frontend to javafx.fxml;
    exports frontend;
}