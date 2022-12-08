import java.io.*;
import java.util.Scanner;

public class DatasetOneElim {

    public static void main(String[] args) throws IOException {
        File file = new File(
                "C:\\Users\\Jura\\Desktop\\zad4-dataset1.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));

        double x,y,izlaz;
        Example[] examples = new Example[250];
        int num = 0;
        String line = null;
        while(true){
            if((line = input.readLine())!=null){
                Scanner s = new Scanner(line);
                x = s.nextDouble();
                y = s.nextDouble();
                izlaz = s.nextDouble();
            }
            else {
                break;
            }

            Example example = new Example(x,y,izlaz);
            examples[num] = example;
            System.out.flush();
            num++;
        }

        Elimination elim = new Elimination(100,examples);
        elim.eliminationGeneticAlg();
    }
}
