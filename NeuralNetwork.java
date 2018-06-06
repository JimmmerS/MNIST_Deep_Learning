
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NeuralNetwork {

    // Each layer has a 2d array of weights connecting this layer's nodes to the next
    private double[][][] weights;

    // Each layer i has layerSizes[i] biases
    private double[][] biases;

    private int numLayers;
    private int inputSize;
    private int outputSize;

    // This is a universal constructor for use with later networks

    // Given an array where each element is the layer size, construct
    // a randomized neural network
    public NeuralNetwork(int[] layerSizes){

        Random random = new Random();

        if(layerSizes.length < 2){
            System.out.println("Insufficient node layers");
            System.exit(1);
        }

        numLayers = layerSizes.length;
        inputSize = layerSizes[0];
        outputSize = layerSizes[numLayers - 1];

        /*numConnections = 0;
        for(int i = 0; i < numLayers - 1; i++){
            numConnections += layerSizes[i] * layerSizes[i + 1];
        }*/

        // The first layer is input, so we don't set biases for it
        weights = new double[numLayers - 1][][];
        biases = new double[numLayers - 1][];

        // We want to match up the nodes from our current layer to those in the next layer
        // This creates a 2d array of connections where each node from this layer is connected
        // to each node in the following layer.
        for(int layer = 0; layer < numLayers - 1; layer++){
            weights[layer] = new double[layerSizes[layer + 1]][layerSizes[layer]];

            // Populate this 2d layer mapping with random values
            for(int y = 0; y < layerSizes[layer + 1]; y++){
                for(int x = 0; x < layerSizes[layer]; x++){
                    weights[layer][y][x] = random.nextGaussian();
                }
            }
        }

        // Populate the random biases
        for(int i = 0; i < numLayers - 1; i++){
            biases[i] = new double[layerSizes[i]];
            for(int x = 0; x < layerSizes[i]; x++){
                biases[i][x] = random.nextGaussian();
            }
        }
    }

    // Find the output given input
    public double[] feedForward(double[] input){
        double[] currLayerVals = input;

        // Recalculate all the node values
        for(int i = 0; i < numLayers - 1; i++){
            // Do the dot product of weights[i] with the current calculated layer + bias[i]
            // Then sigmoid all of that and repeat
            currLayerVals = sigmoid(addBias(dot(weights[i], currLayerVals), biases[i]));
        }

        // This is the final layer, or output activation values
        return currLayerVals;
    }

    public double[] StochGradDesc(NumberImage[] trainingData, double[] testData, int epochs, int batchSize, int eta){

        int numTests = 0;
        if(testData != null){
            numTests = testData.length;
        }

        // Truncate the input data if it doesn't fit perfectly
        int totalBatches = trainingData.length / batchSize;
        NumberImage[][] miniBatchData = new NumberImage[totalBatches][batchSize];

        for(int i = 0; i < epochs; i++){
            // TODO: Write a function that can shuffle the training data
            //shuffleArray(trainingData);

            // Populate the shuffled minibatch data
            for(int batchNum = 0; batchNum < totalBatches; batchNum++){
                for(int j = 0; j < batchSize; j++){
                    miniBatchData[batchNum][j] = new NumberImage(trainingData[batchNum * batchSize + j]);
                }
            }

            for(int batchNum = 0; batchNum < miniBatchData.length; batchNum++){
                Run_Mini_Batch(miniBatchData[batchNum], eta);
            }

            if(testData != null){

            }else{

            }
        }

        return null;
    }

    // This only works if the dimensions line up
    public double[] dot(double[][] weights, double[] input){
        double[] output = new double[input.length];
        int tmp;

        // Fill the resulting matrix
        for(int row = 0; row < 2; row++){

            tmp = 0;
            for(int col = 0; col < input.length; col++){
                tmp += weights[row][col] * input[col];
            }
            output[row] = tmp;
        }
        return output;
    }

    public double[] addBias(double[] input, double[] bias){
        double[] output = new double[input.length];
        for(int i = 0; i < bias.length; i++){
            output[i] = input[i] + bias[i];
        }
        return output;
    }

    public void Run_Mini_Batch(NumberImage[] batchData, int eta){
        //nabla_b = [np.zeros(b.shape) for b in self.biases]
        //nabla_w = [np.zeros(w.shape) for w in self.weights

        double[][] updatedBiases = new double [biases.length][];
        double[][][] updatedWeights = new double [weights.length][][];

        for(int i = 0; i < biases.length; i++){
            updatedBiases[i] = new double[biases[i].length];
        }

        for(int i = 0; i < weights.length; i++){
            updatedWeights[i] = new double[weights[i].length][weights[i][0].length];
        }

        for(int batchNum = 0; batchNum < batchData.length; batchNum++){

        }

    }

    public double[][] backprop(NumberImage numberImage){
        double[][] biasWeightDeltas = new double[1][];
        return null;
    }

    /*
    TODO: It might be useful to reuse the input array

    TODO: Here is a link for a more efficient approach to sigmoid, a possible improvement

    https://stackoverflow.com/questions/2887815/speeding-up-math-calculations-in-java?
    utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     */
    public static double[] sigmoid(double[] input){
        double[] scaledOutput = new double[input.length];

        // Squeeze each value into [0, 1]
        for(int i = 0; i < input.length; i++){
            scaledOutput[i] = (1.0 / (1.0 + Math.exp(-input[i])));
        }
        return scaledOutput;
    }

    // Shuffle the input array in place
    public static void shuffleArray(double[] ar){
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random random = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--){
            int index = random.nextInt(i + 1);
            // Simple swap
            double a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

}
