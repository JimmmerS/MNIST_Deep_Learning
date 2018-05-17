/*
    This class will connect to the MNIST database and pull in new images
 */
public class MNIST {

    private static final int TRAINING_SIZE = 50000;
    private static final int VALIDATION_SIZE = 10000;
    private static final int TEST_SIZE = 10000;

    public MNIST (){

    }

    class image {
        private int value;

        // Each value is between 0x00 and 0xFF
        private byte[][] data;

        public image (){
            data = new byte[255][255];
            value = 0;

            // Read in the MNIST data
        }

        public int getValue(){ return value; }
        public byte[][] getData() { return data; }
    }
}
