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

    private static final String train_images_file = "src/train-images-ubyte.gz";
    private static final String train_labels_file = "src/train-labels-ubyte.gz";
    private static final String test_images_file = "src/test-images-ubyte.gz";
    private static final String test_labels_file = "src/test-labels-ubyte.gz";

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
                //exampleImage = new Image(new ByteArrayInputStream(rawImage));
            }

        } catch (IOException e) {
            System.out.println("Data file couldn't be read");
            System.exit(1);
        }
    }

    public Image getExampleImage() {
        return trainingImages[exampleCounter++].getImage();
    }

    class NumberImage {
        private int rows;
        private int cols;

        private int value;

        // Each value is between 0x00 and 0xFF
        private int[][] data;
        private byte[][] rawData;

        public NumberImage (int rows, int cols, byte[] raw){
            this.rows = rows;
            this.cols = cols;
            this.rawData = new byte[rows][cols];

            data = new int[rows][cols];

            // Set this with the labels
            value = 0;

            // Convert the MNIST data to the unsigned byte val stored in an int
            for(int row = 0; row < rows; row++){
                for(int col = 0; col < cols; col++){
                    data[row][col] = raw[row * cols + col] & 0xFF;
                    rawData[row][col] = raw[row * cols + col];
                }
            }
        }

        // Return the visual representation of the data
        public WritableImage getImage(){
            WritableImage image = new WritableImage(cols, rows);
            PixelWriter pixelWriter = image.getPixelWriter();
            int grayScaleConv = 0xFF000000;
            for(int row = 0; row < rows; row++){
                for(int col = 0; col < cols; col++){
                    pixelWriter.setArgb(col, row, UByteToARGB(rawData[row][col]));
                }
            }

            return image;
        }

        public int UByteToARGB(byte ubyte){
            // Alpha value of 255
            int result = 0xFF000000;

            // Set R,G,B to the same input value
            result += (0xFF - (ubyte & 0xFF));
            result += (0xFF - (ubyte & 0xFF)) << 8;
            result += (0xFF - (ubyte & 0xFF)) << 16;

            return result;
        }

        public int getValue(){ return value; }
        public int[][] getData() { return data; }

        public void setValue(int value) { this.value = value; }
    }
}
