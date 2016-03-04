import java.util.*;

public class Main{
	public static void main(String args[]){
		String a = new String("test");
		String b = new String("test1");
		Map<String,String> map = new HashMap<>();
		map.put(a,"12");
		map.put(b,"12");
		System.out.println(map.toString());
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
	}	
}

