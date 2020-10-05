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


	void update()
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

				removeBullet(bullets[i]);
			}
		}
	}


	void draw()
	{
		fill(objectColor);
		rect(position.x, position.y, size, size*3);
	}


	void removeBullet(Bullet bullet)
	{
		if(bullet.position.y < 0)
		{
			bullet = null;
		}

		else if(bullet.position.y > height)
		{
			bullet = null;
		}
	}
}