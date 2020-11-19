/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appagenda;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author aldob
 */
public class Main extends Application {
    
    private EntityManagerFactory emf;
    private EntityManager em;

    
    public static void main(String[] args) {
        launch(args);
    }

    //START
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        StackPane rootMain = new StackPane();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AgendaView.fxml"));
        //Parent root = fxmlLoader.load(); ANTES DE AÑADIR OTRA VENTANA
        Pane rootAgendaView=fxmlLoader.load();
        
        rootMain.getChildren().add(rootAgendaView);
        
        //Scene scene = new Scene(root); ANTES DE AÑADIR OTRA VENTANA
        
        Scene scene = new Scene(rootMain,600,400);
        primaryStage.setTitle("App Agenda");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Conexión a la BD creando los objetos EntityManager y
        // EntityManagerFactory
        emf=Persistence.createEntityManagerFactory("AppAgendaPU");
        em=emf.createEntityManager();
        
        //CARGAR CONTROLADOR
        AgendaViewController agendaViewController =
            (AgendaViewController)fxmlLoader.getController();
          
        agendaViewController.setEntityManager(em);
        
        //CARGAR DATOS DE LA BASE DE DATOS
        agendaViewController.cargarTodasPersonas();
    }
    
    //METODO STOP
    @Override
    public void stop(){        
        //CERRAR entitimanager y factory
        em.close();
        emf.close();
        
        try {
            DriverManager.getConnection("jdbc:derby:BDAgenda;shutdown=true");
        } catch (SQLException ex) {
        }
    }

//FIN CLASE
}



