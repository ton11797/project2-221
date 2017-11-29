import java.util.*;
class lightOut
{
    public static void main(String[] arg)
    {
        //game play1 = new game(3,"110010111");
        Scanner sc=new Scanner(System.in);
        String con="Y",con2="Y";
        do
        {
            con2="Y";
            System.out.printf("Enter Number of rows = ");
            int len=sc.nextInt();
            System.out.printf("Grid size = %d x %d = %d\n",len,len,len*len);
            if(len==4)System.out.printf("!!Warning Grid size 4x4 only 1/16 state have solution\r\n");
            if(len>4) {
                System.out.printf("!!Warning Grid size %dx%d some state may use very long time to solved\r\n", len, len);
                System.out.printf("Do you want to continue(Y/N)?");
                con2 = sc.next();
            }
            if(con2.equals("Y")) {
                System.out.printf("Enter initial states (%d bits) = ", len * len);
                String str = sc.next();
                while (str.length() != len * len) {
                    System.out.println("String wrong.Please try again.\n");
                    System.out.printf("Enter initial states (%d bits) = ", len * len);
                    str = sc.next();
                }
                game play = new game(len, str);
                System.out.printf("Do you want to continue(Y/N)?");
                con = sc.next();
            }
        }
        while(con.toLowerCase().equals("y"));
    }

}
