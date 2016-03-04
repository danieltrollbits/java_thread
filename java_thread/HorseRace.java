import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.ConcurrentHashMap;

public class HorseRace{
	private static int numberOfHorses = 0;
	public static Map<String,Integer> lastHorses = new ConcurrentHashMap<>();

	public static void main(String args[]){
		Runnable barrierAction = new Runnable() {
    		public void run() {
    	    	System.out.println("All horses at starting line");
    		}
		};

		Scanner in = new Scanner(System.in);
		System.out.print("Number of horses... ");
		while(!in.hasNextInt()){
			System.out.print("Not a number... ");
			in.next();
		}
		numberOfHorses = in.nextInt();

		CyclicBarrier barrier = new CyclicBarrier(numberOfHorses, barrierAction);

		List<Horse> list = new ArrayList<>();
		int numberOfHorsesAtStartingLine = 0;

		for (int i = 1; i <= numberOfHorses; i++){
			Horse horse = new Horse("Horse-" + i,barrier);
			list.add(horse);
		}

		for (int i = 0; i < numberOfHorses; i++){
			list.get(i).start();
		}
	}
}