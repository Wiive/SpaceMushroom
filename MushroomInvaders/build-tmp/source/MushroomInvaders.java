import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MushroomInvaders extends PApplet {

Player player;
Enemy[] enemies;
Bullet[] bullets; 
int numberOfEnemies = 30;
float deltaTime;
long time;
int score = 0;
String scoreText = "Current Score: ";


public void setup()
{
	
	frameRate(60);
	textAlign(CENTER);
	ellipseMode(CENTER);

	player = new Player(400,850,30,200,100,150);

	enemies = new Enemy[numberOfEnemies];
	for (int i = 0; i < numberOfEnemies; ++i)
	{
		if(i < 10)
		{
		enemies[i] = new Enemy(50 + (i*50), height/6, 25,1);
		}
		if(i >= 10)
		{			
			enemies[i] = new Enemy(50 + ((i-10)*50), height/8, 25,2);
		}
		if(i >= 20)
		{			
			enemies[i] = new Enemy(50 + ((i-20)*50), height/12, 25,3);
		}
	}

	bullets = new Bullet[30];
}

public void draw()
{
	background(0);
	textSize(28);
	text(scoreText + score, width/2,30);

	long currentTime = millis();
	deltaTime = (currentTime - time);
	deltaTime *= 0.001f;

	player.update();
	player.draw();

	for(int i = 0; i < enemies.length; ++i)
	{
		enemies[i].update();
		enemies[i].draw();
	}

	for(int i = 0; i < bullets.length; ++i)
	{
		if(bullets[i] != null)
		bullets[i].draw();
	}

	time = currentTime;
}
class Bullet extends GameObject
{
  int speed = 260;
  String typeOfBullet;
  

  Bullet(float x, float y, int size, String typeOfBullet) 
  {
   super(x, y, size);
   position.x = x;
   position.y = y;
   this.size = size;
   // "pnemybullet" "playberBullet"
   this.typeOfBullet = typeOfBullet;
   objectColor = color(255);
  }

  public void draw()
  {
    if (typeOfBullet == "playerBullet") 
      {
        position.y = position.y - speed * deltaTime;
      }
    else 
    {
      position.y = position.y + speed * deltaTime;
    }
  //Update bullets
    for (int i = 0; i < bullets.length; i++)
    {
     if (bullets[i] == null)
      {
      //No bullet, skip to the next one.
      continue;
      }
      else
      {
        //update bullet...
        fill(objectColor);
        rect(position.x, position.y, size, size*3);
        if(bullets[i].position.y < 0)
        {
           bullets[i] = null;
        }
      }
    }
  }
}
 public boolean bulletCollision(GameObject one, Bullet two)
 { 
  int maxDistance = one.size/2 + two.size;

  if(abs(one.position.x - two.position.x) > maxDistance || abs(one.position.y - two.position.y) > maxDistance)
  {
    return false;
  }
  else if(dist(one.position.x, one.position.y, two.position.x, two.position.y) > maxDistance)
  {
    return false;
  }
  else
  {
    return true;
  }
}
class Enemy extends GameObject
{
	float horizontalSpeed = 80;
	float verticalSpeed = 50;
	boolean hitScreenWall;
	boolean swapDirection;

	int scoreValue;
	int scoreTier1 = 10;
	int scoreTier2 = 20;
	int scoreTier3 = 30;
	int colorTier1 = color(0, 200, 0);
	int colorTier2 = color(200, 0, 0);
	int colorTier3 = color(0, 0, 200);

	Enemy(float x, float y, int size, int tierType)
	{
		super(x,y,size);
		position.x = x;
		position.y = y;
		this.size = size;

		if (tierType == 1)
		{
			scoreValue = scoreTier1;
			objectColor = colorTier1;
		}
		else if(tierType == 2)
		{
			scoreValue = scoreTier2;
			objectColor = colorTier2;
		}
		else if(tierType == 3)
		{
			scoreValue = scoreTier3;
			objectColor = colorTier3;
		}
	}

	public void update()
	{
		position.x = position.x + horizontalSpeed * deltaTime;
		
		if(position.x < size || position.x > width - size)
		{
			hitScreenWall = true;
			swapDirection = true;
		}
		else hitScreenWall = false;

		if(swapDirection)
		{
			
			//print("Hit wall ");		
			for(int i = 0; i < enemies.length; ++i)
			{
				enemies[i].horizontalSpeed = enemies[i].horizontalSpeed * -1;
				enemies[i].position.y = enemies[i].position.y + verticalSpeed;
				//print(horizontalSpeed + " ");		
			}				
		 	swapDirection = false;
		}	
		checkCollision();
		enemyShoot();
	}

	public void draw()
	{
		fill(objectColor);
		ellipse(position.x, position.y, size, size);
	}


	public void checkCollision()
	{
		for(int i = 0; i < enemies.length; ++i)
		{
			for(int j = 0; j < bullets.length; ++j)
				if(bullets[j] != null)
				{
					if(bulletCollision(enemies[i], bullets[j]))
					{
						if (bullets[j].typeOfBullet == "playerBullet") 
						{
						print("DOM TRÄFFA");
						bullets[j] = null;
						enemies[i].horizontalSpeed = 0; 
						enemies[i].position.y = -1000;
						score = score + enemies[i].scoreValue;	
						}
					}
				}
		}
	}

	public void enemyShoot()
	{  
		for(int i = 0; i < enemies.length; ++i)
		{
			for (int j = 0; j < bullets.length; j++)
     	{
        	if (bullets[j] == null)
        	{
         	 	bullets[j] = new Bullet(enemies[i].position.x, enemies[i].position.y, 10, "enemyBullet");

        		break;
      		}	
    	}
		}
		
	}
}
class GameObject
{
	PVector position;
	PVector velocity;
	int size;
	int objectColor;

	GameObject(float x, float y, int size)
	{
		position = new PVector(x,y);
	}
}
boolean moveLeft;
boolean moveRight;
PVector inputVector;

public void keyPressed()
{
    if (keyCode == LEFT || key == 'a')
      moveLeft = true;
    else if (keyCode == RIGHT || key == 'd')
      moveRight = true;

     //Spawn new bullet it we press "space-bar"
    if (keyPressed && key == 32) 
    {  
      //Find empty spot in array, create list.
      for (int i = 0; i < bullets.length; i++)
      {
        if (bullets[i] == null)
        {
          bullets[i] = new Bullet(player.position.x, player.position.y, 10, "playerBullet");
          //we are done, break/quit the loop.
          break;
        }
      }
    } 
}

public void keyReleased()
{
    if (keyCode == LEFT || key == 'a')
      moveLeft = false;
    else if (keyCode == RIGHT || key == 'd')
      moveRight = false;
}

public PVector input()
{
  inputVector = new PVector();
  
  inputVector.x = 0;
  
  if (moveLeft) {
    inputVector.x -= 1;
  }
  if (moveRight) {
    inputVector.x += 1;
  }

  return inputVector;
}
class Player extends GameObject
{
	float speed = 20;
	float maxSpeed = 30;
	PVector acceleration;
	float quickTurnSpeed = 200;
	float accelerationMultiplier = 2;
	float deaccelerationMultiplier = 1.5f;


	Player(float x, float y, int size,int value1, int value2, int value3)
	{
		super(x,y,size);
		position.x = x;
		position.y = y;
		this.size = size;
		objectColor = color(value1, value2, value3);
		velocity = new PVector();
	}

	public void update()
	{
		acceleration = input();
		acceleration.mult(accelerationMultiplier * speed * deltaTime);

		 //Quick turn
        float dot = velocity.dot(acceleration); 

        if (dot < 0) 
        {
            acceleration.mult(quickTurnSpeed);
        }


		if (acceleration.mag() == 0)
	{
		
  		acceleration.x -= velocity.x * deaccelerationMultiplier * speed * deltaTime;
  	}

		velocity.add(acceleration);
		velocity.limit(maxSpeed);

		PVector move = velocity.copy();
		move.mult(speed * deltaTime);

		position.add(move);

		ScreenWall();
	}

	public void draw()
	{
		fill(objectColor);
		ellipse(position.x, position.y, size, size);
  	}
	
	public void ScreenWall()
	{
		if (position.x < size)
			position.x = size;
		else if (position.x > width - size)
			position.x = width -size;	
	}

}
  public void settings() { 	size(800,1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MushroomInvaders" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
