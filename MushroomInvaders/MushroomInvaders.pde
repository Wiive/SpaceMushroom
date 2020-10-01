Player player;
Enemy[] enemies;
Bullet[] bullets;
int numberOfEnemies = 40;
int enemyDistanceLength = 30;
float deltaTime;
long time;
float enemyShootCooldown;
int score = 0;
String scoreText = "Current Score: ";
String gameOverText = "GAME OVER";
String restartGameText = "Press [R] to restart game";
boolean gameOver = false;


void setup()
{
	size(800,1000);
	frameRate(165);
	textAlign(CENTER);
	ellipseMode(CENTER);

	player = new Player(400,850,30,200,100,150);
	spawnEnemies();

	bullets = new Bullet[30];
	float enemyShootCooldown = random(3,4);
}

void draw()
{
/*	textSize(28);
	text(scoreText + score, width/2,30);*/

	if(gameOver)
	{
		gameOverScreen();
	}
	else
	{
		gameScreen();
	}	
}

void restartGame()
{
	player = new Player(400,850,30,200,100,150);
	spawnEnemies();

	bullets = new Bullet[30];

	score = 0;
}

void gameScreen()
{
	background(0);
	fill(255);
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

void gameOverScreen()
{
	background(0,0,0,100);
	textSize(50);
	fill(255);
	text(gameOverText, width/2, height/2);
	textSize(20);
	text(restartGameText, width/2, height/2 + 40);
}

void spawnEnemies()
{
	enemies = new Enemy[numberOfEnemies];
	for (int i = 0; i < numberOfEnemies; ++i)
	{
		if(i < 10)
		{
		enemies[i] = new Enemy(50 + (i*enemyDistanceLength), height/6, 25,1);
		}
		if(i >= 10)
		{			
			enemies[i] = new Enemy(50 + ((i-10)*enemyDistanceLength), height/8, 25,2);
		}
		if(i >= 20)
		{			
			enemies[i] = new Enemy(50 + ((i-20)*enemyDistanceLength), height/12, 25,3);
		}
	}
	// Originalvärden för Enemies
	/*enemies = new Enemy[numberOfEnemies];
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
	}*/
}