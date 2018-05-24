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

        if(layerSizes.length < 2){
            System.out.println("Insufficient node layers");
            System.exit(1);
        }

        numLayers = layerSizes.length;
        inputSize = layerSizes[0];
        outputSize = layerSizes[numLayers - 1];

        numConnections = 0;
        for(int i = 0; i < numLayers - 1; i++){
            numConnections += layerSizes[i] * layerSizes[i + 1];
        }

        weights = new double[numLayers][][];
        biases = new double[numLayers][];

        for(int x = 0; x < numLayers - 1; x++){
            for(int y = 1; y < numLayers; y++){

            }
        }

        // We want to match up the nodes from our current layer to those in the next layer
        // This creates a 2d array of connections where each node from this layer is connected
        // to each node in the following layer.
        for(int layer = 0; layer < numLayers - 1; layer++){
            weights[layer] = new double[layerSizes[layer]][layerSizes[layer + 1]];
        }

        // The first layer is input, so we don't set biases for it
        for(int i = 1; i < numLayers; i++){
            biases[i] = new double[layerSizes[i]];
        }

    }
}
