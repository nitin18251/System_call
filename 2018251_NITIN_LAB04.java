//package lab3;

import java.util.*;

abstract class hero
{	private float attack;
	private float health;
	private float defence;
	private int XP;
	private  int level=1;
	private int c_special=0;
	private int flag=0;
	int sidekickcount=0;
	ArrayList<sidekicks> sidekicklist=new ArrayList<sidekicks>();
	
	public int allotsidekick()
	{	
		int location=0;
		sidekicks temp=sidekicklist.get(0);
		for(int i=0;i<sidekicklist.size();i++)
		{
			if (temp.comapreTo(sidekicklist.get(i))==-11)
			{
				temp=sidekicklist.get(i);
				location=i;
			}
			i++;
		}
		
		return location;
	}
	
	public void attack(villian v)
	{
		v.setHealth(v.getHealth() - this.getAttack());
	}
	
	
	
	public void defence(villian v)
	{	int z=v.attack(this);
		System.out.println(" my defence "+this.getDefence());
		if(this.getDefence()<z)
			this.setHealth(this.getHealth()-v.getAttack()+this.getDefence());
		
	}
	
	public abstract void special(villian v);
	public abstract int checkspecial();
	
	public int getLevel()
	{	
		if (getXP()>=20 && getXP()<=40)
		{level =2;System.out.println("level : "+ level );return 2;}
		if (getXP()>40 && getXP()<=60)
			{level=3;System.out.println("level : "+ level );
			return 3;}
		if (getXP()>60)
			{level=4;System.out.println("level : "+ level );
			return 4;}
		else 
			return 1;
	}
	
	public int canspecial()
	{
		if (c_special==4)
			{
			c_special=0;
			setFlag(1);
			return 1;
			}
		else 
		{	c_special++;
			return 0;
		}
	}

	public int getAttack() {
		return (int) attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return (int) defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getHealth() {
		return (int) health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getXP() {
		return XP;
	}

	public void setXP(int xP) {
		XP = xP;
	}
}

final class warrior extends hero
{	int check=-1;
	warrior()
	{	
		this.setAttack(10);
		this.setDefence(3);
		this.setHealth(100);
		this.setXP(0);
		
	}
	@Override
	public void special(villian v)
	{
		this.setAttack(this.getAttack() + 5);
		this.setDefence(this.getDefence() + 5);
	}
	
	public int checkspecial()
	{if (getFlag()==4)
		{this.setAttack(this.getAttack() - 5);this.setDefence(this.getDefence() - 5);setFlag(0);}
	setFlag(getFlag() + 1);
	return 0;
	}
	

}

final class mage extends hero
{	int check=-1;
	mage()
	{
		this.setAttack(5);
		this.setDefence(5);
		this.setHealth(100);
		this.setXP(0);
	}
	
	public void special(villian v)
	{
		v.setHealth((int) (v.getHealth()*0.95));
	}
	
	public int checkspecial()
	{
		if (getFlag()==4)
		{setFlag(0);return 0;}
	setFlag(getFlag() + 1);
	return 1;
	}
	
}

final class thief extends hero
{
	thief()
	{
		this.setAttack(6);
		this.setDefence(4);
		this.setHealth(100);
		this.setXP(0);
	}
	
	public void special(villian v)
	{
		this.setHealth((int) (this.getHealth()+v.getHealth()*(0.3)));
		v.setHealth((int) (v.getHealth()*0.7));
	}
	
	public int checkspecial()
	{
		return 0;
	}
}

final class healer extends hero
{	int check =-1;
	healer()
	{
		this.setAttack(4);
		this.setDefence(8);
		this.setHealth(100);
		this.setXP(0);
	}
	
	public void special(villian v)
	{
		this.setHealth((int) (this.getHealth()*1.05));
	}
	
	public int checkspecial()
	{
		if (check==-1)
			return 0;
		else if (check==3)
		{	
			check=0;
			return 0;
		}
		else
		{
			check++;
			return 1;
		}
	}
	
}

class villian
{
	private int attack;
	private int health;
	public int getattack()
	{	int n= (int) this.getHealth()/4;
		float mean =(n+1)/2;
		float st_dev =(float) Math.sqrt((n*n -1)/12);
		Random rand=new Random();
		int q=(int) ((int)rand.nextGaussian() *st_dev + mean);
		System.out.println("attack "+ q);
		return  q;
	}
	
	public int attack(hero h)
	{	
		int a=getattack();
		h.setHealth(h.getHealth()-a);
		return a;	
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	
}

final class goblins extends villian
{
	goblins()
	{
		this.setHealth(100);
	}
}

final class zombies extends villian
{
	zombies()
	{
		this.setHealth(150);
	}
}

final class fiends extends villian
{
	fiends()
	{
		this.setHealth(200);
	}
}

final class lionfangs extends villian
{
	lionfangs()
	{
		this.setHealth(250);
	}
	
	@Override
	public int attack(hero h)
	{
		Random rand = new Random();
		int x=rand.nextInt(10);
		
		if (x==0)
		{
			h.setHealth(h.getHealth()/2);
			return h.getHealth()/2;
		}
		else
		{	int damage=-1;
			while(damage<0)
			{
				damage=(int)rand.nextGaussian()+1;
			}
			h.setHealth(h.getHealth() - damage*(this.getHealth())/4);
			return damage*(this.getHealth())/4;
		}	
		
	}
}

class User
{	static int count=0;
	final private String name;
	final private hero Hero;
	int arr[]=new int[400];
	
	User(String name,hero Hero)
	{
		this.name=name;
		this.Hero=Hero;
	}
	
	int allotmonster(int position)
	{	if (arr[position]!=0)
			return arr[position];
		Random rand = new Random();
		int x=rand.nextInt(3)+1;
		arr[position]=x;
		return x;
	}
	
	int startgame()
	{
		Scanner in=new Scanner(System.in);
		int loc=0;
		int sidekickactivated=0;
		int lion=0;
		List T;
		sidekicks temp = null;
		while(lion!=4)
		{	
		
		if (loc!=0)
		{
		System.out.println("if you want to buy sidekicks press 1 or to use XP to increase level press 2   ");
		int side=in.nextInt();
		if (side==1)
			{					
				System.out.println("your XP "+ this.Hero.getXP());
				if (this.Hero.getXP()<5) { System.out.println("insufficient XP");  break;}
				this.Hero.sidekickcount++;
				System.out.println("1) minions    2) knight ");
				int j=in.nextInt();
				if (j==1)
					{
					temp=new  minions ();
					System.out.print("XP to give ");
					int xp=in.nextInt();
					if (xp>this.Hero.getXP())
						{
							System.out.println("insufficeint xp ,making sidekick using 5 XP");
							this.Hero.setXP(this.Hero.getXP()-5);
						}
					else 
						{
						temp.setAttack(temp.getAttack() + (float) ((xp-5)*0.5));
						this.Hero.setXP(this.Hero.getXP()-xp);
						this.Hero.sidekicklist.add(temp);						
						}
					}
				else if (j==2)
					{
					temp=new knight ();
					System.out.print("XP to give ");
					int xp=in.nextInt();
					if (xp>this.Hero.getXP())
						{
							System.out.println("insufficeint xp ,making sidekick using 8 XP");
							this.Hero.setXP(this.Hero.getXP()-8);
						}
					else 
						{
						temp.setAttack(temp.getAttack() + (float) ((xp-8)*0.5));
						System.out.print(temp.getAttack());
						this.Hero.setXP(this.Hero.getXP()-xp);
						this.Hero.sidekicklist.add(temp);						
						}
					
					}

			}
		}
		this.Hero.setHealth(50+this.Hero.getLevel()*50);
		this.Hero.setAttack(this.Hero.getAttack()+this.Hero.getLevel()-1);
		this.Hero.setDefence(this.Hero.getDefence()+this.Hero.getLevel()-1);
		System.out.println("CHOOSE LOCATION");
		System.out.println("Location "+(3*loc+1));
		System.out.println("Location "+(3*loc+2));
		System.out.println("Location "+(3*loc+3));
		if(loc!= 0)
		{
			System.out.println("press " +loc+ " to reenter same level");
		}
		System.out.println("to exit press -1");
		
		int losehealth=0;
		int s=in.nextInt();
		if (s==-1)
		{	System.out.println("exiting");
			return -1;
		}
		
		if (s==loc)
			lion--;
		loc=s;
		int mon=this.allotmonster(s);
		
		villian v = null;
		if (lion==4)
		{	
			System.out.println("You are fighting lionfang");
			v=new lionfangs();
		}
		else if (mon==1)
		{	
			System.out.println("You are fighting Goblin");
			v=new goblins();
		}
		else if (mon==2)
		{System.out.println("You are fighting zombies");
			v=new zombies();
		}
		else if (mon==3)
		{System.out.println("You are fighting fiends");
			v=new fiends();
		}
		
		
		loc=s;
		int sidekicknumber=0;
		
		int useage=0;
		if (this.Hero.sidekickcount>0)
		{
			System.out.println("Use sidekick press 1 to continue press 2");
			sidekickactivated=in.nextInt();
			if (sidekickactivated==1)
				{
				sidekicknumber=this.Hero.allotsidekick();
				this.Hero.sidekicklist.get(sidekicknumber).setAttack(this.Hero.sidekicklist.get(sidekicknumber).getAttack() + this.Hero.sidekicklist.get(sidekicknumber).getXP()*0.5);
				useage=this.Hero.sidekicklist.get(sidekicknumber).special(this.Hero,v);
				this.Hero.sidekicklist.get(sidekicknumber).setHealth(100);
				}
		}
		sidekicks k[]=new sidekicks[3];
		
		if (useage==1)
		{
			k[0]=this.Hero.sidekicklist.get(sidekicknumber).clone();
			k[1]=this.Hero.sidekicklist.get(sidekicknumber).clone();
			k[2]=this.Hero.sidekicklist.get(sidekicknumber).clone();
			
		}
		
		
		
		//System.out.println(this.Hero.getHealth());
		int q=0;
		int v_health=0;
		while(this.Hero.getHealth()>0 && v.getHealth()>0)
		{
		v_health=0;
		System.out.println("1) Attack");
		System.out.println("2) Defence");
		if( this.Hero.canspecial()==1 )
			System.out.println("3) Special move");
		int choice =in.nextInt();
		
		if (choice==1)
			{
			if (q>0 && q<4)
				{this.Hero.checkspecial();q++;}
				this.Hero.attack(v);
				losehealth=v.attack(this.Hero);
			System.out.println("your health " +this.Hero.getHealth()+" enemy's health "+v.getHealth());
			}
		else if (choice==2)
		{	if (q>0 && q<4)
				{this.Hero.checkspecial();q++; System.out.println("q is "+q);}
			    this.Hero.defence(v);
			System.out.println("your health " +this.Hero.getHealth()+" enemy's health "+v.getHealth());
		}
		
		else if (choice==3)
			{this.Hero.special(v);v.attack(this.Hero);q=1;this.Hero.checkspecial();System.out.println("your health " +this.Hero.getHealth()+" enemy's health "+v.getHealth());}
		
		
		if (v.getHealth()<=0 )
			v_health=1;
		
		if (sidekickactivated==1 && this.Hero.sidekicklist.get(sidekicknumber).getHealth()>0 && v_health==0 )
			{
			this.Hero.sidekicklist.get(sidekicknumber).losehealth(losehealth);
			this.Hero.sidekicklist.get(sidekicknumber).doattack(v);
			}
		
		if (useage==1 && this.Hero.sidekicklist.get(sidekicknumber).getHealth()>0 && v_health==0)
			{
			k[0].doattack(v);
			k[2].doattack(v);
			k[1].doattack(v);
			
			k[0].losehealth(losehealth);
			k[1].losehealth(losehealth);
			k[2].losehealth(losehealth);
			
			}
		if (sidekickactivated==1 && v_health==0)
			System.out.println("sidekick's health " +this.Hero.sidekicklist.get(sidekicknumber).getHealth()+" enemy's health "+v.getHealth());
		
		if (sidekickactivated==1 && v_health==0 && v.getHealth()<=0)
		{
			System.out.println("sidekick killed villian");
			this.Hero.sidekicklist.get(sidekicknumber).setXP(this.Hero.sidekicklist.get(sidekicknumber).getXP() + this.Hero.getLevel()*2);
		}
		
		if (v.getHealth()<=0)
		{	
			this.Hero.setXP(this.Hero.getXP()+this.Hero.getLevel()*20);
			System.out.println("XP is "+ this.Hero.getXP());
			this.Hero.getLevel();
			System.out.println(lion);
			lion++;
		}
		
		if (this.Hero.getHealth()<=0)
			return 0;
		}

		System.out.println("stage is "+ (lion +1));
	
		this.Hero.setHealth(100);
		if (sidekickactivated==1)
			this.Hero.sidekicklist.get(sidekicknumber).setHealth(100);
		if (sidekickactivated!=0)
			this.Hero.sidekicklist.get(sidekicknumber).unactivatespecial(this.Hero, v);
	}
		return 0;
	}

	public String getName() {
		return name;
	}
	
}

interface Comparable<sidekicks>
{
	public int comapreTo(sidekicks T);
}

interface cloneable
{
	
}

abstract class sidekicks implements Comparable<sidekicks>,cloneable 
{
	private float XP;
	private float health=100;
	private float attack;
	public abstract int special(hero h,villian v);
	public abstract void unactivatespecial(hero h ,villian v);
	public void doattack(villian v)
	{	//System.out.print(this.attack);
		v.setHealth((int) (v.getHealth()-this.getAttack()));
	}
	
	public int compareTo(sidekicks T)
	{
		if (this.getXP()>T.getXP())
			return 1;
		else if (this.getXP()<T.getXP())
			return -1;
		else
			return 0;
	}
	
	public minions clone()
	{
		minions temp = new minions();
		temp.setAttack(this.getAttack());
		temp.setXP(this.getXP());
		temp.setHealth(this.getHealth());
		return temp;
	}
	
	public void losehealth(int h)
	{	
		setHealth(getHealth() - h);
	}
	public float getHealth() {
		return health;
	}
	public void setHealth(float health) {
		this.health = health;
	}
	public float getAttack() {
		return attack;
	}
	public void setAttack(double d) {
		this.attack = (float) d;
	}
	public float getXP() {
		return XP;
	}
	public void setXP(float xP) {
		XP = xP;
	}
	
	
}




class minions extends sidekicks
{	int cloneused=0;
	minions()
	{
		setAttack(1);
	}

	public int special(hero h,villian v)
	{	Scanner in =new Scanner(System.in);
		if (cloneused==0)
			{
			System.out.println("you want to create clone press 1");
			if (in.nextInt()==1)
			{
				cloneused=1;
				return 1;}
			} 
		return 0;
	}

	@Override
	public int comapreTo(sidekicks T) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void unactivatespecial(hero h, villian v) {
		// TODO Auto-generated method stub
		
	}


}

class knight extends sidekicks
{
	knight()
	{
		setAttack(2);
	}
	
	public int special(hero h,villian v)
	{
		if (v instanceof zombies)
		{
			h.setDefence(h.getDefence()+5);
		}
		return 0;
	}

	@Override
	public int comapreTo(sidekicks T) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void unactivatespecial(hero h ,villian v) {
		// TODO Auto-generated method stub
		if (v instanceof zombies)
		{
			h.setDefence(h.getDefence()-5);
		}
	}


	

}



class gameemulator
{	static ArrayList<User> player=new ArrayList<User>();
	void getuser()
	{	
		Scanner in=new Scanner(System.in);
		System.out.print("enter name ");
		String name=in.next();
		System.out.print("enter hero ");
		String h=in.next();
		hero hero=null;
		if (h.contentEquals("warrior"))
			hero =new warrior();
		else if (h.contentEquals("mage"))
			hero =new mage();
		else if (h.contentEquals("thief"))
			hero =new thief();
		else if (h.contentEquals("healer"))
			hero =new healer();
		User.count++;
		User e=new User(name,hero);
		player.add(e);

	}
	
	
	User getuser(String s)
	{	int i=0;
		while(player.get(i)!=null)
		{
			if (player.get(i).getName().contentEquals(s))
				{return player.get(i);}
			i++;
		}
		User u = null;
		return u;
	}
	void getgame()
	{
		Scanner in=new Scanner(System.in);
		System.out.print("Enter name  ");
		String user=in.next();
		User x=getuser(user);

		while(true)
		{
		int z=x.startgame();
		
		if (z==1)
		{
			System.out.print("you won");
			break;
		}
		if (z==0)
		{
			System.out.println("you lost");
			break;
		}
		
		if (z==-1)
			break;
		}
			
	
	}
	
	
}



public class lab3 {

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		gameemulator gamer=new gameemulator();
		while (true)
		{
		System.out.println("1) enter user 2) existing user ");
		int choice =in.nextInt();
		if (choice ==1)
			gamer.getuser();
		if (choice ==2)
			{
			gamer.getgame();
			}
		if (choice ==3)
			System.exit(0);
		

		}

}
}
