package src.pannel;
import java.until.Random;

public class RandomNumberGenerator{
    
    int Random(String tipo){
        Random random = new Random();
        if(tipo == "ATAQUE"){
            int min = 1;
            int max = 50;
            int customRangeNumber = random.nextInt(max - min + 1) + min;
        }
    }
    

}