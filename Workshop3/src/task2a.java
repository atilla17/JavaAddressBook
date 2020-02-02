import java.lang.reflect.Array;
import java.util.*;

public  class task2a {

    public static List<String> topNames2017 = Arrays.asList(
            "amelia",
            "olivia",
            "emily",
            "isla",
            "ava",
            "oliver",
            "jack",
            "charlie",
            "harry",
            "jacob"
    );


    public static void Go()
    {
       PrintSortedListB(topNames2017);



    }

    private static void PrintSortedList(List<String> list_)
    {
        Collections.sort(list_);
        list_.forEach(name_ -> {
            char[] tmp = new char[name_.length()];
            tmp = name_.toCharArray();
            tmp[0] = Character.toUpperCase(tmp[0]);
            System.out.println(tmp);

        });/*
        for(int i = 0; i < list_.size(); i++)
        {
          char[] tmp = new char[list_.size()];
          tmp = list_.get(i).toCharArray();
          System.out.println(tmp);

        }*/
    }




   private static void PrintSortedListB(List<String> list_)
   {

       Collections.sort(list_);
       list_.stream().map(String::toUpperCase).forEach(System.out::println);
   }

   public void example()
   {
      
   }










}
