package Main;

import controller.ControllerCustomer;
import controller.ControllerShop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ModelShop;
import model.ProductList;
import view.ViewCustomer;
import view.ViewShop;

/**
 * MainClass
 */
public class Main
        extends Application {

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create productlist and add it to the model
        ProductList productList = new ProductList();
        ModelShop   modelShop   = new ModelShop(productList);

        // Create controller and model and link them together
        ViewShop       viewShop       = new ViewShop();
        ControllerShop controllerShop = new ControllerShop();
        controllerShop.link(modelShop, viewShop);

        // Create GUI: Scene, Stage
        Scene sceneShop = new Scene(viewShop);
        primaryStage.setScene(sceneShop);
        primaryStage.show();

        // Create customer controller and model and link them together
        ViewCustomer       viewCustomer       = new ViewCustomer();
        ControllerCustomer controllerCustomer = new ControllerCustomer();
        controllerCustomer.link(modelShop, viewCustomer);

        // Create GUI: Scene, Stage for Customer
        Stage stageCustomer = new Stage();
        Scene sceneCustomer = new Scene(viewCustomer);
        stageCustomer.setScene(sceneCustomer);
        stageCustomer.setX(0);
        stageCustomer.show();
    }
}
