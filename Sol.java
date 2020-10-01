/**
* Purpose: To write a programme that stimulates the solar system and prints out the final position of
* the planets after a given number of seconds. These final positions are calculated using 3 numerical methods, Euler, Euler-Cramer
* and Verlat.
*@author Sam Dicker
*@version 1.4
*Date 09/12/17
**/
import java.lang.Math;
import java.util.Scanner;
import java.io.*;

public class Sol
{

	public static void main(String[] args) throws IOException
	{	
	    
		//These particle constructors create a particle with its 3D position, 3D velocity and mass (and given name) on the 6th of Decemeber 2017
		Particle Sun= new Particle(2.920248396549780e8,8.972132474121116e8,-1.852273805250082e7,-9.766021015197407,8.999107274274724, 2.343058883707526e-1,1.989e30,"Sun");
		Particle Mercury= new Particle(3.829319260184269e10,3.006579981572141e10,-1.121291495019559e9,-3.924482531869952e4,4.075261406219975e4,6.928932150005902e3,3.285e23,"Mercury");
		Particle Venus = new Particle(-6.288713524018889e10,-8.711498473448260e10,2.420028410161521e9,2.819420158427912e4,-2.057574198728450e4,-1.909682490007154e3,48.685e23,"Venus");
		Particle Earth= new Particle(4.140466469890121e10,1.424503995927281e11,-2.445535338269174e7,-2.908835936996451e4,8.209644361960233e3,-9.912014509612099e-1,5.972e24,"Earth");
		Particle Moon= new Particle(4.129955128358053e10,1.427943443576151e11,-4.029299392382056e7,-3.014009178777005e4,7.927886991734857e3,8.129352859572725e1,734.9e20,"Moon");
		Particle Mars= new Particle(-2.461494412318427e11,-8.135321860360315e9,5.840321737549835e9,1.782308233072359e3,-2.213417581488241e4,-5.077600037411356e2,6.42e23,"Mars");
		Particle Jupiter= new Particle(-6.546426964798173e11,-4.808722956005415e11,1.663711488827327e10,7.580054129332052e3,-9.908450611467071e3,-1.284402525644377e2,1898.13e24,"Jupiter");
		Particle Saturn= new Particle(-1.334215714757109e10,-1.504394065313983e12,2.668798870954269e10,9.129211782543706e3,-1.178929428362886e2,-3.607824250715662e2,5.68e26,"Saturn");
		Particle Uranus= new Particle(2.658409446072430e12,1.342881443829036e12,-2.945261707595515e10,-3.120325134675427e3,5.761001873658634e3, 6.170745789759779e1,86.8103e24,"Uranus");
		Particle Neptune= new Particle(4.287271774704164e12,-1.297057972347536e12,-7.209406620702243e10,1.537407993825956e3,5.235166632803802e3,-1.425296174958979e2,102.41e24,"Neptune");
		Particle Pluto= new Particle(1.600580829670277e12,-4.741583382604813e12,4.439465334439969e10,5.257471750558029e3,5.829448912839446e2,-1.601534403978083e3,1.307e22,"Pluto");
		
		Particle[]Planets={Sun,Mercury,Venus,Earth,Moon,Mars,Jupiter,Saturn,Uranus,Neptune,Pluto}; //Creates an array of all the bodies in the system
		
		Scanner s1= new Scanner(System.in);
		int Method;
		double TimeStep;
		double FinalTime;
		int type;
		
		do
		{
			System.out.println("Select Method");
			System.out.println("1. Euler" +"\t"+"2.Euler-Cramer"+"\t"+"3.Verlat");  //Do loop ensures one of methods is selected
			Method=s1.nextInt();
		}
		while(Method!=1 && Method!=2 && Method!=3);
		
	    do
		{
			System.out.println("Enter a positive timestep (in seconds) i.e 1 second");  // Do loop loop ensures the value of the time step is positive integer
			TimeStep= s1.nextDouble();
		}
		while(TimeStep<=0);
		
	    do
		{
			System.out.println("Enter a positive integer for total time (in seconds) you would like to simulate from after the 6th of December 2017 at 00:00 i.e 3.154e7 seconds in a year ");
			FinalTime=s1.nextDouble();        //Do loop ensures total time is greater than Timestep (and advertently greater than 0)
		}
		while(FinalTime<TimeStep);
		
		do
		{
			System.out.println("Which planet are you gathering data for?");
			for(int i=0;i<Planets.length;i++)
			{
				System.out.println(i+". " + Planets[i].GetName());
			}
			type=s1.nextInt();
		}
		while(type>(Planets.length-1));
		
		File file = new File( Planets[type].GetName() + " Position, Method " + Method +".txt");
		FileWriter fw = new FileWriter(file);
		PrintWriter pw= new PrintWriter(fw);
			
	    
		/* For loop updates the velocities and position of the each particle in the particle array
  		*  Using eulers method (Method 1) for a set time (timestep) over a number of seconds (final time)
		*/
		/* For loop updates the velocities and position of the each particle in the particle array
  		*  Using eulers-cramers method (Method 2) for a set time (timestep) over a number of seconds (final time)
		*/
			for(int t=0;t<FinalTime;t+=TimeStep)
			{
				Gravity a= new Gravity();
				PhysicsVector[]Ac= a.GravitySim(Planets);
				
				for(int j=0;j<Planets.length;j++)
				{
					if(Method==1)
					{
						Planets[j].Euler(TimeStep,Ac[j]);
					}
					if(Method==2)
					{
						Planets[j].EulerCramer(TimeStep,Ac[j]);
					}
					
				    if(Method==3)
					{
						Planets[j].Verlat1(TimeStep,Ac[j]);
					}
				}
				
				if(Method==3)
				{
				    Gravity d= new Gravity();
				    PhysicsVector[]Ac1 = d.GravitySim(Planets);
					
					for(int j=0;j<Planets.length;j++)
					{
						Planets[j].Verlat2(TimeStep,Ac1[j],Ac[j]);	
					}
				}

				if(t%86400==0)
				{
				    pw.println(Planets[type].GetPos().returnString());
	            }
			}
							
			if(Method==1)
			{
			    System.out.println("\n"+"Euler Method");
			}
			
			if(Method==2)
			{
				System.out.println("\n"+"EulerCramer Method");
			}
			
			if(Method==3)
			{
				System.out.println("\n"+"Verlat Method");
			}
		
		//Prints out the positions of the particles in the array to the screen
		for(int p=0;p<Planets.length;p++)
			{
				System.out.println("The position of Planet "+ Planets[p].GetName()+ " (in metres) is ");
				Planets[p].GetPos().print();
			}
			
		pw.close();
	}
}
