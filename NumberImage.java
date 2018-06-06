import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class NumberImage {
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

    public NumberImage(NumberImage originalNI){
        this.rows = originalNI.rows;
        this.cols = originalNI.cols;
        this.value = originalNI.value;
        this.data = originalNI.data.clone();
        this.rawData = originalNI.rawData.clone();
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

    public double[] unitVectorValue() {
        double[] unitVector = new double[10];
        unitVector[value] = 1;

        return unitVector;
    }

    public int getValue(){ return value; }
    public int[][] getData() { return data; }

    public void setValue(int value) { this.value = value; }
}