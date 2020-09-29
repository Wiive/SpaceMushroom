Player player;
float deltaTime;
long time;

void setup()
{
	size(800,1000);
	frameRate(60);

	player = new Player(400,850,30,200,100,150);
}

void draw()
{
	background(0);

	long currentTime = millis();
	deltaTime = (currentTime - time);
	deltaTime *= 0.001f;

	player.update();
	player.draw();

	time = currentTime;
}
