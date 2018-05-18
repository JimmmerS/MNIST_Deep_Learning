/*
    This class will connect to the MNIST database and pull in new images
 */


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
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

    private static final String train_images_file = "src/train-images-ubyte.gz";
    private static final String train_labels_file = "src/train-labels-ubyte.gz";
    private static final String test_images_file = "src/test-images-ubyte.gz";
    private static final String test_labels_file = "src/test-labels-ubyte.gz";

    private NumberImage[] trainingImages;
    private NumberImage[] testImages;

    private Image exampleImage;

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
            trainImageStream.readInt(); // Number of Images
            int rows = trainImageStream.readInt(); // Number of rows
            int cols = trainImageStream.readInt(); // Number of cols

            byte[] singleImage = new byte[rows * cols];
            for(int i = 0; i < rows * cols; i++){
                singleImage[i] = trainImageStream.readByte();
            }


            exampleImage = new Image(new ByteArrayInputStream(singleImage));


        } catch (IOException e) {
            System.out.println("Data file couldn't be read");
            System.exit(1);
        }
    }

    public Image getExampleImage() { return exampleImage; }

    class NumberImage {
        private int value;

        // Each value is between 0x00 and 0xFF
        private byte[][] data;

        public NumberImage (){
            data = new byte[255][255];
            value = 0;

            // Read in the MNIST data
        }

        public int getValue(){ return value; }
        public byte[][] getData() { return data; }
    }
}
