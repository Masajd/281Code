/**
* Purpose: To create a class file that can create the object "Gravity" in which its contents contains its position and mass.
* The gravity class also calculates the acceleration of a particle towards another particle of mass M at distance r from the 
* other due to gravity
*
* @author Sam Dicker
* @version 1.2
* Date: 09/12/2017
**/
import java.lang.Math;

public class Gravity
{
	private double G=6.67408e-11; //The gravitational constant G in m^3 kg^-1 s^-2
	private PhysicsVector position= new PhysicsVector();
	private double Mass;

	/**
	* Default constructor that creates a gravity with position(0,0,0) metres,
	* and mass 0.0 kg
	**/
	public Gravity()
	{
		position= new PhysicsVector();
		double Mass= 0.0;
	}
	
	/**
	* Gravity constructor that takes the 4 arguments and assigns them to a position and mass
	* @param x double, i component of position
	* @param y double, j component of position
	* @param z double, k component of position
	* @param M double, Mass of object
	**/
	public Gravity(double x,double y,double z, double M)
	{
		position=  new PhysicsVector(x,y,z);
		double Mass= M;
	}
	
	/**
	* Calculates the acceleration due to gravity of a particle due to another planet of mass M at a distance r from the other particle.
	* It does this for all particles in the system and calculates the sum of the accelerations. This acceleration is then assigned to itself in
	* a Planet accelerations array ,and return to be used by other methods.
	* @param a Particle array, contains the position, velocity and mass of a group of particles
	* @param PlanetAccelerations PhysicsVector array, contains all the total accelerations of the particles
	* @param j integer, counter variable
	* @param i integer, counter variable
	* @param acc PhysicsVector, the total sum of the all the accelerations of a particle
	* @param ac PhysicsVector, the vector acceleration of a particle due to another particle
	* @param r PhysicsVector, the distance between 2 particles
	* @param g double, the magnitude of a particle due to another particle
	* @return PlanetAccelerations, the total accelerations of all particles in an array
	**/
	public PhysicsVector[] GravitySim(Particle [] a)
	{

		PhysicsVector[]PlanetAccelerations= new PhysicsVector[a.length];   //Creates an acceleration array of the inputted array length
			
			for(int j=0;j<a.length;j++)                                    // for loop to determine the planet you are calculating the accleration for
			{
				PhysicsVector acc= new PhysicsVector(0,0,0);                 //sets the values of acc and ac to (0,0,0)
				PhysicsVector ac= new PhysicsVector(0,0,0);
				
				for(int i=0;i<a.length;i++)                                  //for loop to make sure it calculates the acceleration of all planets in the system
				{					
 			        if(j!=i)                                                 // So the code doesnt calculate the accleration of a planet due to itself
					{
						PhysicsVector r=new PhysicsVector(PhysicsVector.subtract(a[j].GetPos(),a[i].GetPos()));      //calculates the distance between planet j and planet i
						double g=-(G*a[i].GetMass())/(r.magnitude()*r.magnitude());                                  //calculates the magnitude of the acceleration between the 2 planets
						
						ac= r.getUnitVector();            //Calculates the unit vector of return
						ac.scale(g);                      //Scales the magnitude with the unit vector giving a vector acceleration
						
						acc.increaseBy(ac);				//adds acceleration due to one planet to the total acceleration
					}	
				}
				
				PlanetAccelerations[j]=acc;            //sets the total accleration of planet j to accleration j in an array
			}
			
		return PlanetAccelerations;                   //returns the planet acceleration array
	}
}
		
		