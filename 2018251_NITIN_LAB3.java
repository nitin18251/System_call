package lab3;

import java.util.ArrayList;
import java.util.*;

abstract class hero
{	private int attack;
	private int health;
	private int defence;
	private int XP;
	private  int level=1;
	private int c_special=0;
	private int flag=0;
	
	
	public void attack(villian v)
	{
		v.health-=this.getAttack();
	}
	
	public void defence(villian v)
	{	int z=v.attack(this);
		System.out.println(" my defence "+this.getDefence());
		if(this.getDefence()<z)
			this.setHealth(this.getHealth()-v.attack+this.getDefence());
		
	}
	
	public abstract void special(villian v);
	public abstract int checkspecial();
	
	public int getLevel()
	{	
		if (getXP()>20 && getXP()<=40)
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
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
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
		return health;
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
		v.health=(int) (v.health*0.95);
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
		this.setHealth((int) (this.getHealth()+v.health*(0.3)));
		v.health=(int) (v.health*0.7);
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
	int attack;
	int health;
	public int getattack()
	{	int n= (int) this.health/4;
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
}

final class goblins extends villian
{
	goblins()
	{
		this.health=100;
	}
}

final class zombies extends villian
{
	zombies()
	{
		this.health=150;
	}
}

final class fiends extends villian
{
	fiends()
	{
		this.health=200;
	}
}

final class lionfangs extends villian
{
	lionfangs()
	{
		this.health=250;
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
			h.setHealth(h.getHealth() - damage*(this.health)/4);
			return damage*(this.health)/4;
		}	
		
	}
}

class User
{	static int count=0;
	final String name;
	final hero Hero;
	int arr[]=new int[200];
	
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
		int lion=0;
		
		while(lion!=4)
		{	
		//System.out.println(this.Hero.getLevel());
		this.Hero.setHealth(50+this.Hero.getLevel()*50);
		this.Hero.setAttack(this.Hero.getAttack()+this.Hero.getLevel()-1);
		this.Hero.setDefence(this.Hero.getDefence()+this.Hero.getLevel()-1);
		System.out.println("CHOOSE LOCATION");
		System.out.println("Location "+(3*loc+1));
		System.out.println("Location "+(3*loc+2));
		System.out.println("Location "+(3*loc+3));
		if(loc!= 0)
			System.out.println("press " +loc+ " to reenter same level");
		System.out.println("to exit press -1");
		
		int s=in.nextInt();
		if (s==-1)
		{	System.out.println("exiting");
			return -1;
		}
		loc=s;
		int mon=this.allotmonster(s);
		
		villian v = null;
		if (mon==1)
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
		
		//System.out.println(this.Hero.getHealth());
		int q=0;
		while(this.Hero.getHealth()>0 && v.health>0)
		{
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
				v.attack(this.Hero);
			System.out.println("your health " +this.Hero.getHealth()+" enemy's health "+v.health);
			}
		else if (choice==2)
		{	if (q>0 && q<4)
				{this.Hero.checkspecial();q++; System.out.println("q is "+q);}
			this.Hero.defence(v);
			System.out.println("your health " +this.Hero.getHealth()+" enemy's health "+v.health);
		}
		
		else if (choice==3)
			{this.Hero.special(v);v.attack(this.Hero);q=1;this.Hero.checkspecial();System.out.println("your health " +this.Hero.getHealth()+" enemy's health "+v.health);}
		
		if (v.health<=0)
		{	
			this.Hero.setXP(this.Hero.getXP()+this.Hero.getLevel()*20);
			System.out.println("XP is "+ this.Hero.getXP());
			this.Hero.getLevel();
			lion++;
		}
		
		if (this.Hero.getHealth()<=0)
			return 0;
		}
		

		
		System.out.println("lion is "+ lion );
		
		if (lion==4)
		{	lionfangs l= new lionfangs();
			System.out.println("FIGHTING LIONFANG");
			while(this.Hero.getHealth()>=0 && l.health>=0)
		{
			
			System.out.println("1) Attack");
			System.out.println("2) Defence");
			if( this.Hero.canspecial()==1 )
				System.out.println("3) Special move");
			int choice =in.nextInt();
			
			if (choice==1)
				{
				this.Hero.attack(l);
				l.attack(this.Hero);
				System.out.println("your health " +this.Hero.getHealth()+" enemy's health "+l.health);
				}
			else if (choice==2)
			{
				this.Hero.defence(l);
				System.out.println("your health " +this.Hero.getHealth()+" enemy's health "+l.health);
			}
			
			else if (choice==3)
			{this.Hero.special(v);l.attack(this.Hero);q=1;this.Hero.checkspecial();System.out.println("your health " +this.Hero.getHealth()+" enemy's health "+l.health);}
			
			}
			if (l.health<0)
				return 1;
			if (l.health>0) return 0;
		}
		this.Hero.setHealth(100);
	}
		return 0;
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
			if (player.get(i).name.contentEquals(s))
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
