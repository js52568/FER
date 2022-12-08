import java.util.Arrays;

public class Evolution {

    int populationSize;
    double mutationP;
    Example[] examples;

    public Evolution(int populationSize, Example[] examples) {
        this.populationSize = populationSize;
        this.examples = examples;
    }


    public void geneticAlg() {
        Population population = generateFirstPopulation();
        System.out.println("This is the first population " + population.getBestSolution().toString() + " " + population.getGeneration());
    }

    public Population generateFirstPopulation() {
        Population population = new Population();
        Solution[] solutions = new Solution[populationSize];
        Solution bestSolution = null;
        double[] params;
        for (int i=0;i<populationSize;i++) {
            params = RandomParams.getParams();
            double sum = 0;
            int num = 0;
            for (Example example : examples) {
                double v = example.prijenosnaKarakteristika(example.x, example.y, params[0], params[1], params[2], params[3], params[4]);
                double diff = example.value - v;
                double squared = Math.pow(diff, 2);
                sum += squared;
                num++;
            }
            double mse = sum/num;
            Solution solution = new Solution(params,mse);
            solutions[i] = solution;
            if (i==0) bestSolution = solution;
            if (bestSolution.mse > solution.mse) bestSolution = solution;
        }
        population.setBestSolution(bestSolution);
        population.setSolutions(solutions);
        population.setGeneration(1);
        return population;
    }

}
