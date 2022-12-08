import java.util.Arrays;
import java.util.Random;

public class Elimination extends Evolution{
    public Elimination(int populationSize, Example[] examples) {
        super(populationSize, examples);
    }

    int iterNumber = 10000;
    int pm = 1;

    public void eliminationGeneticAlg() {
        Population population = generateFirstPopulation();
        System.out.println("This is the first population " + population.getBestSolution().toString() + " " + population.getGeneration());
        int iter = 1;
        while (population.bestSolution.getMse() > 0.01 && iter < iterNumber) {
            //Solution[] three = chooseThree(population.getSolutions());
            Solution[] three = proportionalChoose(population);
            int indexOfWorst = findWorst(three,population);
            Solution[] parents = getParents(three);
            Solution[] old = population.getSolutions();
            Solution child = uniformCrossover(parents);
            child = mutate(child);
            child.setMse(calculateMSE(child));
            old[indexOfWorst] = child;
            population.setSolutions(old);
            if (child.getMse() < population.getBestSolution().getMse()) {
                population.setBestSolution(child);
                System.out.println("new best solution: " + child + " " + population.getGeneration());
            }
            iter++;
            population.setGeneration(iter);
        }
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
    //duplikati su
    private Solution[] getParents(Solution[] three) {
        double worstMse = three[0].getMse();
        Solution worstSolution = three[0];
        for (Solution s:three) {
            if (s.getMse() > worstMse) {
                worstMse = s.getMse();
                worstSolution = s;
            }
        }
        Solution[] sol = new Solution[2];
        int n = 0;
        for (int i=0;i<3;i++) {
            if (worstSolution != three[i]) {
                sol[n] = new Solution(three[i].getParams(),three[i].getMse());
                n++;
            }
        }
        return sol;
    }

    private int findWorst(Solution[] three, Population population) {
        double worstMse = three[0].getMse();
        Solution worstSol = three[0];
        for (Solution s:three) {
            if (s.getMse() > worstMse) {
                worstMse = s.getMse();
                worstSol = new Solution(s.getParams(),s.getMse());
            }
        }
        int index = -1;
        for (int i=0;i<population.getSolutions().length;i++) {
            if (Arrays.equals(population.getSolutions()[i].getParams(),worstSol.getParams())) {
                index = i;
            }
        }
        return index;
    }

    private Solution[] proportionalChoose(Population population) {
        double sum = 0;
        for (int i=0;i<populationSize;i++) {
            //sum += (worstMse-population.getSolutions()[i].getMse());
            sum += 1/population.getSolutions()[i].getMse();
        }
        int[] indexes = new int[3];
        int j = 0;
        while (j<3) {
            Random r = new Random();
            double limit = 0 + (sum) * r.nextDouble();
            int chosen = 0;
            double upperLimit = population.getSolutions()[0].getMse();
            while (limit > upperLimit && chosen<populationSize-1) {
                chosen++;
                upperLimit += population.getSolutions()[chosen].getMse();
            }
            boolean inParents = isInRandoms(indexes,chosen);
            if (!inParents) {
                indexes[j] = chosen;
                j++;
            }
            //parents[j] = population.getSolutions()[chosen];
            //j++;
        }
        Solution[] tour = new Solution[3];
        int n =0;
        for (int index:indexes) {
            tour[n] = population.getSolutions()[index];
            n++;
        }
        return tour;
    }

    private Solution[] chooseThree(Solution[] solutions) {
        int[] randoms = new int[3];
        int counter = 0;
        int index;
        while (counter < 3) {
            Random r = new Random();
            index = r.nextInt(solutions.length);
            if (!isInRandoms(randoms,index)) {
                randoms[counter] = index;
                counter++;
            }
        }
        Solution[] three = new Solution[3];
        for (int i=0;i<3;i++) {
            three[i] = solutions[randoms[i]];
        }
        return three;
    }

    public boolean isInRandoms(int[] arr,int num) {
        boolean ret = false;
        for (int element : arr) {
            if (element == num) {
                ret = true;
            }
        }
        return ret;
    }
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
}
