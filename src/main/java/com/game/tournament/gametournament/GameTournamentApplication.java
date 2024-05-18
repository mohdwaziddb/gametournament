package com.game.tournament.gametournament;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class GameTournamentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameTournamentApplication.class, args);

		/*List<Integer> list1 = Arrays.asList(1,2,3,4,5,1);
		list1.stream().forEach(System.out::println);
		Set<Integer> collect = list1.stream().collect(Collectors.toSet());
		List<Integer> collect2 = list1.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

		System.out.println(collect);
		System.out.println(collect2);
		List<Integer> collect1 = list1.stream().filter(s->s%2==0).collect(Collectors.toList());
		List<Integer> collect11 = list1.stream().filter(s-> s%2==0).collect(Collectors.toList());
		System.out.println(collect1);
		Stream<Object> build = Stream.builder().add("Banana").add("Apple").add("Mango").build();
		//Set<Object> collect2 = build.collect(Collectors.toSet());
		//System.out.println(collect2);

		List<Object> collect3 = build.sorted().collect(Collectors.toList());
		System.out.println(collect3);


		List<String> ll = Arrays.asList("C","D","S","G","P");
		List<String> collect4 = ll.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		Map<String, Integer> collect5 = ll.stream().collect(Collectors.toMap(s -> s, String::length));
		System.out.println(collect5);

		List<Map<String,Object>> ll1 = new LinkedList<>();
		Map<String,Object> m1 = new HashMap<>();
		m1.put("name","Wazid");
		m1.put("class","II");
		ll1.add(m1);

		m1 = new HashMap<>();
		m1.put("name","Shivam");
		m1.put("class","V");
		ll1.add(m1);

		List<Map<String, Object>> name = ll1.stream().filter(s -> s.get("name") != "Shivam").collect(Collectors.toList());
		System.out.println(name);
		List<Map<String, Object>> collect6 = ll1.stream().sorted(Comparator.comparing(s->s.get("name").toString())).collect(Collectors.toList());
		System.out.println(collect6);

		List<String> aaa = Arrays.asList("a","b","c","d");
		List<String> collect7 = aaa.stream().map(String::toUpperCase).collect(Collectors.toList());
		System.out.println(collect7);

		List<String> collect8 = aaa.stream().map(String::toUpperCase).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		System.out.println(collect8);

		Map<Long,Object> mm = new HashMap<>();
		mm.put(2l,"Sonu");
		mm.put(1l,"Wazid");
		mm.put(3l,"Shivam");

		Map<Long, Object> collect9 = mm.entrySet().stream().sorted(Map.Entry.<Long,Object>comparingByKey().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldvalue,newVlaue)->oldvalue,HashMap::new));
		*//*for (Map.Entry a : mm.entrySet()){

		}*//*

		List<Integer>  kk = Arrays.asList(1,2,3,4,5);
		int sum = kk.stream().mapToInt(Integer::intValue).sum();
		System.out.println(collect9);*/

	}

}
