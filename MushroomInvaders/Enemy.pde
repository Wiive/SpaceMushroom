class Enemy extends GameObject
{
	float horizontalSpeed = 50;
	float verticalSpeed = 100;
	boolean hitScreenWall;
	boolean moveLeft = false;
	boolean moveRight = true;

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
			hitScreenWall = true;

		if(hitScreenWall)
		{
			print("Hit wall ");		
			for(int i = 0; i < numberOfEnemys; ++i)
			{
				enemies[i].horizontalSpeed = horizontalSpeed * -1;
				enemies[i].position.y = position.y + verticalSpeed;
				print(horizontalSpeed + " ");

			}	
		 	hitScreenWall = false;
		}


	
	}

	void draw()
	{
		fill(objectColor);
		ellipse(position.x, position.y, size, size);
	}


}