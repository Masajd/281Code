/**
* Purpose: To create a class file that can create the object "Particle" in which its contents can 
* be used to simulate the movement of a particle in space.
* The "Particle" consists of a 3-dimensional position, velocity, mass and a given name.
* The particle class also peforms most of the numerical methods needed to calculate the new positions and velocities 
* of the particles.
*
* @author Sam Dicker
* @version 1.0
* Date: 09/12/2017
**/

public class Particle
{
	private PhysicsVector velocity= new PhysicsVector();
	private PhysicsVector position= new PhysicsVector();
	private double Mass;
	private String Name;
	
	/**
	* Default constructor that creates a particle with position (0,0,0) metres,
	* velocity (0,0,0) m/s, mass 0.0 kg and Name "Unnamed"
	**/
	public Particle()
	{
		position= new PhysicsVector();
		velocity= new PhysicsVector();
		Mass= 0.0;
		Name="Unnamed";
	}
	
	/**
	* Constructor that takes 8 arguments and sets them to position, velocity, mass and name. 
	* Position and velocity are in terms of i, j and k using the physicsvector class
	* @param x double, i component of position
	* @param y double, j component of position
	* @param z double, k component of position
	* @param vx double, i component of velocity
	* @param vy double, j component of velocity
	* @param vz double, k component of velocity
	* @param M double, Mass of object
	* @param N String, Name of object
	**/
	public Particle(double x,double y,double z,double vx,double vy,double vz,double M, String N)
	{
		position=new PhysicsVector(x,y,z);
		velocity=new PhysicsVector(vx,vy,vz);
		Mass=M;
		Name=N;
	}
	
  	/**
	* The Euler method, calculates the new position of a particle using its velocity in a given direction
	* and the timestep. It then calculates the new velocity of the object using the accleration and the timestep.
	* @param t double, The time step
	* @param ac physicsvector, the acceleration of the particle in terms of i,j, and k
	* @param position physicsvector, the position of the particle in terms of i,j and k
	* @param velocity physicsvector, the velocity of the particle in terms of i, j and k
	**/
	public void Euler(double t,PhysicsVector ac)
	{	
		position.increaseBy(PhysicsVector.scale(t,velocity));        //position.new = position.old + (timestep * velocity.old)
		velocity.increaseBy(PhysicsVector.scale(t,ac));              //velocity.new = velocity.old + (timestep * acceleration)
	}
	
    /**
    * The Euler method, calculates the new velocity of a particle using its acceleration and old velocity in a given direction
	* and the timestep. It then calculates the new position of the object using the velocity, its old position and the timestep.
	* @param t double, The time step
	* @param ac physicsvector, the acceleration of the particle in terms of i,j, and k
	* @param position physicsvector, the position of the particle in terms of i,j and k
	* @param velocity physicsvector, the velocity of the particle in terms of i, j and k
	**/
	public void EulerCramer(double t,PhysicsVector ac)
	{	
		velocity.increaseBy(PhysicsVector.scale(t,ac));              //velocity.new = velocity.old + (timestep * acceleration)
		position.increaseBy(PhysicsVector.scale(t,velocity));        //position.new = position.old + (timestep * velocity.new)
	}
	
	/**
    * The first part of verlats equation, this calculates the new position of a particle using its acceleration, velocity and the time step
	* and its previous position.
	* @param t double, The time step
	* @param ac physicsvector, the acceleration of the particle in terms of i,j, and k
	* @param position physicsvector, the position of the particle in terms of i,j and k
	* @param velocity physicsvector, the velocity of the particle in terms of i, j and k
	**/
    public void Verlat1(double t,PhysicsVector ac)
	{
		position.increaseBy(PhysicsVector.add(PhysicsVector.scale(0.5*t*t,ac),PhysicsVector.scale(t,velocity)));   //position.new = position.old + (velocity.old * t) + (0.5 * acceleration.old * timestep^2)
	}

	/**
    * The second part of verlats equation, this calculates the new velocity of a particle using its new acceleration, its old
	* accleration, its old velocity and the time step
	* and its previous position.
	* @param t double, The time step
	* @param ac physicsvector, the acceleration of the particle in terms of i,j, and k
	* @param ac1 physicsvector, the new acceleration of the particle, calculated from its new position, in terms of i,j and k
	* @param position physicsvector, the position of the particle in terms of i,j and k
	* @param velocity physicsvector, the velocity of the particle in terms of i, j and k
	**/
    public void Verlat2(double t,PhysicsVector ac1,PhysicsVector ac)
    {
		velocity.increaseBy(PhysicsVector.scale(0.5,(PhysicsVector.add(PhysicsVector.scale(t,ac1),PhysicsVector.scale(t,ac)))));   //velocity.new = velocity.old + 0.5 * ((acceleration.new * timestep) + (accleration.old * timestep))
	}
	
	/**
	* Ask the particle constructor to return the position of the particle in terms of i,j and k
	*@param position physicsvector, the position of the particle
	*@returns The position of the particle
	**/
	public PhysicsVector GetPos()
	{
		return position;
	}
	
	/**
	* Ask the particle constructor to return the velocity of the particle in terms of i,j and k
	*@param velocity physicsvector, the velocity of the particle
	*@returns The velocity of the particle
	**/
	public PhysicsVector GetVel()
	{
		return velocity;
	}
	
	/**
	* Ask the particle constructor to return the mass of the particle in terms of kg
	*@param Mass double, the mass of the particle
	*@returns The mass of the particle
	**/
	public double GetMass()
	{
		return Mass;
	}
	
	/**
	* Ask the particle constructor to return the name of the particle in terms of words
	*@param Name, the name of the particle
	*@returns The name of the particle
	**/
	public String GetName()
	{
		return Name;
	}
	
	/**
	* Copies the contents of a particle array to another particle array that can be used elsewhere in the main method
	* without changing the original array
	*@param d Particle array, the array to be copied
	*@param e Particle array, the new array
	*@return Particle array e
	**/
	public static Particle[] CopyArray(Particle[] d)
	{
		Particle[]e = new Particle[d.length];
		
        for(int i=0;i<d.length;i++)
		{
			e[i]= d[i];
		}
		
		return e;
	}
}
	
	