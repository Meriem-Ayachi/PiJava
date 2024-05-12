
package myblog_pi.Model;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageCell extends TableCell<Publication, Image> {
    private final ImageView imageView;

    public ImageCell() {
        this.imageView = new ImageView();
        imageView.setFitWidth(100); 
        imageView.setPreserveRatio(true);
        setGraphic(imageView);
    }

    @Override
    protected void updateItem(Image item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            imageView.setImage(null);
        } else {
            imageView.setImage(item);
        }
    }
}
