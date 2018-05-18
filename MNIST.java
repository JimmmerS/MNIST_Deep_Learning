/*
    This class will connect to the MNIST database and pull in new images
 */


import java.io.*;
import java.util.zip.GZIPInputStream;

public class MNIST {

    // These correspond to the subset of MNIST data in the .gz files
    private static final int TRAINING_SIZE = 60000;
    private static final int VALIDATION_SIZE = 10000;
    private static final int TEST_SIZE = 10000;

    private static final String train_images_file = "train-images-ubyte.gz";
    private static final String train_labels_file = "train-labels-ubyte.gz";
    private static final String test_images_file = "test-images-ubyte.gz";
    private static final String test_labels_file = "test-labels-ubyte.gz";

    private NumberImage[] trainingImages;
    private NumberImage[] testImages;

    public MNIST (){
        trainingImages = new NumberImage[TRAINING_SIZE];
        testImages = new NumberImage[TEST_SIZE];


        try {
            DataInputStream trainImageStream = new DataInputStream(new GZIPInputStream(new FileInputStream(
                    train_images_file)));
            DataInputStream trainLabelStream = new DataInputStream(new GZIPInputStream(new FileInputStream(
                    train_labels_file)));
            DataInputStream testImageStream = new DataInputStream(new GZIPInputStream(new FileInputStream(
                    test_images_file)));
            DataInputStream testLabelStream = new DataInputStream(new GZIPInputStream(new FileInputStream(
                    test_labels_file)));
        } catch (IOException e){
            System.out.println("Data file couldn't be read");
            System.exit(1);
        }
    }

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
