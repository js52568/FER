public class Population {
    Solution[] solutions;
    Solution bestSolution;
    int generation;

    public Population(Solution[] solutions, Solution bestSolution) {
        this.solutions = solutions;
        this.bestSolution = bestSolution;
    }

    public Population(Solution[] solutions, Solution bestSolution, int generation) {
        this.solutions = solutions;
        this.bestSolution = bestSolution;
        this.generation = generation;
    }

    public Population() {
    }

    public Solution[] getSolutions() {
        return solutions;
    }

    public void setSolutions(Solution[] solutions) {
        this.solutions = solutions;
    }

    public Solution getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(Solution bestSolution) {
        this.bestSolution = bestSolution;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public void selectBetter() {
    }
}
