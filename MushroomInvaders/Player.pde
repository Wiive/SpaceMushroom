class Player extends GameObject
{
	float speed = 20;
	float maxSpeed = 30;
	PVector acceleration;
	float quickTurnSpeed = 200;
	float accelerationMultiplier = 2;
	float deaccelerationMultiplier = 1.5;


	Player(float x, float y, int size,int value1, int value2, int value3)
	{
		super(x,y,size);
		position.x = x;
		position.y = y;
		this.size = size;
		objectColor = color(value1, value2, value3);
		velocity = new PVector();
	}

	void update()
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

	void draw()
	{
		fill(objectColor);
		ellipse(position.x, position.y, size, size);
  	}
	
	void ScreenWall()
	{
		if (position.x < size)
			position.x = size;
		else if (position.x > width - size)
			position.x = width -size;	
	}


	void checkCollision()
	{
		
			for(int i = 0; i < bullets.length; ++i)
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