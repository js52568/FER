import java.util.Random;

public class Generational extends Evolution{
    public Generational(int populationSize, Example[] examples) {
        super(populationSize, examples);
    }

    int iterNumber = 10000;
    int pm = 1;

    public void generationalGeneticAlg(boolean elitism) {
        Population population = generateFirstPopulation();
        System.out.println("This is the first population " + population.getBestSolution().toString() + " " + population.getGeneration());
        int iter = 1;
        while (population.bestSolution.getMse() > 0.01 && iter < iterNumber) {
            Population nextPopulation = new Population();
            nextPopulation.setSolutions(new Solution[populationSize]);
            nextPopulation.setGeneration(iter+1);
            int popSize = populationSize;
            if (elitism) {
                //System.out.println("before " + population.getBestSolution());
                nextPopulation.setBestSolution(population.getBestSolution());
                Solution[] old = nextPopulation.getSolutions();
                old[0] = population.getBestSolution();
                nextPopulation.setSolutions(old);
                //System.out.println("after " + nextPopulation.getBestSolution());
                for (int i=1;i<popSize;i++) {
                    Solution[] parents = proportionalChoose(population);
                    Solution child = uniformCrossover(parents);
                    child = mutate(child);

                    child.setMse(calculateMSE(child));

                    Solution[] temp = nextPopulation.getSolutions();
                    temp[i] = child;
                    nextPopulation.setSolutions(temp);
                    if (child.getMse() < nextPopulation.getBestSolution().getMse()) {
                        nextPopulation.setBestSolution(child);
                        System.out.println("new best solution: " + child + " " + nextPopulation.getGeneration());
                    }
                }
            }
            else {
                for (int i=0;i<popSize;i++) {
                    Solution[] parents = proportionalChoose(population);
                    Solution child = uniformCrossover(parents);
                    child = mutate(child);
                    child.setMse(calculateMSE(child));

                    if (i==0) {
                        nextPopulation.setBestSolution(child);
                        Solution[] old = nextPopulation.getSolutions();
                        old[0] = child;
                        nextPopulation.setSolutions(old);
                        //System.out.println("new best solution: " +child.toString() + " " + nextPopulation.getGeneration());
                    } else {
                        Solution[] temp = nextPopulation.getSolutions();
                        temp[i] = child;
                        nextPopulation.setSolutions(temp);
                        if (child.getMse() < nextPopulation.getBestSolution().getMse()) {
                            nextPopulation.setBestSolution(child);
                            System.out.println("new best solution: " + child.toString() + " " + nextPopulation.getGeneration());
                        }
                    }

                }

            }

            population = nextPopulation;
            iter++;
        }
    }

    private double calculateMSE(Solution child) {
        double sum = 0;
        int num = 0;
        for (Example example : examples) {
            double v = example.prijenosnaKarakteristika(example.x, example.y, child.getParams()[0], child.getParams()[1], child.getParams()[2], child.getParams()[3], child.getParams()[4]);
            double diff = example.value - v;
            double squared = Math.pow(diff, 2);
            sum += squared;
            num++;
        }
        double mse = sum/num;
        return mse;
    }

    private Solution mutate(Solution child) {
        for (int i=0;i<5;i++) {
            Random r = new Random();
            double randomVal = 0 + (100) * r.nextDouble();
            if (randomVal < pm+1) {
                double old[] = child.getParams();
                Random r2 = new Random();
                double randomValue = -4 + (4 + 4) * r2.nextDouble();
                old[i] = randomValue;
                child.setParams(old);
            }
        }
        return child;
    }

    //myb ovdje nes kad kopiram child
    private Solution uniformCrossover(Solution[] parents) {
        Solution p1 = parents[0];
        Solution p2 = parents[1];
        double[] childParams = new double[5];
        Solution child = new Solution();
        for (int i=0;i<5;i++) {
            Random r = new Random();
            double randomVal = 0 + (100) * r.nextDouble();
            if (randomVal < 50) {
                childParams[i] = p1.getParams()[i];
            }
            else {
                childParams[i] = p2.getParams()[i];
            }
        }
        child.setParams(childParams);
        return child;
    }

    private Solution[] proportionalChoose(Population population) {
        double sum = 0;
        double worstMse = population.getSolutions()[0].getMse();
        for (Solution s:population.getSolutions()) {
            if (worstMse < s.getMse()) {
                worstMse = s.getMse();
            }
        }
        for (int i=0;i<populationSize;i++) {
            //sum += (worstMse-population.getSolutions()[i].getMse());
            sum += 1/population.getSolutions()[i].getMse();
        }
        Solution[] parents = new Solution[2];
        for (int j=0;j<2;j++) {
            Random r = new Random();
            double limit = 0 + (sum) * r.nextDouble();
            int chosen = 0;
            double upperLimit = population.getSolutions()[0].getMse();
            while (limit > upperLimit && chosen<populationSize-1) {
                chosen++;
                upperLimit += population.getSolutions()[chosen].getMse();
            }
           parents[j] = population.getSolutions()[chosen];
        }
        return parents;
    }


}
