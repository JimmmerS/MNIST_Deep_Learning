/*
    This class will connect to the MNIST database and pull in new images
 */


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.GZIPInputStream;


public class MNIST {

    // These correspond to the subset of MNIST data in the .gz files
    private static final int TRAINING_SIZE = 60000;
    private static final int VALIDATION_SIZE = 10000;
    private static final int TEST_SIZE = 10000;

    private static final String train_images_file = "src/data/train-images-ubyte.gz";
    private static final String train_labels_file = "src/data/train-labels-ubyte.gz";
    private static final String test_images_file = "src/data/test-images-ubyte.gz";
    private static final String test_labels_file = "src/data/test-labels-ubyte.gz";

    private NumberImage[] trainingImages;
    private NumberImage[] testImages;

    private Image exampleImage;
    private int exampleCounter = 0;

    public MNIST (){
        trainingImages = new NumberImage[TRAINING_SIZE];
        testImages = new NumberImage[TEST_SIZE];
        exampleImage = null;
        DataInputStream trainImageStream = null, trainLabelStream = null,
                        testImageStream = null, testLabelStream = null;

        try {
            trainImageStream = new DataInputStream(new GZIPInputStream(new FileInputStream(
                    train_images_file)));
            trainLabelStream = new DataInputStream(new GZIPInputStream(new FileInputStream(
                    train_labels_file)));
            testImageStream = new DataInputStream(new GZIPInputStream(new FileInputStream(
                    test_images_file)));
            testLabelStream = new DataInputStream(new GZIPInputStream(new FileInputStream(
                    test_labels_file)));

            // Get rid of magic numbers
            trainImageStream.readInt();
            trainLabelStream.readInt();
            testImageStream.readInt();
            testLabelStream.readInt();

            // Get rid of extra info we don't need for an image file (We will change this)
            int numImages = trainImageStream.readInt(); // Number of Images
            int rows = trainImageStream.readInt(); // Number of rows
            int cols = trainImageStream.readInt(); // Number of cols

            trainLabelStream.readInt(); // Number of items

            System.out.println("NUM IMAGES: " + numImages);
            System.out.println("ROWS: " + rows);
            System.out.println("COLS: " + cols);

            // Go through all the images we see
            for(int j = 0; j < numImages; j++) {
                byte[] rawImage = new byte[rows * cols];
                for (int i = 0; i < rows * cols; i++) {
                    rawImage[i] = trainImageStream.readByte();
                }

                // Create a new NumberImage object from this chunk of data
                trainingImages[j] = new NumberImage(rows, cols, rawImage);
                trainingImages[j].setValue(trainLabelStream.readByte() & 0xFF);
            }

        } catch (IOException e) {
            System.out.println("Data file couldn't be read");
            System.exit(1);
        }
    }

    public Image getExampleImage() {
        return trainingImages[exampleCounter].getImage();
    }
    public int getValue() {
        return trainingImages[exampleCounter++].getValue();
    }


}
