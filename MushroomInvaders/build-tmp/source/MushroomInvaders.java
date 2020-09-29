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
float deltaTime;
long time;

public void setup()
{
	
	frameRate(60);

	player = new Player(400,850,30,200,100,150);
}

public void draw()
{
	background(0);

	long currentTime = millis();
	deltaTime = (currentTime - time);
	deltaTime *= 0.001f;

	player.update();
	player.draw();

	time = currentTime;
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
	float speed = 30;
	float maxSpeed = 50;
	PVector acceleration;
	float quickTurnSpeed = 100;
	float accelerationMultiplier = 0.85f;
	float deaccelerationMultiplier = 0.6f;


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
