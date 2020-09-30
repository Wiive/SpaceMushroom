Player player;
Enemy[] enemies;
Bullet[] bullets; 
int numberOfEnemies = 30;
float deltaTime;
long time;
float enemyShootCooldown;
int score = 0;
String scoreText = "Current Score: ";


void setup()
{
	size(800,1000);
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
	float enemyShootCooldown = random(3,4);
}

void draw()
{
	background(0);
	textSize(28);
	text(scoreText + score, width/2,30);

	long currentTime = millis();
	enemyShootCooldown = enemyShootCooldown - deltaTime;
	
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
