
import java.util.Random;
public class NeuralNetwork {

    // Each layer has a 2d array of weights connecting this layer's nodes to the next
    private double[][][] weights;

    // Each layer i has layerSizes[i] biases
    private double[][] biases;

    private int numLayers;
    private int inputSize;
    private int outputSize;

    int numConnections;

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

    // This only works if the dimensions line up
    public double[] dot(double[][] weights, double[] input){
        double[] output = new double[input.length];

        // Fill the resulting matrix

        return output;
    }

    public double[] addBias(double[] input, double[] bias){
        double[] output = new double[input.length];
        for(int i = 0; i < bias.length; i++){
            output[i] = input[i] + bias[i];
        }
        return output;
    }

    public double[] sigmoid(double[] input){
        double[] scaledOutput = new double[input.length];

        // Squeeze each value into [0, 1]

        return scaledOutput;
    }
}
