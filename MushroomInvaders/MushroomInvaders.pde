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


void setup()
{
	size(800,1000);
	frameRate(165);
	textAlign(CENTER);
	ellipseMode(CENTER);
	backgroundImage = loadImage("Background.png");

	player = new Player(400,850,60);

	spawnEnemies();
	float enemyShootCooldown = random(3,4);

	bullets = new Bullet[30];
	
}


void draw()
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


void spawnEnemies()
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
		enemies[i] = new Enemy(50 + ((i-20)*enemyDistance), height/4.8, 25,1);
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


void gameOverScreen()
{
	background(0,0,0,100);
	textSize(50);
	fill(255);
	text(gameOverText, width/2, height/2);
	textSize(20);
	text(restartGameText, width/2, height/2 + 40);
}


void gameScreen()
{
	background(backgroundImage);
	fill(255);
	textSize(28);
	text(scoreText + score, width/2,30);

	long currentTime = millis();
	enemyShootCooldown = enemyShootCooldown - deltaTime;
	shootCooldown = shootCooldown - deltaTime;
	
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
		bullets[i].update();
	}

	time = currentTime;
}


void restartGame()
{
	player = new Player(400,850,60);

	spawnEnemies();

	bullets = new Bullet[30];

	score = 0;
}