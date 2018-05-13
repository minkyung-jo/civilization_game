package view;

import controller.GameController;
import model.Building;
import model.Settlement;
import model.TerrainTile;
import controller.TileType;
import model.MapObject;

import javafx.scene.control.Button;
import javafx.scene.control.Alert;
/**
 * This class should represents the bulding menu
 */
public class BuildingMenu extends AbstractMenu {
    /**
    * there should be an invest and demolish button for this menu
    * as well as functions associated with the buttons
    */
    public BuildingMenu() {
        //TODO

        super();
        Button investButton = new Button("Invest");
        Button demolishButton = new Button("Demolish");

        super.addMenuItem(investButton);
        super.addMenuItem(demolishButton);

        investButton.setOnAction((event) -> {
                TerrainTile tile = GameController.getLastClicked().getTile();
                TileType myTile = tile.getType();
                MapObject occupant = tile.getOccupant();
                if (GameController.getCivilization().getTreasury().getCoins()
                    < 25) {
                    Alert noMoney = new Alert(Alert.AlertType.WARNING);
                    noMoney.setHeaderText("Cannot invest!");
                    noMoney.setTitle("Not enough money.");
                    noMoney.showAndWait();
                } else {
                    ((Building) occupant).invest();
                    GameController.getCivilization().getTreasury().spend(25);
                    GameController.updateResourcesBar();
                }
            });

        demolishButton.setOnAction((event) -> {
                TerrainTileFX clicked = GameController.getLastClicked();
                TerrainTile tile = GameController.getLastClicked().getTile();
                TileType myTile = tile.getType();
                if (!(tile.getOccupant() instanceof Settlement)) {
                    Alert fail = new Alert(Alert.AlertType.WARNING);
                    fail.setHeaderText("Cannot be demolished!");
                    fail.setTitle("Is not a settlement");
                    fail.showAndWait();
                } else if (GameController.getCivilization().getNumSettlements()
                    < 2) {
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setHeaderText("Cannot be demolished!");
                    error.setTitle("Not enough settlements.");
                    error.showAndWait();
                } else {
                // how to demolish...?
                    ((Settlement) tile.getOccupant()).demolish();
                    GameController.updateResourcesBar();
                    GameController.setLastClicked(clicked);
                    tile.setOccupant(null);
                    GameController.getCivilization().decrementNumSettlements();
                    GridFX.update();
                }
            });
    }
}
