package fpt.com.view;

import fpt.com.Product;
import fpt.com.core.view.BaseView;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * View: Customer
 */
public class ViewCustomer
        extends BaseView {

    /**
     * TableView: Products
     */
    private ProductsTableView<Product> productsTableView = new ProductsTableView<Product>();

    /**
     * TableView: Orders
     */
    private OrdersTableView<Product> ordersTableView = new OrdersTableView<Product>();

    /**
     * Button: Buy
     */
    private Button buyButton = new Button("Buy");

    /**
     * Input: Quantity
     */
    private TextField quantityInput = new TextField();

    /**
     * Threads for networking purpose
     */
    private Thread requestThread;
    private Thread responseThread;

    /**
     * Constructor
     */
    public ViewCustomer() {
        // Create left part: Products
        Label productsLabel = new Label("Products");
        HBox  buyGroup      = new HBox(buyButton, quantityInput);
        VBox  productsBox   = new VBox(productsLabel, productsTableView, buyGroup);
        this.quantityInput.setPromptText("Quantity...");

        // Create right part: Orders
        Label ordersLAbel = new Label("Orders");
        VBox  ordersBox   = new VBox(ordersLAbel, ordersTableView);

        // Time
        Label timeLabel = new Label("Time:");
        Label timeDataLabel = new Label();
        HBox timeGroup = new HBox(timeLabel, timeDataLabel);
        VBox timeBox = new VBox(timeGroup);

        // Setting up splitpane with product and orders section
        SplitPane contentPane = new SplitPane(productsBox, ordersBox);
        contentPane.prefHeightProperty().bind(this.heightProperty());
        this.setMinHeight(300);

        // Add Splitpane to View
        this.getChildren().add(timeBox);
        this.getChildren().add(contentPane);

        // Activate add button only when all required input fields are filled
        BooleanBinding booleanBinding = quantityInput
                .textProperty()
                .isEqualTo("");
        buyButton.disableProperty().bind(booleanBinding);

        //Threads
        sendUDPTimeRequest();
        this.requestThread.start();
        getUDPTimeResponse(timeDataLabel);
        this.responseThread.start();
    }

    /**
     * Add event handler for buttons
     *
     * @param eventHandler
     */
    public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
        buyButton.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public Button getBuyButton() {
        return this.buyButton;
    }

    /**
     * Get TableView: Products
     *
     * @return
     */
    public TableView<Product> getProductsTable() {
        return productsTableView;
    }

    /**
     * Get TableView: Orders
     *
     * @return
     */
    public TableView<Product> getOrdersTable() {
        return ordersTableView;
    }

    /**
     * Get selected row index of table
     *
     * @return
     */
    public Integer getSelectedProductIndex() {
        return productsTableView.getSelectionModel().getSelectedIndex();
    }

    /**
     * Get content of quantity input
     *
     * @return
     */
    public Integer getQuantity() {
        try {
            int number = Integer.parseInt(quantityInput.getText());
            if (number > 0) {
                return Integer.parseInt(quantityInput.getText());
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Send response for time to socket
     */
    private void sendUDPTimeRequest() {
        InetAddress address = null;
        try {
            // Get ip by hostname
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        final byte[] command = "time".getBytes();
        final DatagramPacket packet = new DatagramPacket(command, command.length, address, 6667);

        // Initialize thread for sending packet through socket
        this.requestThread = new Thread("Time Request") {
            @Override
            public void run(){
                try (DatagramSocket socket = new DatagramSocket()) {
                    while (true) {
                        // Send command packet
                        socket.send(packet);

                        // Sleep for a second before sending next package
                        sleep(TimeUnit.SECONDS.toMillis(1));
                    }
                } catch (IOException | InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Socket for updating time
     *
     * @param timeDataLabel
     */
    private void getUDPTimeResponse(Label timeDataLabel) {
        this.responseThread = new Thread("Time Response") {
            @Override
            public void run(){
                try (DatagramSocket socket = new DatagramSocket(6667)) {
                    while (true) {
                        byte[] answer = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(answer,
                                                                   answer.length);

                        // Wait for packet
                        try {
                            socket.receive(packet);
                            System.out.println("Recieved packet [IP: " + packet.getAddress() + ":" + packet
                                    .getPort() + "] with message: " + new String(packet.getData(), 0,
                                                                                 packet.getLength()));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                timeDataLabel.setText(" " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(
                                        Calendar.getInstance().getTime()));
                            }
                        });
                    }
                } catch (SocketException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
    }
}
