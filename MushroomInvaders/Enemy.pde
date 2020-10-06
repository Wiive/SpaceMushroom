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

	void update()
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
			shootingEnemy = int(random(enemies.length));
			enemyShoot();
		}
	}


	void draw()
	{
		imageMode(CENTER);
		image(enemyImage, position.x, position.y, size, size);
	}


	void checkCollision()
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


	void enemyShoot()
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
