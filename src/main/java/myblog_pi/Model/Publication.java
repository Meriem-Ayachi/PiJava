package myblog_pi.Model;

import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class Publication {
    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty shortDescription;
    private SimpleStringProperty content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Forum_Commentaire commentaire;
    private String image;
    private Like likee;
    
    private ObjectProperty<Image> imageProperty;

    public Publication() {
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.shortDescription = new SimpleStringProperty();
        this.content = new SimpleStringProperty();
        this.imageProperty = new SimpleObjectProperty<>();
    }

    public Publication(SimpleIntegerProperty id, SimpleStringProperty title, SimpleStringProperty shortDescription, SimpleStringProperty content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Publication(SimpleStringProperty shortDescription, SimpleStringProperty content, LocalDateTime createdAt) {
        this.shortDescription = shortDescription;
        this.content = content;
        this.createdAt = createdAt;
    }

 
    
    

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getShortDescription() {
        return shortDescription.get();
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription.set(shortDescription);
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty shortDescriptionProperty() {
        return shortDescription;
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public Image getImageObject() {
        return imageProperty.get();
    }

    public void setImageObject(Image image) {
        imageProperty.set(image);
    }

    public ObjectProperty<Image> imageProperty() {
        return imageProperty;
    }
}
