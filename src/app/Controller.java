package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.controlsfx.control.CheckTreeView;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private CheckTreeView<String> _dataList;
    @FXML private ListView<String> _selectedList;

    IDataModel dataModel = new DataModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         _dataList.setRoot(dataModel.getTreeRoot());
         _dataList.setShowRoot(false);
         _selectedList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
     }

    /**
     * When the add button is pressed all checked names in the check tree view are added to the
     * selected names list.
     */
    public void addButtonPressed() {
         List<TreeItem<String>> checkedNames = _dataList.getCheckModel().getCheckedItems();
         for (TreeItem<String> name : checkedNames) {
             if (name.getChildren().size() < 2) { // if a node is not a leaf, do not add it
                 _selectedList.getItems().add(name.getValue());
             }
         }
         _dataList.getCheckModel().clearChecks(); // clear items checked after they have been added
     }

    /**
     * When the remove button is pressed all selected items in the selected list are removed
     * from being practised.
     */
    public void removeButtonPressed() {
        ObservableList<String> itemsToDelete = _selectedList.getSelectionModel().getSelectedItems();
        _selectedList.getItems().removeAll(itemsToDelete);
     }

    /**
     * When the randomise button the order of the items in the selected list are shuffled.
     */
     public void randomiseButtonPressed() {
         Collections.shuffle(_selectedList.getItems());
     }
}
