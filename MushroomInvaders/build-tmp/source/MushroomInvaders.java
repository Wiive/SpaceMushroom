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
int enemyDistance = 30;
float enemyShootCooldown;
int numberOfEnemies = 100;
Bullet[] bullets;
float deltaTime;
long time;
int score = 0;
String scoreText = "Current Score: ";
String gameOverText = "GAME OVER";
String restartGameText = "Press [R] to restart game";
boolean gameOver = false;
PImage backgroundImage;


public void setup()
{
	
	frameRate(165);
	textAlign(CENTER);
	ellipseMode(CENTER);
	backgroundImage = loadImage("Background.png");

	player = new Player(400,850,60);

	spawnEnemies();
	float enemyShootCooldown = random(3,4);

	bullets = new Bullet[30];
}


public void draw()
{
	textSize(28);
	text(scoreText + score, width/2,30);

	if(gameOver)
	{
		gameOverScreen();
	}

	else
	{
		gameScreen();
	}	
}


public void spawnEnemies()
{
	enemies = new Enemy[numberOfEnemies];

	for (int i = 0; i < numberOfEnemies; ++i)
	{
		if(i < 20)
		{
		enemies[i] = new Enemy(50 + (i*enemyDistance), height/4, 25,1);
		}

		if(i >= 20)
		{
		enemies[i] = new Enemy(50 + ((i-20)*enemyDistance), height/4.8f, 25,1);
		}

		if(i >= 40)
		{			
			enemies[i] = new Enemy(50 + ((i-40)*enemyDistance), height/6, 25,2);
		}

		if(i >= 60)
		{			
			enemies[i] = new Enemy(50 + ((i-60)*enemyDistance), height/8, 25,2);
		}

		if(i >= 80)
		{			
			enemies[i] = new Enemy(50 + ((i-80)*enemyDistance), height/12, 25,3);
		}
	}
}


public void gameOverScreen()
{
	background(0,0,0,100);
	textSize(50);
	fill(255);
	text(gameOverText, width/2, height/2);
	textSize(20);
	text(restartGameText, width/2, height/2 + 40);
}


public void gameScreen()
{
	background(backgroundImage);
	fill(255);
	textSize(28);
	text(scoreText + score, width/2,30);

	long currentTime = millis();
	deltaTime = (currentTime - time);
	deltaTime *= 0.001f;

	enemyShootCooldown = enemyShootCooldown - deltaTime;
	shootCooldown = shootCooldown - deltaTime;

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
		bullets[i].update();
	}

	if(allEnemiesDead(score))
	{
		restartGame();
	}

	time = currentTime;
}


public boolean allEnemiesDead(int score)
{
	if(score == 1800)
	{
		return true;
	}
	
	else
	{
		return false;
	}
}


public void restartGame()
{
	player = new Player(400,850,60);

	spawnEnemies();

	bullets = new Bullet[30];

	score = 0;
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

		//When creating a bullet - use the strings playerBullet or enemyBullet
		this.typeOfBullet = typeOfBullet;
		objectColor = color(255);
	}


	public void update()
	{
		if (typeOfBullet == "playerBullet") 
		{
			position.y = position.y - speed * deltaTime;
		}

		else 
		{
			position.y = position.y + speed * deltaTime;
		}

		
		for (int i = 0; i < bullets.length; i++)
		{
		 	if (bullets[i] == null)
			{
				continue;
			}

			else
			{
				draw();

				if(bullets[i].position.y < 0)
					{
						bullets[i] = null;
					}

				else if(bullets[i].position.y > height)
					{
						bullets[i] = null;
					}
			}
		}
	}


	public void draw()
	{
		fill(objectColor);
		rect(position.x, position.y, size, size*3);
	}
}
 public boolean bulletCollision(GameObject gameObject, Bullet bullet)
{ 
	int maxDistance = gameObject.size/2 + bullet.size;

	if(abs(gameObject.position.x - bullet.position.x) > maxDistance
		|| abs(gameObject.position.y - bullet.position.y) > maxDistance)
	{
		return false;
	}

	else if(dist(gameObject.position.x,	gameObject.position.y,
			bullet.position.x, bullet.position.y) > maxDistance)
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
	float horizontalSpeed = 50;
	float verticalSpeed = 50;
	boolean hitScreenWall;
	boolean swapDirection;
	int shootingEnemy;
	int scoreValue;	
	int scoreTier1 = 10;
	int scoreTier2 = 20;
	int scoreTier3 = 30;
	PImage enemyImage;
	PImage enemyImageTier1 = loadImage("Svamp1.png");
	PImage enemyImageTier2 = loadImage("Svamp2.png");
	PImage enemyImageTier3 = loadImage("Svamp3.png");


	Enemy(float x, float y, int size, int tierType)
	{
		super(x,y,size);
		position.x = x;
		position.y = y;
		this.size = size;

		if (tierType == 1)
		{
			scoreValue = scoreTier1;
			enemyImage = enemyImageTier1;
		}

		else if(tierType == 2)
		{
			scoreValue = scoreTier2;
			enemyImage = enemyImageTier2;
		}

		else if(tierType == 3)
		{
			scoreValue = scoreTier3;
			enemyImage = enemyImageTier3;
		}
	}

	public void update()
	{
		if(position.x + horizontalSpeed * deltaTime < size
			|| position.x + horizontalSpeed * deltaTime > width - size)
		{
			hitScreenWall = true;
			swapDirection = true;
		}

		else hitScreenWall = false;

		if(swapDirection)
		{					
			for(int i = 0; i < enemies.length; ++i)
			{
				enemies[i].horizontalSpeed = enemies[i].horizontalSpeed * -1;	
			}	

			for (int i = 0; i < enemies.length; ++i)
			{
				enemies[i].position.y = enemies[i].position.y + verticalSpeed;				
			}			
			swapDirection = false;
		}	

		position.x = position.x + horizontalSpeed * deltaTime;

		checkCollision();

		if(enemyShootCooldown <= 0)
		{
			enemyShootCooldown = random(1,2);
			shootingEnemy = PApplet.parseInt(random(enemies.length));
			enemyShoot();
		}
	}


	public void draw()
	{
		imageMode(CENTER);
		image(enemyImage, position.x, position.y, size, size);
	}


	public void checkCollision()
	{
		for(int i = 0; i < enemies.length; ++i)
		{	
			if(enemies[i].position.y + player.size >= player.position.y)
			{
				gameOver = true;
			}
			for(int j = 0; j < bullets.length; ++j)
			{
				if(bullets[j] != null)
				{
					if(bulletCollision(enemies[i], bullets[j]))
					{
						if (bullets[j].typeOfBullet == "playerBullet") 
						{
							bullets[j] = null;
							enemies[i].horizontalSpeed = 0; 
							enemies[i].position.y = -1000;
							score = score + enemies[i].scoreValue;	
						}
					}
				}	
			}
		}
	}


	public void enemyShoot()
	{  
		for (int i = 0; i < bullets.length; i++)
		{
			if (bullets[i] == null)
			{
				bullets[i] = new Bullet(enemies[shootingEnemy].position.x,
										enemies[shootingEnemy].position.y, 5, "enemyBullet");

				break;
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
PVector inputVector = new PVector();
boolean resetButtonDown = false;
float shootCooldown = 0.33f;


public void keyPressed()
{
	if (keyCode == LEFT || key == 'a')
	{
		moveLeft = true;
	}

	else if (keyCode == RIGHT || key == 'd')
	{
		moveRight = true;
	}


	if(shootCooldown <= 0)
	{
		if (keyPressed && key == 32) 
		{  
			for (int i = 0; i < bullets.length; i++)
			{
				if (bullets[i] == null)
				{
					bullets[i] = new Bullet(player.position.x, player.position.y, 5, "playerBullet");
					shootCooldown = 0.33f;

					break;
				}
			}
		} 
	}
 

	if(gameOver)
	{
		if (resetButtonDown == false && key == 'r')
		{
			resetButtonDown = true;

			restartGame();
		}
	}
}


public void keyReleased()
{
	if (keyCode == LEFT || key == 'a')
	{
		moveLeft = false;
	}

	else if (keyCode == RIGHT || key == 'd')
	{
		moveRight = false;
	}

	if (key == 'r') 
	{
		resetButtonDown = false;
		gameOver = false;
	}
}


public PVector input()
{
	inputVector.x = 0;
	
	if (moveLeft)
	{
		inputVector.x -= 1;
	}
	if (moveRight)
	{
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
	PImage playerImage = loadImage("Player.png");


	Player(float x, float y, int size)
	{
		super(x,y,size);
		position.x = x;
		position.y = y;
		this.size = size;
		
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

		checkCollision();
	}


	public void draw()
	{
		imageMode(CENTER);
		image(playerImage, position.x, position.y,size, size);
	}
	

	public void ScreenWall()
	{
		if (position.x < size)
		{
			position.x = size;
		}

		else if (position.x > width - size)
		{
			position.x = width -size;	
		}
	}


	public void checkCollision()
	{
		for(int i = 0; i < bullets.length; ++i)
		{
			if(bullets[i] != null)
			{
				if(bulletCollision(player, bullets[i]))
				{
					if (bullets[i].typeOfBullet == "enemyBullet") 
					{					
						bullets[i] = null;
						gameOver = true;
					}
				}
			}
		}
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
