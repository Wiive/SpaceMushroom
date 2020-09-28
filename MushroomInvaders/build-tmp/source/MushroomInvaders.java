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



public void setup()
{
	background(0);
	
	Player player = new Player(400,850,30);
}

public void draw()
{
	player.update();
	player.draw();
}
class GameObject
{
	PVector position;
	PVector velocity;
	int size;
	int col;

	GameObject(float x, float y, int size)
	{
		position = new PVector(x,y);

	}

}
boolean moveLeft;
boolean moveRight;
PVector inputVector;

//Key pressed, set our movement variables to true
public void keyPressed()
{
    if (keyCode == LEFT || key == 'a')
      moveLeft = true;
    else if (keyCode == RIGHT || key == 'd')
      moveRight = true;
}

//When a key is released, we will set our variable to false
public void keyReleased()
{
    if (keyCode == LEFT || key == 'a')
      moveLeft = false;
    else if (keyCode == RIGHT || key == 'd')
      moveRight = false;
}

//Returns a PVector input
public PVector input()
{
  //Reset our input to zero
  inputVector.x = 0;

  //Change our input based on user input
  if (moveLeft) {
    inputVector.x -= 1;
  }
  if (moveRight) {
    inputVector.x += 1;
  }

  //return our result
  return inputVector;
}
class Player extends GameObject
{
	float speed = 3;
	float maxSpeed = 10;
	PVector acceleration;
	float startPos;


	Player(float x, float y, int size)
	{
		super(x,y,size);
		push();
		startPos = y;
		this.size = size;
		position.x = x;
		velocity = new PVector();
		pop();
	}

	public void update()
	{
		acceleration = input();
		velocity.add(acceleration);
		velocity.limit(maxSpeed);

		PVector move = velocity.copy();

		move.mult(speed);

		position.add(move);
	}

	public void draw()
	{
		fill(255);
		ellipse(position.x, startPos, size, size);
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
