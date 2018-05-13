package model;

import javafx.scene.image.Image;
/**
 * Represents a Pyramid that can increase philosophy.
 *
 * @version 1.0
 * @author Jim Harris
 */
class CandyCrush extends Landmark {

    /**
     * Public constructor.
     *
     * @param owner the Civilization that owns this Building.
     */
    public CandyCrush(Civilization owner) {
        super(owner);
    }

    @Override
    public void invest() {
        super.invest();
        getOwner().getTechnology().philosophize();
    }

    @Override
    public String toString() {
        return "CandyCrush " + super.toString();
    }

    @Override
    public Image getImage() {
        return super.getImage();
    }
}
