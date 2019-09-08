import java.util.*;
//in matrix xo 3 represents x and 4 o it its done by variable xy in inserter
// chance if 0 then machine plays if chance is 1 then user plays.
// xy for user yx for machine
class Xoxo 
{
	static void show(int xo[][])
	{
		int i,j;
		for(i=0;i<3;i++)
		{
			
			System.out.print("|");
			for(j=0;j<3;j++)
			{
				if(xo[i][j]==3)
				{
					System.out.print("x"+"|");
				}
				else
				{
					if(xo[i][j]==4)
					{
						System.out.print("o"+"|");
					}
					else
					{
						System.out.print(" "+"|");
					}
				}
			}
			System.out.println();
			
		}
		System.out.println();
	}
	static int checker(int xo[][])
	{
		int i,j;
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
			{
				if(xo[i][j]==0)
				{
					return 1;
				}
			}
		}
		return 0;
		
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		int i,j,k,row,col,chance = 0,user,entry_no=0/*,tothe=5*/;
		int xo[][]=new int[3][3];
		System.out.println("program is without alpha-beta");
		System.out.println("Enter 1 if you want to play x or 0 to play o ");
		System.out.println("one who choses x plays first !");
		Scanner sc=new Scanner(System.in);
		int term;
		while(true)
		{
			term=sc.nextInt();
			if(term==1)
			{
				System.out.println("you have selected x ");
				user=3;
				chance=1;
				break;
			}
			else
			{
				if(term==0)
				{
					System.out.println("you have selected o ");
					user=4;
					chance=0;
					break;
				}
				else
				{
					System.out.println("wrong choice ! start over again !");
					continue;
				}
			}
		}
		Machine m=new Machine(xo,user);
		System.out.println("enter difficulty level now !");
		System.out.println("1- easy 2- medium 3 -immpossible ");
		while(true)
		{
			
			m.difficulty=sc.nextInt();
			if(m.difficulty ==1 || m.difficulty==2 || m.difficulty==3)
			{
				break;
			}
			else
			{
				System.out.println("Wrong choice please enter difficulty again!");
			}
				
		}
		
		show(xo);
		while(checker(xo)>0)
		{
			if(chance==1)
			{
				System.out.println("Enter row and column in which you want insert ");
				row=sc.nextInt();
				col=sc.nextInt();
				if(xo[row][col]>0)
				{
					
					System.out.println("Position you have opted is already filled pl try again !");
					continue;
				}
				else
				{
					xo[row][col]=user;
					//tothe--;
					//System.out.println(tothe);
					chance=0;
				}
			}
			else
			{
				if(chance==0)
				{
					xo=m.controller(xo,1);
					if(m.tothe>4)
					{
						m.tothe--;
					}
					chance=1;
					show(xo);
				}
			}
			entry_no ++;
			if(m.win()>0)                // machine wins machine looses !
			{
				System.out.println("sorry u lose!");
				System.exit(0);
			}
			if((m.loose()<0))
			{
				System.out.println("congo u win!");
				System.exit(0);
			}
			
		}
		System.out.println("match draws !!!");
	}
}
// if turn =1 machine plays 0 then user plays
// level tell you depth of recurssion count starts from 0 max to max it can go till 8(machine opens game)
//i_base j_base are used for pointxo which keeps track of machine favourable roots

	