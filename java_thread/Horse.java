import java.util.Random;
import java.lang.InterruptedException;
import java.util.concurrent.*;
import java.util.*;

public class Horse implements Runnable {
	private String name;
	private Thread thread;
	private int distanceTravel = 0;
	private CyclicBarrier barrier = null;
	private final int distanceFromBarnToGate = 10;
	private final int distanceFromGateToFinishLine = 20;

	public Horse(String horseName, CyclicBarrier barrier){
		name = horseName;
		this.barrier = barrier;
	}

	public void run(){
		try{
			System.out.println(name + " running to gate");
			int hopDistance;
			while( distanceTravel < distanceFromBarnToGate ){
				hopDistance = hop();
				distanceTravel += hopDistance;
				if ( distanceTravel > distanceFromBarnToGate ) {
					hopDistance -= ( distanceTravel - distanceFromBarnToGate );
					distanceTravel = 10;
				}
				sleep();
				System.out.println(name + " hop " + hopDistance + " unit/s : " + (10-distanceTravel) + " unit/s left " + System.currentTimeMillis());
			}
			this.barrier.await();

			System.out.println(name + " running to finish line " + System.currentTimeMillis());
			distanceTravel = 0;
			boolean boost = false;
			while( distanceTravel < distanceFromGateToFinishLine ){
				if (boost){
					hopDistance = boostHop();
					System.out.println(name + " receive boost -----------------");
				}
				else{
					hopDistance = hop();
				}
				distanceTravel += hopDistance;
				if( distanceTravel > distanceFromGateToFinishLine ){
					hopDistance -= ( distanceTravel - distanceFromGateToFinishLine );
					distanceTravel = 20;
				}
				HorseRace.lastHorses.put(name,distanceTravel);
				if (isLast()){
					boost = true;
				}
				else{
					boost = false;
				}
				sleep();
				// System.out.println(HorseRace.lastHorses + " :");
				System.out.println(name + " hop " + hopDistance + " unit/s : " + (distanceFromGateToFinishLine-distanceTravel) + " unit/s left " + System.currentTimeMillis());	
				
			}
			System.out.println(name + " at finish line");
		}catch(BrokenBarrierException|InterruptedException e){
			System.out.println("Error: " + e.getMessage());
		}
	}

	public boolean isLast(){
		int last = 20;
		String nameLast = "";
		for (Map.Entry<String,Integer> entry : HorseRace.lastHorses.entrySet()){
			if(entry.getValue() < last){
					last = entry.getValue();
					nameLast = entry.getKey();
			}
		}
		HorseRace.lastHorses.put(nameLast,last);
		if (name == nameLast){
			return true;
		}
		else{
			return false;
		}
	}

	public int boostHop(){
		Random random = new Random();
		int min = 11, max = 15;
		int rand =  random.nextInt(max - min + 1) + min;
		return rand;
	}

	public int hop(){
		Random random = new Random();
		int min = 1, max = 10;
		int rand =  random.nextInt(max - min + 1) + min;
		return rand;
	}

	public void sleep(){
		try{
			thread.sleep(1000);
		}catch(InterruptedException e){
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void start (){
      if (thread == null){
         thread = new Thread (this, name);
         thread.start();
      }
   }

}