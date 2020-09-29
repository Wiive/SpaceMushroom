Player player;
Enemy[] enemies;
Bullet[] bullets; 
int numberOfEnemys = 5;
float deltaTime;
long time;

void setup()
{
	size(800,1000);
	frameRate(60);

	player = new Player(400,850,30,200,100,150);
	enemies = new Enemy[numberOfEnemys];
	for (int i = 0; i < numberOfEnemys; ++i)
	{
		enemies[i] = new Enemy(50 + (i*50), 50, 30,1);
	}

	bullets = new Bullet[10];
}

void draw()
{
	background(0);

	long currentTime = millis();
	deltaTime = (currentTime - time);
	deltaTime *= 0.001f;

	player.update();
	player.draw();

	for(int i = 0; i < enemies.length; ++i)
	{
		enemies[i].update();
		enemies[i].draw();
	}


	time = currentTime;
}
