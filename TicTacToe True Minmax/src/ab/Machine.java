package ab;
import java.util.Random;

class Machine
{
	int xo[][],sum=0,user,pointxo[][],k,placed=0,i_base,j_base,totcases,tothe,difficulty,cutoff;      
	// if placed gets 1 then machine's turn is over
	Machine(int ox[][],int user_from_xoxo)
	{
		int i,j;
		pointxo=new int[3][3];
		xo=new int [3][3];
		tothe=5;
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
			{
				xo[i][j]=ox[i][j];
			}
		}
		user=user_from_xoxo;
	}
	void show(int subject[][])    // this is just to test program 
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.print(" "+subject[i][j]);
			}
			System.out.println();
			System.out.println();
		}
		System.out.println();
	}
	int win()      //  win loose are like machine wins/looses
	{
		int i,j,sumr = 0,sumc=0;
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
			{
				sumr+=xo[i][j];
				sumc+=xo[j][i];
			}
			if(sumr==3*(7-user) || sumc==3*(7-user))
			{
				sumr=sumc=0;
				return 1;
			}
			sumr=sumc=0;
		}
		if(3*(7-user)==xo[0][0]+xo[1][1]+xo[2][2] || 3*(7-user)==xo[0][2]+xo[1][1]+xo[2][0])
		{
			return 1;
		}
		return 0;
	}
	int loose()
	{
		int i,j,sumr = 0,sumc=0;
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
			{
				sumr+=xo[i][j];
				sumc+=xo[j][i];
			}
			if(sumr==3*(user) || sumc==3*(user))
			{
				sumr=sumc=0;
				return -1;
			}
			sumr=sumc=0;
		}
		if(3*(user)==xo[0][0]+xo[1][1]+xo[2][2] || 3*(user)==xo[0][2]+xo[1][1]+xo[2][0])
		{
			return -1;
		}
		return 0;
		
	}
	private int tree(int turn,int level,int alpha,int beta) // tothe has depth control
	{
		//System.out.println(" level="+level +" alpha="+alpha+" beta="+beta);
		int i,j,minmax,temp = 0,win_loose=1,change=0;  //we cant upgrage point cuz we need it clean in recrssion 
		// so to store points in pointxo array we need point1
		//System.out.println("level is "+level);
		// for wins , why go to next?
		int alpha_temp=alpha;
		int beta_temp=beta;
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
			{
				if(xo[i][j]==0)
				{
					if(turn==1) // maximize machine
					{
						xo[i][j]=7-user;
						if(win()>0)
						{
							//System.out.println("win Here");
							totcases+=1;
							temp=(10-level)*win_loose;
							if(win_loose>1)
							{
							/*System.out.println("------------");
							show(xo);
							System.out.println(temp);
							System.out.println("------------");*/
							}
							win_loose+=1;
						}
						else
						{
							//if(!( win_loose >=2))
								temp=tree(0,level+1,alpha,beta);
							
						}
						if(alpha<temp) // going towards maximum
						{
							alpha=temp;
							//change=1;
							if(level==0)
							{
								i_base=i;
								j_base=j;
							}
						}
						xo[i][j]=0;
						if(alpha>=beta)
						{
							cutoff+=1;
							//System.out.println("1. cutoff !");
							return beta_temp;
						}
					}
					else   // minimize user
					{
						xo[i][j]=user;
						if(loose()<0)
						{
							totcases+=1;
							temp=((-10)+level)*win_loose;
							//System.out.println("loose Here");
							if(win_loose>1)
							{
							/*System.out.println("------------");
							show(xo);
							System.out.println(temp);
							System.out.println("------------");*/
							}
							win_loose+=1;
						}
						else
						{
							//if(! (win_loose>=2))
								temp=tree(1,level+1,alpha,beta);
							
						}
						if(beta>temp)
						{
							
							//change=1;
							beta=temp;
						}
						xo[i][j]=0;
						if(alpha>=beta)
						{
							cutoff+=1;
							//System.out.println("2. cutoff !");
							return alpha_temp;
						}
					}
				}
			}
		}
		// if draw return zero
		for(i=0;i<3 && change==0;i++)
		{
			for(j=0;j<3;j++)
			{
				if(xo[i][j]==0)
				{
					change=1;
					break;
				}
			}
		}
		if(change==0)
		{
			//alpha_temp==alpha && beta_temp==beta
			totcases+=1;
			return 0;
		}
		
		// otherwise alpha/beta
		if(turn==1)
		{
			return alpha;
		}
		else
		{
			return beta;
		} 
	}
	
	int[][] controller(int ox[][],int turn)
	{
		int i,j,max=-9999;
		pointxo=new int[3][3];
		xo=new int [3][3];
		for(i=0;i<3;i++)
		{
			for(j=0;j<3;j++)
			{
				xo[i][j]=ox[i][j];
			}
		}
		if(difficulty == 2 /*|| difficulty==3*/)
		{
			for(i=0;i<3;i++)          // immediate win takes
			{
				for(j=0;j<3;j++)
				{
					if(xo[i][j]==0)
					{
						xo[i][j]=(7-user);
						if(win()>0)
						{
							return xo;
						}
						xo[i][j]=0;
					}
				}
			}
			for(i=0;i<3;i++)          // immediate loose avoids
			{
				for(j=0;j<3;j++)
				{
					if(xo[i][j]==0)
					{
						xo[i][j]=user;
						if(loose()<0)
						{
							xo[i][j]=(7-user);
							return xo;
						}		
						xo[i][j]=0;
					}	
				}
			}
		}
        Random r=new Random();
		if(difficulty==3)
		{
			totcases=0;
			cutoff=0;
			long startTime=System.nanoTime();
			System.out.println("Machine win rate is "+tree(turn,0,-99999,99999));             	 // fork creation or elimination
			long endTime=System.nanoTime();
			System.out.println("Total cases are "+totcases);
			System.out.println("Total no of cutoffs are "+cutoff);
			System.out.println("Time required to calculate all cases is "+(endTime-startTime)+ " nanosecs");
			/*for(i=0;i<3;i++)                     // we are now checking who has win rate greater 
			{
				for(j=0;j<3;j++)
				{
					if(xo[i][j]==0 && max<pointxo[i][j])
					{
						max=pointxo[i][j];
						i_base=i;
						j_base=j;
					}
				}
			}
			// now to choose which one to take if more than one has max probablity
            
            sum=0;
            for(i=0;i<3;i++) 
			{
				for(j=0;j<3;j++)
				{
					if(xo[i][j]==0 && max == pointxo[i][j])
					{
				        sum++;		
					}
				}
			}
            System.out.println("sum is "+sum);
            if(sum>1)
            {
                int random=r.nextInt(sum);
                sum=0;
                for(i=0;i<3 && sum!=random ;i++)
                {
                    for(j=0; j<3 && sum!=random ;j++)
                    {
                        if(xo[i][j]==0 && max==pointxo[i][j])
                        {
                            i_base=i;
                            j_base=j;
                            sum++;   
                        }
                    }   
                }
            }
            */
            
            
			//show(pointxo);
		}
		else
		{
			
			while(true)
			{
				i_base=r.nextInt(3);
				j_base=r.nextInt(3);
				if(xo[i_base][j_base]==0)
				{
					break;
				}
			}
			System.out.println("here ! "+i_base+"  "+j_base);
		}
		xo[i_base][j_base]=7-user;
		pointxo=null;
		System.gc();
        sum=0;
        i_base=0;j_base=0;
		//System.out.println("total cases are ----> "+totcases);
		return xo;
	}
}
