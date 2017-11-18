package model_old.image_tag_explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageManager {
    public List<Image> images;

    /**
     * Construct a new ImageManager without any Images.
     *
     */
    public ImageManager() {
        images = new ArrayList<>();
    }

    /**
     * Construct a new ImageManager with a given list of abstract Image files.
     *
     * @param files the list of abstract file representations of image files
     */
    public ImageManager(File[] files){
        this.images = new ArrayList<>(files.length);
        for(File file: files) {
            images.add(new Image(file));
        }
        //TODO: figure out how this constructor can just call the one below, passing in the right info.
    }

    /**
     * Construct a new ImageManager with a given list of Image objects.
     *
     * @param images the list of Image objects
     */
    public ImageManager(Image[] images) {
        this.images = new ArrayList<>(Arrays.asList(images));
    }

    /**
     * Add an Image object to the list of Image objects if it does not already exist.
     *
     * @param newImage a new Image object
     * @return Whether the new Image was added to the list.
     */
    public boolean addImage(Image newImage) {
        Boolean here = false;

        for (Image image: images) {
            if (image.equals(newImage)) {
                here = true;
            }
        }

        if (!here) {
            images.add(newImage);
        }

        return !here;
    }

//    public boolean addImages(File[] files) {
//    	boolean ret = true;
//		for(int i = 0; i<files.length; i++ ) {
//			if(!images.add(new Image(files[i]))) {
//				ret = false;
//			}
//		}
//		return ret;
//	}

    /**
     * Remove the Image object from the list of Image objects if it does not already exist.
     *
     * @param thisImage an existing Image object
     * @return Whether the Image was removed from the list.
     */
    public boolean removeImage(Image thisImage) {
        Boolean here = false;

        for (Image image: images) {
            if (image.equals(image)) {
                here = true;
            }
        }

        if (here) {
            images.remove(thisImage);
        }

        return here;
    }

    public Image[] getImages() {
        return images.toArray(new Image[images.size()]);
    }

}
