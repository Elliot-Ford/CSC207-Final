package model_old.image_tag_explorer;

import java.util.ArrayList;
import java.util.List;

public class Tag implements java.io.Serializable {

  private List<Image> images;
  private String name;

  public Tag(String name) {
    this(name, new Image[0]);
  }

  public Tag(String name, Image[] images) {
    this.name = name;
    this.images = new ArrayList<>(images.length);
    for(Image image : images) {
      addImage(image);
    }
  }

  public void addImage(Image newImage) {
    boolean here = false;
    // Checking if this Image is new to this Tag
    for (Image image : images) {
      if (image.getName().equals(newImage.getName())) {
        here = true;
      }
    }

    if (!here) {
      images.add(newImage);
      newImage.addTag(this);
    }
  }

  public void removeImage(Image image) {
    boolean here = false;
    // Checking if this Image is in this Tag
    for (Image existImage : images) {
      if (existImage.getName().equals(image.getName())) {
        here = true;
      }
    }

    // If this Image is in this Tag, then remove it.
    if (here) {
      images.remove(image);
      image.removeTag(this);
      // TODO: this return value of image.removeTag(this) is not used in this call
    }
  }

  public String getName() {
    return this.name;
  }
}
