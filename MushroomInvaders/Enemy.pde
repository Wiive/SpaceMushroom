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
	color colorTier1 = color(0, 200, 0);
	color colorTier2 = color(200, 0, 0);
	color colorTier3 = color(0, 0, 200);

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

	void update()
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
	}

	void draw()
	{
		fill(objectColor);
		ellipse(position.x, position.y, size, size);
	}


	void checkCollision()
	{
		for(int i = 0; i < enemies.length; ++i)
		{
			for(int j = 0; j < bullets.length; ++j)
				if(bullets[j] != null)
				{
					if(bulletCollision(enemies[i], bullets[j]))
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